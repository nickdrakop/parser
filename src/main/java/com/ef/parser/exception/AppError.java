/**
 @author nick.drakopoulos
 */
package com.ef.parser.exception;

public enum AppError {

    VALIDATION_ERROR(1, "Validation error"),
    FILE_NOT_FOUND(2, "File not found"),
    GENERAL_ERROR(3, "General error"),
    INVALID_FILE_FORMAT(4, "Invalid file format"),
    PARSE_ERROR(5, "Error when tried to parse the file"),
    INVALID_FIELD(6, "Invalid field");

    private final int code;
    private final String description;

    AppError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getErrorCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
