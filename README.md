# Spring Boot URL  #
This project demonstrates the use of Spring Boot 2.0 and Redis to build a short-term URL api. Also popularly known as 'tinyurl'.

### Prerequisites
* Redis running locally
  * host: localhost
  * port: 6379

### Execution

```
java -jar spring-url-shortner.jar
```

### API Details
#### Create Short URL:
`http://localhost:8085/rest/changeurl`

Request body:
```JSON
{
    "url": "https://www.worldometers.info/coronavirus/"
}
```
Response body:
```JSON
{
    "id": "94717296",
    "url": "https://www.worldometers.info/coronavirus/",
    "created": "2018-12-02T14:11:26.887"
}
```
Response codes:

| HTTP Status | Description           |
|-------------|-----------------------|
| 200         | successful operation  |
| 500         | internal server error |

#### Retrieve Original URL:
`http://localhost:8085/rest/changeurl/{id}`

Response body:
```JSON
{
    "id": "94717296",
    "url": "https://www.worldometers.info/coronavirus/",
    "created": "2021-05-01T14:11:26.887"
}
```
Response codes:

| HTTP Status | Description           |
|-------------|-----------------------|
| 200         | successful operation  |
| 404         | not found             |
| 500         | internal server error |
