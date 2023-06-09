package com.jaimayal.customer;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper<Customer> {
    
    @Override
    public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
        Long customerId = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        Integer age = resultSet.getInt("age");
        String gender = resultSet.getString("gender");
        
        return new Customer(customerId, name, email, password, age, gender);
    }
}
