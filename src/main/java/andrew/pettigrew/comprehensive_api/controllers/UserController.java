package andrew.pettigrew.comprehensive_api.controllers;

import andrew.pettigrew.comprehensive_api.ResourceTypes;
import andrew.pettigrew.comprehensive_api.dtos.UserDto;
import andrew.pettigrew.comprehensive_api.dtos.UserRegisterDto;
import andrew.pettigrew.comprehensive_api.entities.User;
import andrew.pettigrew.comprehensive_api.jsonapi.JsonApiConstants;
import andrew.pettigrew.comprehensive_api.jsonapi.MultipleResourceResponse;
import andrew.pettigrew.comprehensive_api.jsonapi.SingleResourceResponse;
import andrew.pettigrew.comprehensive_api.jsonapi.UserResource;
import andrew.pettigrew.comprehensive_api.jsonapi.requests.*;
import andrew.pettigrew.comprehensive_api.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/" + ResourceTypes.USERS, produces = JsonApiConstants.JSON_API_CONTENT_TYPE)
@AllArgsConstructor
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{uuid}")
    public SingleResourceResponse<UserResource> getUserByUuid(final @PathVariable("uuid") UUID uuid) {
        User user = userService.getUserByUuid(uuid);
        return new SingleResourceResponse<>(UserResource.toResource(user));
    }

    @GetMapping
    public MultipleResourceResponse<UserResource> getAllUsers(@PageableDefault(size = 10, sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<User> users = userService.getAllUsers(pageable);

        final Page<UserResource> userResourcePage = new PageImpl<>(
                users.getContent()
                        .stream()
                        .map(UserResource::toResource)
                        .collect(Collectors.toList()),
                users.getPageable(),
                users.getTotalElements()
        );
        return new MultipleResourceResponse<>(userResourcePage);
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public SingleResourceResponse<UserResource> registerUser(final @RequestBody @Validated CreateRequest<UserRegisterRequest> requestData) {
        UserRegisterDto userRegisterDto = requestData.getData().generateDto();

        String hashedPassword = passwordEncoder.encode(userRegisterDto.getPassword());
        userRegisterDto.setPassword(hashedPassword);

        final var user = userService.registerUser(userRegisterDto);

        return new SingleResourceResponse<>(UserResource.toResource(user));
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
