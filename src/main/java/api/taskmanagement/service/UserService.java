package api.taskmanagement.service;

import api.taskmanagement.exception.UserNotFoundException;
import api.taskmanagement.model.Task;
import api.taskmanagement.model.User;
import api.taskmanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional
    public User updateUser(int id, User user) {
       User existingUser = userRepository.findById(id)
               .orElseThrow(() -> new UserNotFoundException("User not found"));

       existingUser.setUsername(user.getUsername());
       existingUser.setEmail(user.getEmail());

       existingUser.getTasks().clear();
       if (user.getTasks() != null) {
           existingUser.getTasks().addAll(user.getTasks());

           for (Task task : user.getTasks()) {
               task.setAssignee(existingUser);
           }

       }

       return userRepository.save(existingUser);
    }

    public void deleteUser(int id) {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException("User with ID " + id + " not found");

        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
