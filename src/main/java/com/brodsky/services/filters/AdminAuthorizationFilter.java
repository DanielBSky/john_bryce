package com.brodsky.services.filters;

import com.brodsky.bussinessLogic.AdminFacade;
import com.brodsky.bussinessLogic.login.ClientType;
import com.brodsky.bussinessLogic.login.LoginMenager;
import com.brodsky.services.util.AuthorizationUtil;
import com.brodsky.services.customAnnotations.AdminAuthorization;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
@AdminAuthorization
public class AdminAuthorizationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        try {
            LoginMenager loginMenager = LoginMenager.getInstance();

            System.out.println(requestContext.getHeaders());

            List<String> authHeader = requestContext.getHeaders().
                    get(AuthorizationUtil.AUTHORIZATION_HEADER);

            List<String> pair = AuthorizationUtil.
                    getAuthorizationPairFromHeader(authHeader);

            AdminFacade adminFacade = (AdminFacade) loginMenager.
                    login(pair.get(0), pair.get(1), ClientType.ADMINISTRATOR);

            if(adminFacade != null) {

                requestContext.setProperty("adminFacade", adminFacade);
                return;
            }

        } catch (Exception e) {
            Response unauthorizedStatus =
                    Response.status(Response.Status.UNAUTHORIZED).
                    entity("{\"error\" : \"" + e.getMessage() + "\"}").build();

            requestContext.abortWith(unauthorizedStatus);
        }
    }
}
