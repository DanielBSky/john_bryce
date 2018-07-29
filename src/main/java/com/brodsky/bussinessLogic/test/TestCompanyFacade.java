package com.brodsky.bussinessLogic.test;

import com.brodsky.bussinessLogic.CompanyFacade;
import com.brodsky.javaBeans.Company;

class TestCompanyFacade {
   public static void main(String[] args) throws Exception {
       Company company = new Company();
       CompanyFacade companyFacade = new CompanyFacade(3);

//        System.out.println(companyFacade.login("qwe", "k"));
//        System.out.println(companyFacade.login("tres", "34e"));
//
//        Coupon coupon = new Coupon();
//
//        coupon.setId(56);
//
//        coupon.setCompanyId(3);
//        coupon.setTitle("Ssd");
//        coupon.setStartDate(LocalDate.of(2018, 12, 23));
//        coupon.setEndDate(LocalDate.of(2018, 12, 24));
//        coupon.setCategoryId(7);

//        companyFacade.addCoupon(coupon);

//        companyFacade.updateCoupon(coupon);

//        companyFacade.deleteCoupon(57);


//       ArrayList<Coupon> coupons = companyFacade.getCompanyCoupons(1000);
//
//       for (Coupon c : coupons) {
//
//           System.out.println(c.getPrice());
//       }
       System.out.println(companyFacade.getCompanyDetails());
   }
}
