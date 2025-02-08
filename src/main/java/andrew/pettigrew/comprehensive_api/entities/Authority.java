package andrew.pettigrew.comprehensive_api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false)
    User user;

    @Column(name = "name", nullable = false, length = 50)
    private String name;
}