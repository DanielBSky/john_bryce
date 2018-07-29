package com.brodsky.bussinessLogic.login;

import com.brodsky.DAO.CompaniesDAO;
import com.brodsky.DAO.DBDAO.CompaniesDBDAO;
import com.brodsky.DAO.CustomersDAO;
import com.brodsky.DAO.DBDAO.CustomersDBDAO;
import com.brodsky.bussinessLogic.AdminFacade;
import com.brodsky.bussinessLogic.ClientFacade;
import com.brodsky.bussinessLogic.CompanyFacade;
import com.brodsky.bussinessLogic.CustomerFacade;
import com.brodsky.bussinessLogic.exceptions.LoginException;

public class LoginMenager {

    private CompaniesDAO companiesDAO;
    private CustomersDAO customersDAO;
    private static LoginMenager instance = new LoginMenager();

    private LoginMenager() {
        this.companiesDAO = new CompaniesDBDAO();
        this.customersDAO = new CustomersDBDAO();
    }

    public static LoginMenager getInstance() {
        return instance;
    }

    public ClientFacade login(String email, String password, ClientType clientType) throws Exception {

        switch (clientType) {
            case ADMINISTRATOR:

                if(email.equals("admin@admin.com") && password.equals("admin")) {
                    return new AdminFacade();
                }
                else throw new LoginException();

            case COMPANY:
                    if(companiesDAO.isCompanyExists(email, password)) {
                        int id = companiesDAO.getCompanyId(email, password);
                        return new CompanyFacade(id);
                    }
                    else throw new LoginException();

            case CUSTOMER:
                    if(customersDAO.isCustomerExists(email, password)) {
                        int id = customersDAO.getCustomerId(email, password);
                        return new CustomerFacade(id);
                    }
                    else throw new LoginException();

        }

        return null;

    }

}
