package com.brodsky.DAO.DBDAO.test;

import com.brodsky.DAO.DBDAO.Categories;

public class TestCategories {
    public static void main(String[] args) {
        System.out.println(Categories.CONCERT.getID());
        System.out.println(Categories.valueOf("CONCERT"));
    }

}
