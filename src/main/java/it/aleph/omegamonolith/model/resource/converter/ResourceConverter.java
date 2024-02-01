package it.aleph.omegamonolith.model.resource.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.aleph.omegamonolith.exception.ResourceGenerationException;
import it.aleph.omegamonolith.exception.ResourceReadingException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;
@Converter
@RequiredArgsConstructor
public class ResourceConverter implements AttributeConverter<Map<String, Object>, String> {

    private final ObjectMapper objectMapper;


    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        String bookResourceJson;
        try{
            bookResourceJson = objectMapper.writeValueAsString(stringObjectMap);
        }catch(JsonProcessingException e){
            throw ResourceGenerationException.builder().message("An error occurred during Json parsing").error(e.getMessage()).build();
        }
        return bookResourceJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String book) {
        Map<String, Object> bookResource;
        try{
            bookResource = objectMapper.readValue(book, new TypeReference<Map<String, Object>>() {});
        }catch (IOException e){
            throw ResourceReadingException.builder().message("An error occurred during the reading of the Json resource").error(e.getMessage()).build();
        }
        return bookResource;
    }
}
