package com.brodsky.bussinessLogic.login;

import com.brodsky.bussinessLogic.CompanyFacade;

public class TestLoginMenager {
    public static void main(String[] args) {
        LoginMenager loginMenager = LoginMenager.getInstance();

        try {
            System.out.println(loginMenager.login
                    ("admin@admin.com", "admin", ClientType.ADMINISTRATOR));
//        System.out.println(loginMenager.login
//                ("grog@grog.com", "grog1234", ClientType.COMPANY));
//        System.out.println(loginMenager.login
//                ("block@block.com", "block1234", ClientType.COMPANY));
//        System.out.println(loginMenager.login
//                ("danon@gmail.com", "", ClientType.CUSTOMER));

            CompanyFacade companyFacade1 = (CompanyFacade)loginMenager.login
                    ("drum@drum.com", "drum1234", ClientType.COMPANY);

            CompanyFacade companyFacade2 = (CompanyFacade)loginMenager.login
                    ("block@block.com", "block1234", ClientType.COMPANY);

            System.out.println(companyFacade1.getCompanyID());
            System.out.println(companyFacade2.getCompanyID());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
