package pl.sii.itconference.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.sii.itconference.repo.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;
}
