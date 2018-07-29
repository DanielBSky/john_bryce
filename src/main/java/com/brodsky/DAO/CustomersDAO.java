package com.brodsky.DAO;

import com.brodsky.javaBeans.Customer;

import java.util.ArrayList;

public interface CustomersDAO {
    int getCustomerId(String email, String password) throws Exception;
    boolean isCustomerExists(String email, String password) throws Exception;
    boolean isCustomerExistsById(int id) throws Exception;
    boolean isCustomerEmailExists(String email) throws Exception;
    void addCustomer(Customer customer) throws Exception;
    void updateCustomer(Customer customer) throws Exception;
    void deleteCustomer(int customerID) throws Exception;
    ArrayList<Customer> getAllCustomers() throws Exception;
    Customer getOneCustomer(int customerId) throws Exception;
}
