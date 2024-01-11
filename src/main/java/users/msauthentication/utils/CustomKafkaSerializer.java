package users.msauthentication.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.context.annotation.Configuration;
import users.msauthentication.dtos.Message;

import java.util.Map;

@Slf4j
@Configuration
public class CustomKafkaSerializer implements Serializer<Message<?>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, Message<?> data) {
        try {
            if (data == null) {
                log.warn("Null received at serializing");
                return new byte[0];
            }
            log.warn("Serializing...");
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing MessageDTO to byte[]");
        }
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Message<?> data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
