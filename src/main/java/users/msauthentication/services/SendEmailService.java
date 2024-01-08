package users.msauthentication.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import users.msauthentication.dtos.response.MessageDTO;

import java.util.UUID;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class SendEmailService {

    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;

    public void sendEmail(MessageDTO message) {
        final ProducerRecord<String, MessageDTO> newRecord =
            new ProducerRecord<>("MS_AUTHENTICATION_EVENT_SEND_MAIL", generateTopicKey(), message);

        log.info(format("Received and sent email::%s", newRecord));
        kafkaTemplate.send(newRecord);
    }

    public void sendMessage(MessageDTO message) {
        final ProducerRecord<String, MessageDTO> newRecord =
            new ProducerRecord<>("MS_AUTHENTICATION_EVENT_SEND_MAIL", generateTopicKey(), message);

        log.info(format("Received and sent message::%s", newRecord));
        kafkaTemplate.send(newRecord);
    }

    private String generateTopicKey() {
        var randomNum = UUID.randomUUID();
        return "MS_AUTHENTICATION" + randomNum;
    }
}
