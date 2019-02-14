/**
 @author nick.drakopoulos
 */
package com.ef.parser.jackson;

import com.ef.parser.exception.AppError;
import com.ef.parser.exception.ApplicationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class InstantDeserializer extends JsonDeserializer<Instant> {

    private SimpleDateFormat formatter =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public Instant deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token == JsonToken.VALUE_STRING) {
            String value = jp.getText();
            if (value == null || value.trim().isEmpty()) {
                return null;
            }
            try {
                Date date = formatter.parse(value);
                return date.toInstant();
            } catch (Exception e) {
                throw new ApplicationException(AppError.INVALID_FIELD, "Value it's not of the correct format");
            }
        } else if (token == JsonToken.VALUE_NULL) {
            return null;
        } else {
            throw new ApplicationException(AppError.INVALID_FIELD, "Value type it's not of the correct");
        }
    }
}