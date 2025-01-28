package andrew.pettigrew.comprehensive_api.jsonapi.requests;

import andrew.pettigrew.comprehensive_api.ResourceTypes;
import andrew.pettigrew.comprehensive_api.dtos.UserRegisterDto;
import andrew.pettigrew.comprehensive_api.jsonapi.CreateResource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest implements CreateResource<UserRegisterDto> {

    @Pattern(regexp = ResourceTypes.USERS)
    @NotNull
    private final String type = ResourceTypes.USERS;

    @Valid
    @NotNull
    private UserRegisterDto attributes;

    @Override
    public UserRegisterDto generateDto() {
        return attributes;
    }
}
