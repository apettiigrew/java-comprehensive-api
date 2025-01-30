package andrew.pettigrew.comprehensive_api.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.UniqueElements;

import java.sql.Types;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.CHAR)
    private UUID uuid;

    @Column(name="username", nullable = false)
    @UniqueElements
    private String username;

    @Column(name="role", nullable = false)
    private String role;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    @Column(name="deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

}
