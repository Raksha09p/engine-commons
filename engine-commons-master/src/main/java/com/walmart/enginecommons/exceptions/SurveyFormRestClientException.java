package com.walmart.enginecommons.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SurveyFormRestClientException extends SurveyFormException{
    public SurveyFormRestClientException(String message, Throwable e) { super(message, e); }
}
