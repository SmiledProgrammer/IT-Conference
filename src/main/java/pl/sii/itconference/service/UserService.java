package pl.sii.itconference.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.sii.itconference.dto.UserDto;
import pl.sii.itconference.entity.User;
import pl.sii.itconference.exception.DuplicateUsernameException;
import pl.sii.itconference.exception.ResourceNotFoundException;
import pl.sii.itconference.repo.UserRepository;
import pl.sii.itconference.utils.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class UserService {

    private final UserRepository userRepository;

    public User forceGetUser(@NotBlank String username, @Email @NotBlank String email) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!email.equals(user.getEmail())) {
                log.error("forceGetUser() called but email \"{}\" belonging to username \"{}\" was different than the given \"{}\" email.",
                        user.getEmail(), username, email);
                throw new DuplicateUsernameException("There is already a user with this username.");
            }
            log.info("forceGetUser() called successfully.");
            return user;
        } else {
            User newUser = new User(username, email);
            User userRecord = userRepository.save(newUser);
            log.info("forceGetUser() called successfully and has created a new user.");
            return userRecord;
        }
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(this::mapEntityToDto).toList();
    }

    public User getUserByUsername(@NotBlank String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isEmpty()) {
            log.error("getUserByUsername() called but couldn't find user with \"{}\" username.", username);
            throw new ResourceNotFoundException("Couldn't find user with this username.");
        }
        log.info("getUserByUsername() called successfully.");
        return userOptional.get();
    }

    public UserDto updateUser(@NotBlank String username, @Valid UserDto dto) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isEmpty()) {
            log.error("updateUser() called but couldn't find user with \"{}\" username.", username);
            throw new ResourceNotFoundException("Couldn't find user with this username.");
        }
        User user = userOptional.get();

        if (StringUtils.isNotBlank(dto.getUsername())) {
            if (userRepository.findUserByUsername(dto.getUsername()).isPresent()) {
                log.error("updateUser() called but there was already a user with \"{}\" username.", username);
                throw new ResourceNotFoundException("There is already a user with this username.");
            }
            user.setUsername(dto.getUsername());
        }
        if (StringUtils.isNotBlank(dto.getEmail())) {
            user.setEmail(dto.getEmail());
        }
        User userRecord = userRepository.save(user);
        log.info("updateUser() called successfully.");
        return mapEntityToDto(userRecord);
    }

    private UserDto mapEntityToDto(User user) {
        return new UserDto(user.getUsername(), user.getEmail());
    }
}
