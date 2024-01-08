package users.msauthentication.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {
    @Bean
    public NewTopic sendEmailTopic() {
        return TopicBuilder.name("MS_AUTHENTICATION_EVENT_SEND_MAIL")
            .partitions(3)
            .replicas(3).build();
    }

}
