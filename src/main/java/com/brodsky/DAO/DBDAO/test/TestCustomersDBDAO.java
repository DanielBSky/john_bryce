package com.brodsky.DAO.DBDAO.test;

import com.brodsky.DAO.DBDAO.CustomersDBDAO;
import com.brodsky.javaBeans.Customer;

import java.util.ArrayList;

public class TestCustomersDBDAO {

    public static void main(String[] args) {
        CustomersDBDAO customersDBDAO = new CustomersDBDAO();

        try {
            ArrayList<Customer> customers = getTenCustomers();

            for (Customer customer : customers) {
                    customersDBDAO.addCustomer(customer);
            }

//            System.out.println(customersDBDAO.isCustomerExists
//                    ("oliver@gmail.com", "oliver_crous"));
//
//            System.out.println(customersDBDAO.isCustomerExistsById(91));
//
//            customersDBDAO.deleteCustomer(10);
//
//            customersDBDAO.updateCustomer(new Customer(9, "Test", "Mount",
//                    "local@gmail.com", "local_mount", null));
//
//            System.out.println(customersDBDAO.getOneCustomer(2));
//
//            customers = customersDBDAO.getAllCustomers();
//
//            for (Customer customer : customers) {
//                System.out.println(customer);
//            }
//
//
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }


    private static ArrayList<Customer> getTenCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();

        customers.add(new Customer(0, "Oliver", "Crouse",
                "oliver@gmail.com", "oliver_crouse", null));

        customers.add(new Customer(0, "Olivia", "Jannet",
                "olivia@gmail.com", "olivia_jannet", null));

        customers.add(new Customer(0, "Danon", "Profer",
                "danon@gmail.com", "danon_profer", null));

        customers.add(new Customer(0, "Danetta", "Proud",
                "danetta@gmail.com", "danetta_ptoud", null));

        customers.add(new Customer(0, "Igma", "Jordan",
                "igma@gmail.com", "igma_jordan", null));

        customers.add(new Customer(0, "Tenor", "Trevors",
                "tenor@gmail.com", "tenor_trevors", null));

        customers.add(new Customer(0, "Julia", "Adelia",
                "julia@gmail.com", "julia_adelia", null));

        customers.add(new Customer(0, "Bruno", "Inner",
                "bruno@gmail.com", "bruno_inner", null));

        customers.add(new Customer(0, "Stout", "Lonov",
                "stout@gmail.com", "stout_lonov", null));

        customers.add(new Customer(0, "Local", "Mount",
                "local@gmail.com", "local_mount", null));

        return customers;
    }
}
