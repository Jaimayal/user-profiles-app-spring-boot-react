package com.jaimayal.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {
    
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, 
                                         CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }


    @Override
    public List<Customer> selectAllCustomers() {
        String sql = """
                SELECT id, name, email, password, age, gender
                FROM customer
                ORDER BY id
                """;
        
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Long customerId) {
        String sql = """
                SELECT id, name, email, password, age, gender
                FROM customer
                WHERE id = ?
                """;
        
        return jdbcTemplate.query(sql, customerRowMapper, customerId)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        String sql = """
                INSERT INTO customer (name, email, password, age, gender)
                VALUES (?, ?, ?, ?, ?)
                """;
        
        jdbcTemplate.update(
                sql, 
                customer.getName(), 
                customer.getEmail(), 
                customer.getPassword(),
                customer.getAge(),
                customer.getGender()
        );
    }

    @Override
    public boolean existsCustomerById(Long customerId) {
        String sql = """
                SELECT count(id)
                FROM customer
                WHERE id = ?
                """;
        
        Integer count = jdbcTemplate.queryForObject(
                sql, 
                Integer.class, 
                customerId
        );
        
        return count != null && count > 0;
    }

    @Override
    public boolean existsCustomerByEmail(String email) {
        String sql = """
                SELECT count(id)
                FROM customer
                WHERE email = ?
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                email
        );

        return count != null && count > 0;
    }

    @Override
    public void updateCustomer(Customer update) {
        String sql = """
                UPDATE customer
                SET name = ?, email = ?, password = ?, age = ?, gender = ?
                WHERE id = ?
                """;
        
        jdbcTemplate.update(
                sql, 
                update.getName(), 
                update.getEmail(),
                update.getPassword(),
                update.getAge(), 
                update.getGender(),
                update.getId()
        );
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        String sql = """
                DELETE FROM customer
                WHERE id = ?
                """;
        
        jdbcTemplate.update(sql, customerId);
    }

    @Override
    public Optional<Customer> selectCustomerByEmail(String email) {
        String sql = """
                SELECT id, name, email, password, age, gender
                FROM customer
                WHERE email = ?
                """;

        return jdbcTemplate.query(sql, customerRowMapper, email)
                .stream()
                .findFirst();
    }
}
