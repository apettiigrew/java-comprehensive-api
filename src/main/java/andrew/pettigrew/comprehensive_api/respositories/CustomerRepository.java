package andrew.pettigrew.comprehensive_api.respositories;

import andrew.pettigrew.comprehensive_api.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT c.* FROM customers c where c.uuid = :uuid AND c.deleted_at is NULL",
            nativeQuery = true)
    Optional<Customer> findByUuid(@Param("uuid") UUID uuid);

    @Query(value = "SELECT c.* FROM customers c where c.username = :username AND c.deleted_at is NULL",
            nativeQuery = true)
    Optional<Customer> findByUserName(@Param("username") String username);
}