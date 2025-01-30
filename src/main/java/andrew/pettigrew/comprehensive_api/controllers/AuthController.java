package andrew.pettigrew.comprehensive_api.controllers;

import andrew.pettigrew.comprehensive_api.dtos.UserRegisterDto;
import andrew.pettigrew.comprehensive_api.jsonapi.JsonApiConstants;
import andrew.pettigrew.comprehensive_api.jsonapi.SingleResourceResponse;
import andrew.pettigrew.comprehensive_api.jsonapi.UserResource;
import andrew.pettigrew.comprehensive_api.jsonapi.requests.CreateRequest;
import andrew.pettigrew.comprehensive_api.jsonapi.requests.UserRegisterRequest;
import andrew.pettigrew.comprehensive_api.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/register", produces = JsonApiConstants.JSON_API_CONTENT_TYPE)
@AllArgsConstructor
@Validated
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public SingleResourceResponse<UserResource> registerUser(final @RequestBody @Validated CreateRequest<UserRegisterRequest> requestData) {
        UserRegisterDto userRegisterDto = requestData.getData().generateDto();

        String hashedPassword = passwordEncoder.encode(userRegisterDto.getPassword());
        userRegisterDto.setPassword(hashedPassword);

        final var user = userService.registerUser(userRegisterDto);

        return new SingleResourceResponse<>(UserResource.toResource(user));
    }
}
