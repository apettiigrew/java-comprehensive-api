package andrew.pettigrew.comprehensive_api.services;

import andrew.pettigrew.comprehensive_api.dtos.UserDto;
import andrew.pettigrew.comprehensive_api.entities.AppUser;
import andrew.pettigrew.comprehensive_api.respositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("skipNullModelMapper")
    private ModelMapper modelMapper;

    public Page<AppUser> getAllUsers(Pageable pageable ) {
        return userRepository.findAll(pageable);
    }

    public AppUser getUserByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public AppUser createUser(UserDto userDto) {
        final AppUser user =  modelMapper.map(userDto, AppUser.class);
        return userRepository.save(user);
    }

    public AppUser updateUser(UUID uuid, UserDto userDetails) {
        AppUser user = userRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("User not found"));
        modelMapper.map(userDetails,user);
        return userRepository.save(user);
    }

    public void deleteUser(UUID uuid) {
        final var today = new Date();
        AppUser user = userRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("User not found"));
       user.setDeletedAt(today);
        userRepository.save(user);
    }
}
