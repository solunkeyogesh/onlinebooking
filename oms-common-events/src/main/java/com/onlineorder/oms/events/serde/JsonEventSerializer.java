package com.onlineorder.oms.events.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class JsonEventSerializer<T> implements Serializer<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            if (data == null) {
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing event", e);
        }
    }
}
