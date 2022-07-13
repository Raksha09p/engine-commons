package com.walmart.enginecommons.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SurveyFormSerializationException extends SurveyFormException{
    public SurveyFormSerializationException(String errorMessage, Throwable t){
        super(errorMessage, t);
    }
}
