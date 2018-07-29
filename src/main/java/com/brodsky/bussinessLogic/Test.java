package com.brodsky.bussinessLogic;

import com.brodsky.bussinessLogic.login.ClientType;
import com.brodsky.bussinessLogic.login.LoginMenager;
import com.brodsky.javaBeans.Company;
import com.brodsky.javaBeans.Coupon;
import com.brodsky.javaBeans.Customer;

import java.time.LocalDate;
import java.util.ArrayList;

public class Test {

    public static void testAll() {

        try {
            LoginMenager loginMenager = LoginMenager.getInstance();

            AdminFacade adminFacade = (AdminFacade) loginMenager.login
                    ("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
            System.out.print("This is the address of admin facade: " + adminFacade + "\n\n");
            testAdminFacade(adminFacade);

            CompanyFacade companyFacade = (CompanyFacade) loginMenager.login
                    ("block@block.com", "block1234", ClientType.COMPANY);
            System.out.println(companyFacade);
            testCompanyFacade(companyFacade);

            companyFacade = (CompanyFacade) loginMenager.login
                    ("brest@brest.com", "brest1234", ClientType.COMPANY);
            System.out.println(companyFacade);
            testCompanyFacade(companyFacade);

            companyFacade = (CompanyFacade) loginMenager.login
                    ("grog@grog.com", "grog1234", ClientType.COMPANY);
            System.out.println(companyFacade);
            testCompanyFacade(companyFacade);

            companyFacade = (CompanyFacade) loginMenager.login
                    ("drum@drum.com", "drum1234", ClientType.COMPANY);
            System.out.println(companyFacade);
            testCompanyFacade(companyFacade);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }


    private static void testAdminFacade(AdminFacade adminFacade) throws Exception{
        ArrayList<Company> companies = getTenCompanies();
        ArrayList<Customer> customers = getTenCustomers();

        for (Company c : companies) {
            adminFacade.addCompany(c);
        }

        for(Customer customer : customers) {
            adminFacade.addCustomer(customer);
        }

//        adminFacade.deleteCompany(1);

//        Company company = new Company(2, "BREST",
//                "up@up.com", "up1234", null);

//        adminFacade.updateCompany(company);

//        ArrayList<Company> companiesFromDatabase = adminFacade.getAllCompanies();
//
//        System.out.println("All companies from data base after some manipulation:");
//
//        for(Company c : companiesFromDatabase) {
//            System.out.println(c);
//        }
//
//        System.out.println("\nOne company from data base:");

//        Company companyFromDatabase = adminFacade.getOneCompany(3);
//        System.out.println(companyFromDatabase);



//        adminFacade.deleteCustomer(10);
//
//        Customer customer = new Customer(2, "Daniel", "Maimon", "dm@mail.com", "12", null);
//        adminFacade.updateCustomer(customer);
//
//        ArrayList<Customer> customersFromDatabase = adminFacade.getAllCustomers();
//
//        for(Customer c : customersFromDatabase) {
//            System.out.println(c);
//        }
//
//        Customer customerFromDatabase = adminFacade.getOneCustomer(3);
//        System.out.println(customerFromDatabase);



    }

    private static void testCompanyFacade(CompanyFacade companyFacade) throws Exception {

        ArrayList<Coupon> coupons = getCoupons(companyFacade.getCompanyID());
//
        for (Coupon c : coupons) {
            companyFacade.addCoupon(c);
        }

//        System.out.println(companyFacade.getCompanyDetails());
//        System.out.println(companyFacade.getCompanyCoupons(12.2));
//        System.out.println(companyFacade.getCompanyCoupons(Categories.CONFERENCE));
//        companyFacade.deleteCoupon(8);
//        System.out.println(companyFacade.getCompanyCoupons());


//        companyFacade.addCoupon(getTenCoupons().get(2));

    }


    private static ArrayList<Company> getTenCompanies() {
        ArrayList<Company> companies = new ArrayList<>();

        companies.add(new Company(0, "BLOCK", "block@block.com",
                "block1234", null));
        companies.add(new Company(0, "BREST", "brest@brest.com",
                "brest1234", null));
        companies.add(new Company(0, "DRUM", "drum@drum.com",
                "drum1234", null));
        companies.add(new Company(0, "GROG", "grog@grog.com",
                "grog1234", null));
        companies.add(new Company(0, "AEROFEST", "aerofest@aerofest.com",
                "aerofest1234", null));
        companies.add(new Company(0, "SLOP", "slop@slop.com",
                "slop1234", null));
        companies.add(new Company(0, "TUNE", "tune@tune.com",
                "tune1234", null));
        companies.add(new Company(0, "FGH", "fgh@fgh.com",
                "fgh1234", null));
        companies.add(new Company(0, "MAKEDAY", "makeday@makeday.com",
                "makeday1234", null));
        companies.add(new Company(0, "MAYWAY", "mayway@mayway.com",
                "mayway1234", null));

        return companies;
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


    private static ArrayList<Coupon> getCoupons(int companyId) {

        ArrayList<Coupon> coupons = new ArrayList<>();

            coupons.add(new Coupon(0, companyId, 1, "Secure Group Testing1",
                    "The principal goal of Group Testing (GT) " +
                    "is to identify a small subset\n" +
                    "of \"defective\" items from a large population, by grouping items into\n" +
                    "as few test pools as possible.",
                    LocalDate.of(2018, 10, 20),
                    LocalDate.of(2018, 11, 20),
                    100, 25, "alkd"));
            coupons.add(new Coupon(0, companyId, 2, "Secure Group Testing2",
                    "The principal goal of Group Testing (GT) " +
                            "is to identify a small subset\n" +
                            "of \"defective\" items from a large population, by grouping items into\n" +
                            "as few test pools as possible.",
                    LocalDate.of(2018, 10, 20),
                    LocalDate.of(2018, 11, 20),
                    100, 25, "alkd"));
            coupons.add(new Coupon(0, companyId, 3, "Secure Group Testing3",
                    "The principal goal of Group Testing (GT) " +
                            "is to identify a small subset\n" +
                            "of \"defective\" items from a large population, by grouping items into\n" +
                            "as few test pools as possible.",
                    LocalDate.of(2018, 10, 20),
                    LocalDate.of(2018, 11, 20),
                    100, 25, "alkd"));
            coupons.add(new Coupon(0, companyId, 4, "Secure Group Testing4",
                    "The principal goal of Group Testing (GT) " +
                            "is to identify a small subset\n" +
                            "of \"defective\" items from a large population, by grouping items into\n" +
                            "as few test pools as possible.",
                    LocalDate.of(2018, 10, 20),
                    LocalDate.of(2018, 11, 20),
                    100, 25, "alkd"));
            coupons.add(new Coupon(0, companyId, 5, "Secure Group Testing5",
                    "The principal goal of Group Testing (GT) " +
                            "is to identify a small subset\n" +
                            "of \"defective\" items from a large population, by grouping items into\n" +
                            "as few test pools as possible.",
                    LocalDate.of(2018, 10, 20),
                    LocalDate.of(2018, 11, 20),
                    100, 25, "alkd"));

        return coupons;

    }
}
