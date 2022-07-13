package com.walmart.enginecommons.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SurveyFormException extends Exception{
    public SurveyFormException(String message){super(message);}
    public SurveyFormException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
