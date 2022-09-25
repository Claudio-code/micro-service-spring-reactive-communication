package com.user.service.factory;

import com.user.service.dto.UserDTO;
import com.user.service.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFactory {

    public static User make(final UserDTO userDTO) {
        return User
                .builder()
                .balance(userDTO.balance())
                .name(userDTO.name())
                .build();
    }

    public static User make(final Integer id, final UserDTO userDTO) {
        return User
                .builder()
                .id(id)
                .balance(userDTO.balance())
                .name(userDTO.name())
                .build();
    }

}
