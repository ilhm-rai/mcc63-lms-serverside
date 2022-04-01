package co.id.mii.mcc63lmsserverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.id.mii.mcc63lmsserverside.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
