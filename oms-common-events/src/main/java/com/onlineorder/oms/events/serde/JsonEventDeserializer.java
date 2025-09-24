package com.onlineorder.oms.events.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class JsonEventDeserializer<T> implements Deserializer<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> type;

    public JsonEventDeserializer(Class<T> type) {
        this.type = type;
    }

    @Override
    public T deserialize(String topic, byte[] bytes) {
        try {
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            return objectMapper.readValue(bytes, type);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing event", e);
        }
    }
}