package com.brodsky.services.util;

import org.glassfish.jersey.internal.util.Base64;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AuthorizationUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    public static List<String> getAuthorizationPairFromHeader
            (List<String> authHeader) {

        String userName = null;
        String password = null;
        List<String> pair = new ArrayList<>();

        if(authHeader != null && authHeader.size() > 0) {
            String authToken = authHeader.get(0);
            authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
            String decodedStr = Base64.decodeAsString(authToken);
            StringTokenizer tokenizer =
                    new StringTokenizer(decodedStr, ":");

            if (tokenizer.hasMoreTokens()) {
                userName = tokenizer.nextToken();
            }

            if (tokenizer.hasMoreTokens()) {
                password = tokenizer.nextToken();
            }
        }

        pair.add(userName);
        pair.add(password);

        return pair;
    }
}
