package WoWSFT.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;

public class CustomMJ2HMC extends MappingJackson2HttpMessageConverter implements InitializingBean
{
    private ObjectMapper objectMapper;

    public void afterPropertiesSet()
    {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public void setObjectMapper(ObjectMapper objectMapper)
    {
        super.setObjectMapper(objectMapper);
        this.objectMapper = objectMapper;
    }

    public ObjectMapper getObjectMapper()
    {
        return objectMapper;
    }

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException
    {
        outputMessage.getHeaders().set("Content-Type", "application/json");

        Object returnMessage = convert(object);
        JsonEncoding jsonEncoding = JsonEncoding.UTF8;
        JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputMessage.getBody(), jsonEncoding);
        ObjectWriter objectWriter = objectMapper.writer();
        objectWriter.writeValue(jsonGenerator, returnMessage);
    }

    private Object convert(Object o)
    {
        LinkedHashMap<String, Object> lhm = new LinkedHashMap<>();

        if (o instanceof CustomMessage) {
            lhm.put("status", ((CustomMessage) o).getStatus());
            lhm.put("message", ((CustomMessage) o).getMessage());
        } else if (o instanceof LinkedHashMap && ((LinkedHashMap) o).containsKey("status")) {
            lhm.put("status", ((LinkedHashMap) o).get("status").toString());
            lhm.put("message", ((LinkedHashMap) o).get("message"));
        } else {
            lhm.put("status", "200");
            lhm.put("result", o);
        }

        return lhm;
    }
}
