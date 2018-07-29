package com.brodsky.DAO.DBDAO.test;

import com.brodsky.DAO.DBDAO.Categories;
import com.brodsky.DAO.DBDAO.CompaniesDBDAO;
import com.brodsky.javaBeans.Company;

import java.util.ArrayList;

public class TestCompaniesDBDAO {
    public static void main(String[] args) {
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();


        Categories categories = Categories.SPECTACLE;

        System.out.println(categories.ordinal() + 1);


//
//        try {
//
//        ArrayList<Company> companies = getTenCompanies();
//
//        for (Company company : companies) {
//                companiesDBDAO.addCompany(company);
//        }
//
//        System.out.println(companiesDBDAO.isCompanyExistsById(2));
//
//        System.out.println(companiesDBDAO.isCompanyExists
//                ("block@block.com", "lock1234"));
//
//        companiesDBDAO.deleteCompany(10);
//
//        companiesDBDAO.updateCompany(new Company(9, "MAYWAY", "mayway@mayway.com",
//                "mayway1234", null));
//
//        companies = companiesDBDAO.getAllCompanies();
//
//        for (Company c : companies) {
//            System.out.println(c);
//        }
//
//        System.out.println(companiesDBDAO.getOneCompany(9));
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        try {
//            ArrayList<Company> companies = companiesDBDAO.getAllCompaniesStoredProcedure();
//
//            for (Company c : companies) {
//            System.out.println(c);
//            }
//
//            System.out.println(companiesDBDAO.isCompanyEmailExists("tres"));
//            System.out.println(companiesDBDAO.isCompanyNameExists("SLOPs"));
//            System.out.println(companiesDBDAO.getOneCompany(123));


//            companies.get(3).setEmail("2100");

//            Company company = new Company(0, "BREST", "test",
//                    "test", null);
//            companiesDBDAO.updateCompanyEmail("tres", "DRUM");
//            companiesDBDAO.updateCompanyPassword("34e", "DRUM");

//            companiesDBDAO.updateCompanyEmailAndPassword(company);


//                System.out.print(ThreadLocalRandom.current().nextInt(100, 201) + " ");

//            System.out.println(companiesDBDAO.getCompanyId("tost", "tos"));

        } catch (Exception e) {
            e.printStackTrace();
        }

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

}
