package andrew.pettigrew.comprehensive_api.jsonapi;

import andrew.pettigrew.comprehensive_api.ResourceTypes;
import andrew.pettigrew.comprehensive_api.dtos.UserDto;
import andrew.pettigrew.comprehensive_api.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
public class UserResource implements Resource<UserDto> {
    private final String type = ResourceTypes.USERS;
    private UUID id;
    private UserDto attributes;

    public static UserResource toResource(final User user){
        if(user == null){
            return null;
        }

        final var modelMapper = new ModelMapper();
        final var attributes = modelMapper.map(user,UserDto.class);

        return new UserResource(user.getUuid(),attributes);
    }
}
