package com.brodsky.bussinessLogic.exceptions;

@SuppressWarnings("serial")
public class CompanyNotExistException extends Exception {

    public String getMessage() {
            return "Company doesn't exist.";
        }

}
