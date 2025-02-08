package andrew.pettigrew.comprehensive_api.controllers;

import andrew.pettigrew.comprehensive_api.constant.ApplicationConstants;
import andrew.pettigrew.comprehensive_api.dtos.UserLoginDto;
import andrew.pettigrew.comprehensive_api.dtos.UserRegisterDto;
import andrew.pettigrew.comprehensive_api.jsonapi.*;
import andrew.pettigrew.comprehensive_api.jsonapi.requests.CreateRequest;
import andrew.pettigrew.comprehensive_api.jsonapi.requests.UserLoginRequest;
import andrew.pettigrew.comprehensive_api.jsonapi.requests.UserRegisterRequest;
import andrew.pettigrew.comprehensive_api.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api", produces = JsonApiConstants.JSON_API_CONTENT_TYPE)
@AllArgsConstructor
@Validated
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final Environment env;

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public SingleResourceResponse<UserResource> registerUser(final @RequestBody @Validated CreateRequest<UserRegisterRequest> requestData) {
        UserRegisterDto userRegisterDto = requestData.getData().generateDto();

        String hashedPassword = passwordEncoder.encode(userRegisterDto.getPassword());
        userRegisterDto.setPassword(hashedPassword);

        final var user = userService.registerUser(userRegisterDto);

        return new SingleResourceResponse<>(UserResource.toResource(user));
    }

    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.CREATED)
    public TokenResource loginUsers(final @RequestBody @Validated CreateRequest<UserLoginRequest> loginRequest) {
        UserLoginDto uerLoginDto = loginRequest.getData().generateDto();
        String jwt = "";
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(uerLoginDto.getUsername(),
                uerLoginDto.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if(null != authenticationResponse && authenticationResponse.isAuthenticated()) {
            if (null != env) {
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                        ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                jwt = Jwts.builder().issuer("java-comprehensive-api").subject("JWT Token")
                        .claim("username", authenticationResponse.getName())
                        .claim("authorities", authenticationResponse.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new java.util.Date())
                        .expiration(new java.util.Date((new java.util.Date()).getTime() + 30000000))
                        .signWith(secretKey).compact();
            }
        }

        var response = new LoginResponseDto(HttpStatus.OK.getReasonPhrase(), jwt);
        return TokenResource.toResource(response);
    }

}
