package andrew.pettigrew.comprehensive_api.respositories;

import andrew.pettigrew.comprehensive_api.entities.Invoice;
import andrew.pettigrew.comprehensive_api.enums.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {


    Page<Invoice> findByStatus(@Param("status") InvoiceStatus status, Pageable pageable);
}