package com.brodsky.services.filters;


import com.brodsky.bussinessLogic.CustomerFacade;
import com.brodsky.bussinessLogic.login.ClientType;
import com.brodsky.bussinessLogic.login.LoginMenager;
import com.brodsky.services.util.AuthorizationUtil;
import com.brodsky.services.customAnnotations.CustomerAuthorization;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@CustomerAuthorization
@Provider

public class CustomerAuthorizationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        try {
            LoginMenager loginMenager = LoginMenager.getInstance();

            List<String> authHeader = requestContext.getHeaders().
                    get(AuthorizationUtil.AUTHORIZATION_HEADER);

            List<String> pair = AuthorizationUtil.
                    getAuthorizationPairFromHeader(authHeader);

            CustomerFacade customerFacade = (CustomerFacade) loginMenager.
                    login(pair.get(0), pair.get(1), ClientType.CUSTOMER);

            if(customerFacade != null) {
//                System.out.println(customerFacade.getCustomerDetails());
                requestContext.setProperty("cusFacade", customerFacade);
                return;
            }


        } catch (Exception e) {
            Response unauthorizedStatus =
                    Response.status(Response.Status.UNAUTHORIZED).
                    entity("{\"error\" : \"" +
                    e.getMessage() + "\"}").build();

            requestContext.abortWith(unauthorizedStatus);
        }
    }
}
