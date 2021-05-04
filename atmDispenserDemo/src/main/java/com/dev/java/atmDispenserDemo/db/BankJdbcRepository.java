package com.dev.java.atmDispenserDemo.db;

import com.dev.java.atmDispenserDemo.model.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Prajula
 */
@Repository
public class BankJdbcRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    BankRowMapper bankRowMapper;

    public List<Bank> findAll(){
        return jdbcTemplate.query("select * from bank", bankRowMapper);
    }

    public Bank findByType(String type){
        return jdbcTemplate.queryForObject("select * from bank where type=?",
                new Object[] { type },
                new BeanPropertyRowMapper<>(Bank.class));
    }

    public int update(Bank bank, int amount) {
        return jdbcTemplate.update("update bank set amount = ? where type = ?",
                amount, bank.getType());
    }
}
