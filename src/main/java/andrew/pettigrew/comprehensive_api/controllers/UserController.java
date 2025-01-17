package andrew.pettigrew.comprehensive_api.controllers;

import andrew.pettigrew.comprehensive_api.ResourceTypes;
import andrew.pettigrew.comprehensive_api.dtos.UserDto;
import andrew.pettigrew.comprehensive_api.entities.User;
import andrew.pettigrew.comprehensive_api.jsonapi.JsonApiConstants;
import andrew.pettigrew.comprehensive_api.jsonapi.MultipleResourceResponse;
import andrew.pettigrew.comprehensive_api.jsonapi.SingleResourceResponse;
import andrew.pettigrew.comprehensive_api.jsonapi.UserResource;
import andrew.pettigrew.comprehensive_api.jsonapi.requests.CreateRequest;
import andrew.pettigrew.comprehensive_api.jsonapi.requests.UpdateRequest;
import andrew.pettigrew.comprehensive_api.jsonapi.requests.UserCreateRequest;
import andrew.pettigrew.comprehensive_api.jsonapi.requests.UserUpdateRequest;
import andrew.pettigrew.comprehensive_api.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/" + ResourceTypes.USERS, produces = JsonApiConstants.JSON_API_CONTENT_TYPE)
@AllArgsConstructor
@Validated
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping("/{uuid}")
    public SingleResourceResponse<UserResource> getUserByUuid(final @PathVariable("uuid") UUID uuid) {
        User user = userService.getUserByUuid(uuid);
        return new SingleResourceResponse<>(UserResource.toResource(user));
    }

    @GetMapping
    public MultipleResourceResponse<UserResource> getAllUsers() {
        List<User> user = userService.getAllUsers();

        return new MultipleResourceResponse<>(
                user.stream().map(UserResource::toResource)
                .collect(Collectors.toList()));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public SingleResourceResponse<UserResource> createUser(final @RequestBody @Validated CreateRequest<UserCreateRequest> requestData) {
        UserDto userDto = requestData.getData().generateDto();
        final var user = userService.createUser(userDto);

        return new SingleResourceResponse<>(UserResource.toResource(user));
    }

    @PatchMapping("/{uuid}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public SingleResourceResponse<UserResource> updateUser(final @PathVariable UUID uuid, @RequestBody @Validated UpdateRequest<UserUpdateRequest> userDto) {
        User updatedUser = userService.updateUser(uuid, userDto.getData().generateDto());
        return new SingleResourceResponse<>(UserResource.toResource(updatedUser));
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(final @PathVariable UUID uuid) {
        try {
            userService.deleteUser(uuid);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
