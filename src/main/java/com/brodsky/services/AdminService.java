package com.brodsky.services;

import com.brodsky.bussinessLogic.AdminFacade;
import com.brodsky.javaBeans.Company;
import com.brodsky.javaBeans.Customer;
import com.brodsky.services.customAnnotations.AdminAuthorization;


import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("admin")
@AdminAuthorization
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class AdminService {

    @POST
    @Path("companies")
    public Response createCompany(Company company,
                                  @Context ContainerRequestContext context) {
        try {
            AdminFacade adminFacade =
                    (AdminFacade) context.getProperty("adminFacade");
            adminFacade.addCompany(company);
            System.out.println(company);
            return Response.ok(company).status(201).build();
        } catch (Exception ex) {
            String json = "{\"error\": \"" + ex.getMessage() + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @DELETE
    @Path("companies")
//    @Path("{id}")
    public Response removeCompany(Company company,
                                  @Context ContainerRequestContext context) {
        try {
            AdminFacade adminFacade = (AdminFacade) context.getProperty("adminFacade");
            adminFacade.deleteCompany(company);
            return Response.ok().status(204).build();
        }
        catch(Exception ex) {
            String json = "{\"error\": \"" + ex.getMessage() + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @PUT
    @Path("companies")
    public Response updateCompany(Company company,
                                  @Context ContainerRequestContext context) {
        try {
            AdminFacade adminFacade = (AdminFacade) context.getProperty("adminFacade");
            adminFacade.updateCompany(company);
            return Response.ok(company).build();
        }
        catch(Exception ex) {
            String json = "{\"error\": \"" + ex.getMessage() + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @GET
    @Path("companies/{id}")
    public Response getCompany(@PathParam("id") int id,
                               @Context ContainerRequestContext context) {
        try {
            AdminFacade adminFacade = (AdminFacade) context.getProperty("adminFacade");
            Company company = adminFacade.getOneCompany(id);
            return Response.ok(company).build();
        }
        catch(Exception ex) {
            String json = "{\"error\": \"" + ex.getMessage() + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @GET
    @Path("companies/all")
    public Response getAllCompanies(@Context ContainerRequestContext context) {
        try {
            AdminFacade adminFacade = (AdminFacade) context.getProperty("adminFacade");
            ArrayList<Company> companies = adminFacade.getAllCompanies();
            return Response.ok(companies).build();
        }
        catch(Exception ex) {
            String json = "{\"error\": \"" + ex.getMessage() + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @POST
    @Path("customers")
    public Response createCustomer(Customer customer,
                                   @Context ContainerRequestContext context) {
        try {
            AdminFacade adminFacade = (AdminFacade) context.getProperty("adminFacade");
            adminFacade.addCustomer(customer);
            return Response.ok(customer).status(201).build();
        } catch (Exception ex) {
            String json = "{\"error\": \"" + ex.getMessage() + "\"}";
            System.out.println(json);
            return Response.serverError().entity(json).build();
        }
    }

    @DELETE
    @Path("customers")
//    @Path("{id}")
    public Response removeCustomer(Customer customer,
                                   @Context ContainerRequestContext context) {

        try {
            AdminFacade adminFacade = (AdminFacade) context.getProperty("adminFacade");
            adminFacade.deleteCustomer(customer);
            return Response.ok().status(204).build();
        }
        catch(Exception ex) {
            String json = "{\"error\": \"" + ex.getMessage() + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @GET
    @Path("customers/{id}")
    public Response getCustomer(@PathParam("id") int id,
                                @Context ContainerRequestContext context) {
        try {
            AdminFacade adminFacade = (AdminFacade) context.getProperty("adminFacade");
            Customer customer = adminFacade.getOneCustomer(id);
            return Response.ok(customer).build();
        }
        catch(Exception ex) {
            String json = "{\"error\": \"" + ex.getMessage() + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @PUT
    @Path("customers")
    public Response updateCustomer(Customer customer,
                                   @Context ContainerRequestContext context) {
        try {
            AdminFacade adminFacade = (AdminFacade) context.getProperty("adminFacade");
            adminFacade.updateCustomer(customer);
            customer = adminFacade.getOneCustomer(customer.getId());
            return Response.ok(customer).build();
        } catch (Exception e) {
            String json = "{\"error\": \"" + e.getMessage() + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

    @GET
    @Path("customers/all")
    public Response getAllCustomers(@Context ContainerRequestContext context) {
        try {
            AdminFacade adminFacade = (AdminFacade) context.getProperty("adminFacade");
            ArrayList<Customer> customers = adminFacade.getAllCustomers();
            return Response.ok(customers).build();
        }
        catch(Exception ex) {
            String json = "{\"error\": \"" + ex.getMessage() + "\"}";
            return Response.serverError().entity(json).build();
        }
    }

}
