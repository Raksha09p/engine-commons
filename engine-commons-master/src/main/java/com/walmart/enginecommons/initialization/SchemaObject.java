package com.walmart.enginecommons.initialization;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;


@Data
@Slf4j
public class SchemaObject {

    private FieldType fieldType;

    public SchemaObject(Field field) {
        if (String.class.isAssignableFrom(field.getType())) {
            fieldType = FieldType.valueOf(ServiceConstants.STRING);
        }
    }

    public String getType() {

        return fieldType.toString().toLowerCase();
    }

}