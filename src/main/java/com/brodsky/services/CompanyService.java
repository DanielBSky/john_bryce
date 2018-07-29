package com.brodsky.services;


import com.brodsky.DAO.DBDAO.Categories;
import com.brodsky.bussinessLogic.CompanyFacade;
import com.brodsky.javaBeans.Coupon;
import com.brodsky.services.customAnnotations.CompanyAuthorization;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@CompanyAuthorization
@Path("companies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class CompanyService {

    private CompanyFacade companyFacade = null;


    @POST
    public Response createCoupon(Coupon coupon,
                         @Context ContainerRequestContext context) {
        try {
            companyFacade = (CompanyFacade)context.getProperty("comFacade");
            companyFacade.addCoupon(coupon);
            System.out.println(coupon);

            return Response.ok(coupon).status(201).build();

        } catch (Exception ex) {
            String json = "{\"error\": \"" + ex + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @DELETE
//    @Path("{id}")
    public Response removeCoupon(Coupon coupon,
                         @Context ContainerRequestContext context) {

        try {
            companyFacade = (CompanyFacade)context.getProperty("comFacade");
            Coupon dbCoupon = companyFacade.deleteCoupon(coupon.getId());
            return Response.ok(dbCoupon).build();
        }
        catch(Exception ex) {
            String json = "{\"error\": \"" + ex + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @PUT
    public Response updateCoupon(Coupon coupon,
                         @Context ContainerRequestContext context) {
        try {
            companyFacade = (CompanyFacade)context.getProperty("comFacade");
            Coupon updCoupon = companyFacade.updateCoupon(coupon);
            return Response.ok(updCoupon).build();
        }
        catch(Exception ex) {
            String json = "{\"error\": \"" + ex + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @GET
    @Path("{id}")
    public Response getCompanyCoupon(@PathParam("id") int id,
                             @Context ContainerRequestContext context) {
        try {
            companyFacade = (CompanyFacade)context.getProperty("comFacade");
            Coupon coupon = companyFacade.getCompanyCoupon(id);
            return Response.ok(coupon).build();
        }
        catch(Exception ex) {
            String json = "{\"error\": \"" + ex.getMessage() + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @GET
    @Path("all")
    public Response getAllCoupons(@Context ContainerRequestContext context) {
        try {
            companyFacade = (CompanyFacade)context.getProperty("comFacade");
            System.out.println(companyFacade.getCompanyID());
            ArrayList<Coupon> coupons = companyFacade.getCompanyCoupons();
            return Response.ok(coupons).build();
        }
        catch(Exception ex) {
            String json = "{\"error\": \"" + ex + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @GET
    @Path("all/{type}")
    public Response getCouponsByType(@PathParam("type") String type,
                             @Context ContainerRequestContext context) {
        try {
            companyFacade = (CompanyFacade)context.getProperty("comFacade");
            ArrayList<Coupon> coupons = companyFacade.
                    getCompanyCoupons(Categories.valueOf(type.toUpperCase()));
            return Response.ok(coupons).build();
        }
        catch(Exception ex) {
            String json = "{\"error\": \"" + ex + "\"}";
            return Response.serverError().entity(json).build();
        }
    }
}