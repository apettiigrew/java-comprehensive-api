package andrew.pettigrew.comprehensive_api.jsonapi;

public interface CreateResource<T extends ResourceDto>{

    T generateDto();
}
