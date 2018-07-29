package com.brodsky.services.filters;


import com.brodsky.bussinessLogic.CompanyFacade;
import com.brodsky.bussinessLogic.login.ClientType;
import com.brodsky.bussinessLogic.login.LoginMenager;
import com.brodsky.services.util.AuthorizationUtil;
import com.brodsky.services.customAnnotations.CompanyAuthorization;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@CompanyAuthorization
@Provider
public class CompanyAuthorizationFilter implements ContainerRequestFilter {


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        try {
            LoginMenager loginMenager = LoginMenager.getInstance();

            List<String> authHeader = requestContext.getHeaders().
                    get(AuthorizationUtil.AUTHORIZATION_HEADER);

            List<String> pair = AuthorizationUtil.
                    getAuthorizationPairFromHeader(authHeader);

            CompanyFacade companyFacade = (CompanyFacade)loginMenager.
                        login(pair.get(0), pair.get(1), ClientType.COMPANY);

            if(companyFacade != null) {
                System.out.println(companyFacade.getCompanyID());
                requestContext.setProperty("comFacade", companyFacade);
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
