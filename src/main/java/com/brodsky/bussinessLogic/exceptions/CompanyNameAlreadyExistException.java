package com.brodsky.bussinessLogic.exceptions;

@SuppressWarnings("serial")
public class CompanyNameAlreadyExistException extends Exception {

    public String getMessage() {
        return "Company name already exists.";
    }

}
