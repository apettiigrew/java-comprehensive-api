package andrew.pettigrew.comprehensive_api.jsonapi.requests;

import andrew.pettigrew.comprehensive_api.ResourceTypes;
import andrew.pettigrew.comprehensive_api.dtos.UserDto;
import andrew.pettigrew.comprehensive_api.jsonapi.CreateResource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest implements CreateResource<UserDto> {

    @Pattern(regexp = ResourceTypes.USERS)
    @NotNull
    private final String type = ResourceTypes.USERS;

    @Valid
    @NotNull
    private UserDto attributes;

    @Override
    public UserDto generateDto() {
        return attributes;
    }
}
