package andrew.pettigrew.comprehensive_api.respositories;

import andrew.pettigrew.comprehensive_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUuidAndDeletedAtIsNull(UUID uuid);
}