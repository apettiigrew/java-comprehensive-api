package andrew.pettigrew.comprehensive_api.jsonapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String status;
    private String jwt;
}
