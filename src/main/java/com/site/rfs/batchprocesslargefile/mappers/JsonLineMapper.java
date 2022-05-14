package com.site.rfs.batchprocesslargefile.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.file.LineMapper;

public class JsonLineMapper<T> implements LineMapper<T> {

    private Class<? extends T> targetType;

    private ObjectMapper objectMapper;

    public JsonLineMapper(Class<? extends T> targetType){
        this.objectMapper = new ObjectMapper();
        this.targetType = targetType;
    }

    @Override
    public T mapLine(String line, int lineNumber) throws Exception {
        return objectMapper.readValue(line, targetType);
    }


    public T mapLine(String line) throws Exception {
        return objectMapper.readValue(line, targetType);
    }

}
