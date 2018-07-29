package com.brodsky.bussinessLogic.exceptions;


@SuppressWarnings("serial")
public class CompanyEmailAlreadyExistException extends Exception {

    public String getMessage() {
        return "Company email already exists.";
    }

}
