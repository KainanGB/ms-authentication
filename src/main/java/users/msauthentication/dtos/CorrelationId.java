package users.msauthentication.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CorrelationId {

    private final String id;

    public CorrelationId() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return id;
    }
}
