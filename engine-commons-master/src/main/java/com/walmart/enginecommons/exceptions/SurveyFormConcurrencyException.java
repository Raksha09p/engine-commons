package com.walmart.enginecommons.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SurveyFormConcurrencyException extends SurveyFormException{
    public SurveyFormConcurrencyException(String message, Throwable e) { super(message, e); }
}
