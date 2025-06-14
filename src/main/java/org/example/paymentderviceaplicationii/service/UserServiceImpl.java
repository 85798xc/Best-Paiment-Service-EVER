package org.example.paymentderviceaplicationii.service;

import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.exception.UserListNotFoundException;
import org.example.paymentderviceaplicationii.exception.UserNotFoundException;
import org.example.paymentderviceaplicationii.model.User;
import org.example.paymentderviceaplicationii.model.dto.UserDTO;
import org.example.paymentderviceaplicationii.model.enums.Role;
import org.example.paymentderviceaplicationii.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("by id: " + userId));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("by username: " + username);
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();

        if(userList.isEmpty()){
            throw new UserListNotFoundException();
        }

        return userList;
    }

    @Override
    public void deleteUserById(Long userId) {
        User user = getUserById(userId);

        userRepository.delete(user);
    }

    @Override
    public void deleteUserByEmail(String email) {
        User user = getUserByEmail(email);

        userRepository.delete(user);
    }

    @Override
    public void deleteAllUsers() {
        if(userRepository.count() == 0) {
            throw new UserListNotFoundException();
        }

        userRepository.deleteAll();
    }

    public User updateUser(Long id, UserDTO userDTO) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("by id: " + id));
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("by id: " + id));
        userRepository.deleteById(id);
    }
}
