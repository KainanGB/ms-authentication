package users.msauthentication.services;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import users.msauthentication.dtos.CorrelationId;
import users.msauthentication.dtos.Message;
import users.msauthentication.dtos.request.CreateUserDTO;
import users.msauthentication.dtos.request.VerifyEmailDTO;
import users.msauthentication.dtos.response.EmailDTO;
import users.msauthentication.dtos.response.UserDTO;
import users.msauthentication.entities.UsersEntity;
import users.msauthentication.exceptions.UserAlreadyVerifiedException;
import users.msauthentication.exceptions.WrongEmailVerifierCodeException;
import users.msauthentication.repositories.UsersRepository;

import java.util.Objects;
import java.util.Random;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final SendEmailService sendEmailService;

    public UserDTO register(@RequestBody @Valid CreateUserDTO data) {

        final var randomNumber = 1000 + new Random().nextLong(9000);

        final UsersEntity user = UsersEntity
            .builder()
            .email(data.email())
            .password(data.password())
            .role(data.role())
            .verify_token(randomNumber)
            .verified(0)
            .build();


        final Message<EmailDTO> message = new Message<>(
            new CorrelationId().toString(),
            new EmailDTO(
                "kainanytbr@gmail.com",
                data.email(),
                "Please confirm your email.",
                "your verification code: " + randomNumber)
        );

        usersRepository.save(user);
        sendEmailService.sendEmail(message);

        return new UserDTO(user.getId(), user.getEmail(), user.getCreated_at().toString());
    }

    public void verifyEmail(@RequestBody VerifyEmailDTO request) throws UserAlreadyVerifiedException, WrongEmailVerifierCodeException {
        final var user = usersRepository.findById(request.user_id()).orElseThrow();
        if (user.getVerified() == 1) {
            throw new UserAlreadyVerifiedException("user is already verified");
        } else if (!Objects.equals(user.getVerify_token(), request.code())) {
            throw new WrongEmailVerifierCodeException("wrong code, try again");
        }

        user.setVerified(1);
        usersRepository.save(user);
    }

    public UserDTO getById(@PathVariable Long id) {
        final var randomNumber = 1000 + new Random().nextLong(9000);

        final Message<EmailDTO> message = new Message<>(
            new CorrelationId().toString(),
            new EmailDTO(
                "kainanytbr@gmail.com",
                "kainanytbr2@gmail.com",
                "Please confirm your email.",
                "your verification code: " + randomNumber)
        );

        sendEmailService.sendMessage(message);

        UsersEntity user = usersRepository.findById(id).orElseThrow();

        return  new UserDTO(user.getId(), user.getEmail(), user.getCreated_at().toString());
    }

}
