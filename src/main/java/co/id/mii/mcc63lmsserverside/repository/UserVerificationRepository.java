package co.id.mii.mcc63lmsserverside.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.id.mii.mcc63lmsserverside.model.UserVerification;

@Repository
public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {

  Optional<UserVerification> findByToken(String token);
}
