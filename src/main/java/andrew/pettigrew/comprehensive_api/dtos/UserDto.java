package andrew.pettigrew.comprehensive_api.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
