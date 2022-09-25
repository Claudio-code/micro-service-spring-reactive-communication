package com.user.service.service;

import com.user.service.dto.UserDTO;
import com.user.service.factory.UserDTOFactory;
import com.user.service.factory.UserFactory;
import com.user.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Flux<UserDTO> getAll() {
        return userRepository.findAll()
                .map(UserDTOFactory::make);
    }

    public Mono<UserDTO> getUserId(final Integer id) {
        return userRepository.findById(id)
                .map(UserDTOFactory::make);
    }

    public Mono<UserDTO> createUser(final Mono<UserDTO> userDTOMono) {
        return userDTOMono.map(UserFactory::make)
                .flatMap(userRepository::save)
                .map(UserDTOFactory::make);
    }

    public Mono<UserDTO> updateUser(final Integer id, final Mono<UserDTO> userDTOMono) {
        return userRepository.findById(id)
                .flatMap(user -> userDTOMono.map(userDTO -> UserFactory.make(id, userDTO)))
                .flatMap(userRepository::save)
                .map(UserDTOFactory::make);
    }

    public Mono<Void> deleteUser(final Integer id) {
        return userRepository.deleteById(id);
    }

}
