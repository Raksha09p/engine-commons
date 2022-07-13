package com.walmart.enginecommons.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SurveyFormApiCommunicationFailedException extends SurveyFormException{
    public SurveyFormApiCommunicationFailedException(String message, Throwable e) { super(message, e); }
}
