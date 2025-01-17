package andrew.pettigrew.comprehensive_api.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class MultipleResourceResponse<T extends Resource<? extends ResourceDto>> {

    private final List<T> data;

    public MultipleResourceResponse(final List<T> resource){
        this.data = resource;
    }
}
