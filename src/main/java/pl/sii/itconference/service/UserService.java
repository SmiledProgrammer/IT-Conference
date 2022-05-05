package pl.sii.itconference.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.sii.itconference.entity.User;
import pl.sii.itconference.exception.DuplicateUsernameException;
import pl.sii.itconference.exception.ResourceNotFoundException;
import pl.sii.itconference.repo.UserRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User forceGetUser(String username, String email) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!email.equals(user.getEmail())) {
                log.info("forceGetUser() called but email \"{}\" belonging to username \"{}\" was different than the given \"{}\" email.",
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

    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isEmpty()) {
            log.info("getUserByUsername() called but couldn't find user with \"{}\" username.", username);
            throw new ResourceNotFoundException("Couldn't find user with this username.");
        }
        log.info("getUserByUsername() called successfully.");
        return userOptional.get();
    }
}
