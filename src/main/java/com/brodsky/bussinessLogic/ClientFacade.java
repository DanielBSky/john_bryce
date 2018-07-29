package com.brodsky.bussinessLogic;

import com.brodsky.DAO.CompaniesDAO;
import com.brodsky.DAO.CouponsDAO;
import com.brodsky.DAO.CustomersDAO;

public abstract class ClientFacade {

    protected CompaniesDAO companiesDAO;
    protected CustomersDAO customersDAO;
    protected CouponsDAO couponsDAO;

    public abstract boolean login(String email, String password) throws Exception;

}
