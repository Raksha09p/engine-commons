package com.walmart.enginecommons.initialization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.walmart.enginecommons.commons.annotations.*;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;



@Slf4j
public class ContractParserService {

    private ContractParserService() {
    }

    public static JSONObject getSchema(Class clazz,
                                       List<String> ignoreFields, String updatedBy) {

        JSONObject schema = new JSONObject();

        schema.put(ServiceConstants.TYPE_LABEL, ServiceConstants.OBJECT_LABEL);

        schema.put(ServiceConstants.CLAZZ, clazz.getName());

        HashMap<String, Object> properties = new HashMap<>();

        HashMap<String, Field> fields = new HashMap<>();

        getFields(clazz, fields);

        ArrayList<Field> filteredFields = new ArrayList<>(fields.values());

        for (Field f : filteredFields) {

            JsonIgnore jsonIgnore = f.getDeclaredAnnotation(JsonIgnore.class);
            JsonIgnoreProperties jsonIgnoreProperties = f.getDeclaredAnnotation(JsonIgnoreProperties.class);

            List<String> fieldsToIgnore = new ArrayList<>();

            if (jsonIgnoreProperties != null) {
                fieldsToIgnore.addAll(Arrays.asList(jsonIgnoreProperties.value()));
            }

            if (jsonIgnore == null && !ignoreFields.contains(f.getName())) {
                HashMap<String, Object> fieldProperties = new HashMap<>();

                SchemaObject schemaObject = new SchemaObject(f);
                fieldProperties.put(ServiceConstants.TYPE_LABEL, schemaObject.getType());

                if (schemaObject.getFieldType().equals(FieldType.STRING)) {
                    injectGenericFieldOnlyDecorators(properties, f, fieldProperties);

                }

                injectGenericFieldLevelDecorators(properties, f, fieldProperties);
            }
        }

        if (properties.size() > 0)
            schema.put(ServiceConstants.PROPERTIES_LABEL, properties);

        log.debug("Class {} constructed as schema. ", clazz.getName());
        return schema;
    }

    public static void injectGenericFieldOnlyDecorators(Map<String, Object> properties, Field field,
                                                        Map<String, Object> fieldProperties) {
        properties.put(field.getName(), fieldProperties);
    }

    public static void injectGenericFieldLevelDecorators(Map<String, Object> properties, Field field,
                                                         Map<String, Object> fieldProperties) {

        Tooltip tooltip = field.getDeclaredAnnotation(Tooltip.class);

        if (tooltip != null) {
            fieldProperties.put(ServiceConstants.DISPLAY_NAME_LABEL, tooltip.displayName());
            fieldProperties.put(ServiceConstants.DESCRIPTION_LABEL, tooltip.description());
        }

        Required required = field.getDeclaredAnnotation(Required.class);

        if (required != null) {
            fieldProperties.put(ServiceConstants.REQUIRED_LABEL, true);
        }

        Order order = field.getDeclaredAnnotation(Order.class);

        if (order != null) {
            fieldProperties.put(ServiceConstants.ORDER_LABEL, order.value());
        }

        Size size = field.getDeclaredAnnotation(Size.class);

        if (size != null) {
            fieldProperties.put(ServiceConstants.MIN_SIZE_LABEL, size.min());
            fieldProperties.put(ServiceConstants.MAX_SIZE_LABEL, size.max());

        }

        Pattern pattern = field.getDeclaredAnnotation(Pattern.class);

        if (pattern != null) {
            fieldProperties.put(ServiceConstants.PATTERN_LABEL, pattern.regexp());
        }



        properties.put(field.getName(), fieldProperties);
    }

    public static void getFields(Class clazz, Map<String, Field> fields) {

        for (Field f : clazz.getDeclaredFields()) {
            if (!fields.containsKey(f.getName()) && !Modifier.isFinal(f.getModifiers()) && !f.isSynthetic()) {
                fields.put(f.getName(), f);
            }
        }

        if (!clazz.getSuperclass().getName().equals("java.lang.Object")) {
            getFields(clazz.getSuperclass(), fields);
        }
    }
}