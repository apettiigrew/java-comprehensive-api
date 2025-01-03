package andrew.pettigrew.comprehensive_api.respositories;

import andrew.pettigrew.comprehensive_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}