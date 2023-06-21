package com.blogs_apps_api.globalExceptions;

public class ApiException extends RuntimeException{
    public ApiException(String message)
    {
        super(message);
    }

    public ApiException()
    {
        super();
    }
}
