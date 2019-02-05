package WoWSFT.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CustomObjectMapper extends ObjectMapper
{
    private JsonFactory jsonFactory;

    public CustomObjectMapper(JsonFactory jsonFactory) {

        super(jsonFactory);
        this.jsonFactory = jsonFactory;

        if (jsonFactory == null) {
            throw new IllegalStateException("jsonFactory should not be null.");
        }

        jsonFactory.setCodec(this);
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
        configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, false);
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

    }
}
