package andrew.pettigrew.comprehensive_api.jsonapi.requests;

import andrew.pettigrew.comprehensive_api.ResourceTypes;
import andrew.pettigrew.comprehensive_api.dtos.UserLoginDto;
import andrew.pettigrew.comprehensive_api.jsonapi.CreateResource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest implements CreateResource<UserLoginDto> {

    @Pattern(regexp = ResourceTypes.USERS)
    @NotNull
    private final String type = ResourceTypes.USERS;

    @Valid
    @NotNull
    private UserLoginDto attributes;

    @Override
    public UserLoginDto generateDto() {
        return attributes;
    }
}
