package com.dev.smallurl.controller;

import com.dev.smallurl.model.URLError;
import com.dev.smallurl.model.URL;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotNull;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/changeurl")
public class SmallUrlController {

  @Autowired
  private RedisTemplate<String, URL> redisTemplate;

  @Value("${redis.ttl}")
  private long ttl;

  /**
   * Returns the original URL.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity getShortUrl(@PathVariable String id) {

    // get from redis
    URL url = redisTemplate.opsForValue().get(id);

    if (url == null) {
      URLError urlError = new URLError("id", id, "No such key exists");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(urlError);
    }

    return ResponseEntity.ok(url);
  }

  /**
   * Returns a shortened URL.
   */
  @RequestMapping(method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity shortenURL(@RequestBody @NotNull URL url) {

    UrlValidator validator = new UrlValidator(
        new String[]{"http", "https"}
    );

    // if invalid url, return error
    if (!validator.isValid(url.getUrl())) {
      URLError uRLError = new URLError("url", url.getUrl(), "Invalid URL");
      return ResponseEntity.badRequest().body(uRLError);
    }

    String id = Hashing.murmur3_32().hashString(url.getUrl(), Charset.defaultCharset()).toString();
    url.setId(id);
    url.setCreated(LocalDateTime.now());

    //stores the value in redis
    redisTemplate.opsForValue().set(url.getId(), url, ttl, TimeUnit.SECONDS);

    return ResponseEntity.ok(url);
  }

}
