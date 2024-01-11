package users.msauthentication.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import users.msauthentication.dtos.Message;
import users.msauthentication.dtos.response.EmailDTO;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class SendEmailService {

    private final KafkaTemplate<String, Message<EmailDTO>> kafkaTemplate;

    public void sendEmail(Message<EmailDTO> message) {
        final ProducerRecord<String, Message<EmailDTO>> newRecord =
            new ProducerRecord<>(
                "MS_AUTHENTICATION_EVENT_SEND_MAIL",
                generateTopicKey(message.payload().from()),
                message
            );

        log.info(format("Received and sent email::%s", newRecord));
        kafkaTemplate.send(newRecord);
    }

    public void sendMessage(Message<EmailDTO> message) {

        final ProducerRecord<String, Message<EmailDTO>> newRecord =
            new ProducerRecord<>(
                "MS_AUTHENTICATION_EVENT_SEND_MAIL",
                generateTopicKey(message.payload().from()),
                message
            );

        log.info(format("Received and sent message::%s", newRecord));
        kafkaTemplate.send(newRecord);
    }

    private String generateTopicKey(String from) {
        return "MS_AUTHENTICATION::" + from;
    }
}
