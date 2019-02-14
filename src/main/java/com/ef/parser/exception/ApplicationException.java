/**
 @author nick.drakopoulos
 */
package com.ef.parser.exception;

public class ApplicationException extends RuntimeException {
    private AppError appError;

    public ApplicationException(AppError appError, String message) {
        super(message);
        this.appError = appError;
    }

    public AppError getAppError() {
        return appError;
    }

    public void setAppError(AppError appError) {
        this.appError = appError;
    }
}
