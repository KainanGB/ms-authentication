package users.msauthentication;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;


@SpringBootApplication
@Profile("dev")
public class MsAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAuthenticationApplication.class, args);
	}

}
