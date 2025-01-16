package andrew.pettigrew.comprehensive_api.jsonapi.requests;

import andrew.pettigrew.comprehensive_api.jsonapi.CreateResource;
import andrew.pettigrew.comprehensive_api.jsonapi.ResourceDto;
import jakarta.validation.Valid;
import lombok.Getter;

/**
 * An incoming update request. Can be used to clearly define a request as an incoming update request and to limit
 * allowed types
 * @param <T> The resource for the create request
 */
@Getter
public class UpdateRequest<T extends CreateResource<? extends ResourceDto>> {

    /**
     * The resource that this request contains
     */
    @Valid
    T data;
}
