package com.brodsky.services;

import com.brodsky.DAO.DBDAO.Categories;
import com.brodsky.bussinessLogic.CustomerFacade;
import com.brodsky.javaBeans.Coupon;
import com.brodsky.services.customAnnotations.CustomerAuthorization;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@CustomerAuthorization
@Path("customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class CustomerService {

    private CustomerFacade customerFacade = null;

    @POST
    public Response purchaseCoupon(Coupon coupon,
                       @Context ContainerRequestContext context) {
        try {
            customerFacade = (CustomerFacade) context.getProperty("cusFacade");
            System.out.println(coupon);
            customerFacade.purchaseCoupon(coupon);

            return Response.status(201).build();

        } catch (Exception ex) {
            String json = "{\"error\": \"" + ex.getMessage() + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @GET
    @Path("coupons/all")
    public Response getAllPurchasedCoupons(
                        @Context ContainerRequestContext context) {
        try {
            customerFacade = (CustomerFacade) context.getProperty("cusFacade");
            ArrayList<Coupon> coupons = customerFacade.getCustomerCoupons();
            return Response.ok(coupons).build();

        } catch (Exception ex) {
            String json = "{\"error\": \"" + ex.getMessage() + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @GET
    @Path("coupons/type/{type}")
    public Response getAllPurchasedCouponsByType(@PathParam("type") String type,
                        @Context ContainerRequestContext context) {
        try {
            customerFacade = (CustomerFacade) context.getProperty("cusFacade");
            ArrayList<Coupon> coupons = customerFacade.
                    getCustomerCoupons(Categories.valueOf(type.toUpperCase()));
            return Response.ok(coupons).build();
        }
        catch(Exception ex) {
            String json = "{\"error\": \"" + ex.getMessage() + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @GET
    @Path("coupons/price/{price}")
    public Response getAllPurchasedCouponsByPrice(@PathParam("price") int price,
                                                  @Context ContainerRequestContext context) {
        try {
            customerFacade = (CustomerFacade) context.getProperty("cusFacade");
            ArrayList<Coupon> coupons = customerFacade.
                    getCustomerCoupons(price);
            return Response.ok(coupons).build();
        }
        catch(Exception ex) {
            String json = "{\"error\": \"" + ex.getMessage() + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

}
