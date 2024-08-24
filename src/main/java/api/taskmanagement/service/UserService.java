package api.taskmanagement.service;

import api.taskmanagement.dto.UserDTO;
import api.taskmanagement.exception.UserNotFoundException;
import api.taskmanagement.model.User;
import api.taskmanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(UserDTO userDTO) {
        User user = mapDtoToEntity(userDTO);
        return userRepository.save(user);
    }

    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional
    public User updateUser(int id, UserDTO userDTO) {
       User existingUser = userRepository.findById(id)
               .orElseThrow(() -> new UserNotFoundException("User not found"));

       updateEntityFromDto(existingUser, userDTO);
       return userRepository.save(existingUser);
    }

    public void deleteUser(int id) {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException("User with ID " + id + " not found");

        userRepository.deleteById(id);
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    private User mapDtoToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    private void updateEntityFromDto(User user, UserDTO userDTO) {
        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
    }
}
