package com.brodsky.bussinessLogic.test;

import com.brodsky.bussinessLogic.AdminFacade;
import com.brodsky.javaBeans.Customer;

class TestAdminFacade {
   public static void main(String[] args) throws Exception {
       AdminFacade adminFacade = new AdminFacade();
//
//        Company company = new Company(0, "BREST", "tost",
//                "tost", null);
//
//        adminFacade.updateCompany(company);
//
//        adminFacade.deleteCompany(5);
//
//        ArrayList<Customer> customers = adminFacade.getAllCustomers();
//        System.out.println(customers);
//
//        System.out.println(adminFacade.getOneCustomer(10));
//
//        adminFacade.deleteCustomer(1);

       Customer customer = new Customer(2, "Smale", "Tooth",
               "gft", "dsd", null);

       adminFacade.updateCustomer(customer);

   }

}
