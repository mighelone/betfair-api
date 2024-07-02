package com.mvasce.betfair;

import com.betfair.esa.swagger.model.MarketChange;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.avro.Schema;

public class ConvertToAvro {
    private final AvroMapper mapper;
    private final AvroSchemaGenerator generator;

    public ConvertToAvro() {
        mapper = AvroMapper.builder().build();
        mapper.registerModule(new JavaTimeModule());
        generator = new AvroSchemaGenerator();
        generator.enableLogicalTypes();
    }

    public Schema generateSchema() throws JsonMappingException {
        mapper.acceptJsonFormatVisitor(MarketChange.class, generator);
        return generator.getGeneratedSchema().getAvroSchema();
    }

    public String generateSchemaString() throws JsonMappingException {
        return generateSchema().toString(true);
    }
}
