package com.org.feeprocess.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.feeprocess.dto.EnrollmentEventDTO;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class EnrollmentEventDTODeserializer implements Deserializer<EnrollmentEventDTO> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No additional configuration is needed
    }

    @Override
    public EnrollmentEventDTO deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                return null;
            }
            return objectMapper.readValue(data, EnrollmentEventDTO.class);
        } catch (IOException e) {
            throw new SerializationException("Error deserializing EnrollmentEventDTO", e);
        }
    }

    @Override
    public void close() {
        // No resources need to be released
    }
}

