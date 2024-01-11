package users.msauthentication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import users.msauthentication.entities.UsersEntity;


@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    UserDetails findByEmail(String email);


}
