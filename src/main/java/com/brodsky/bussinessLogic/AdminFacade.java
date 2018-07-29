package com.brodsky.bussinessLogic;

import com.brodsky.DAO.DBDAO.CompaniesDBDAO;
import com.brodsky.DAO.DBDAO.CustomersDBDAO;
import com.brodsky.bussinessLogic.exceptions.*;
import com.brodsky.javaBeans.Company;
import com.brodsky.javaBeans.Customer;

import java.util.ArrayList;


public class AdminFacade extends ClientFacade {

    public AdminFacade() {
        companiesDAO = new CompaniesDBDAO();
        customersDAO = new CustomersDBDAO();
    }

    public boolean login(String email, String password) {
        if(email == null || password == null) {
            return false;
        }
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    public void addCompany(Company company) throws Exception{

        if(companiesDAO.isCompanyNameExists(company.getName())) {
            throw new CompanyNameAlreadyExistException();
        }
        if(companiesDAO.isCompanyEmailExists(company.getEmail())) {
            throw new CompanyEmailAlreadyExistException();
        }
        else {
            companiesDAO.addCompany(company);
        }

    }

    public void updateCompany(Company company) throws Exception{

        if(!companiesDAO.isCompanyNameExists(company.getName())) {
            throw new CompanyNotExistException();
        }
        else {
            companiesDAO.updateCompanyEmailAndPassword(company);
            companiesDAO.getOneCompanyByName(company);
        }
    }

    public void deleteCompany(Company company) throws Exception{
        int id = company.getId();

        if(!companiesDAO.isCompanyExistsById(id)) {
            throw new CompanyNotExistException();
        }
        else {
            Company temp = companiesDAO.getOneCompany(id);

            company.setId(temp.getId());
            company.setName(temp.getName());
            company.setEmail(temp.getEmail());
            company.setPassword(temp.getPassword());
            company.setCoupons(temp.getCoupons());

            companiesDAO.deleteCompany(id);
        }
    }

    public ArrayList<Company> getAllCompanies() throws Exception {

        ArrayList<Company> companies = companiesDAO.getAllCompaniesStoredProcedure();
        return companies;
    }

    public Company getOneCompany(int companyID) throws Exception {
        Company company = null;

        if (!companiesDAO.isCompanyExistsById(companyID)) {
            throw new CompanyNotExistException();
        }
        else {
           company = companiesDAO.getOneCompany(companyID);
        }

        return company;
    }


    public void addCustomer(Customer customer) throws Exception {

        if(customersDAO.isCustomerEmailExists(customer.getEmail())) {
            throw new CustomerEmailAlreadyExistException();
        }
        else {
            customersDAO.addCustomer(customer);
        }
    }


    public void updateCustomer(Customer customer) throws Exception {

        if(!customersDAO.isCustomerExistsById(customer.getId())) {
            throw new CustomerNotExistException();
        }
        else {
            customersDAO.updateCustomer(customer);
        }
    }


    public void deleteCustomer(Customer customer) throws Exception {
        Customer dbCustomer = null;

        if(!customersDAO.isCustomerExistsById(customer.getId())) {
            throw new CustomerNotExistException();
        }
        else {
            customersDAO.deleteCustomer(customer.getId());
        }

    }


    public ArrayList<Customer> getAllCustomers() throws Exception {

        ArrayList<Customer>  customers = customersDAO.getAllCustomers();
        return customers;
    }


    public Customer getOneCustomer(int customerID) throws Exception {
        Customer customer = null;

        if (!customersDAO.isCustomerExistsById(customerID)) {
            throw new CustomerNotExistException();
        }
        else {
            customer = customersDAO.getOneCustomer(customerID);
        }

        return customer;
    }

}