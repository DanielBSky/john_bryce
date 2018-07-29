package com.brodsky.DAO;

import com.brodsky.javaBeans.Company;

import java.util.ArrayList;

public interface CompaniesDAO {

    boolean isCompanyExists(String email, String password) throws Exception;
    boolean isCompanyExistsById(int id) throws Exception;
    boolean isCompanyNameExists(String name) throws Exception;
    boolean isCompanyEmailExists(String email) throws Exception;
    void addCompany(Company company) throws Exception;
    void updateCompany(Company company) throws Exception;
    void updateCompanyEmail(String email, String name) throws Exception;
    void updateCompanyPassword(String password, String name) throws Exception;
    void updateCompanyEmailAndPassword(Company company) throws Exception;
    void deleteCompany(int companyID) throws Exception;
    int getCompanyId(String email, String password) throws Exception;
    ArrayList<Company> getAllCompanies() throws Exception;
    ArrayList<Company> getAllCompaniesStoredProcedure() throws Exception;
    Company getOneCompany(int companyID) throws Exception;

    void getOneCompanyByName(Company company) throws Exception;
}
