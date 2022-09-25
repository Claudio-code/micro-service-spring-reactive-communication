package com.user.service.factory;

import com.user.service.dto.UserDTO;
import com.user.service.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDTOFactory {

    public static UserDTO make(final User user) {
        return UserDTO
                .builder()
                .id(user.getId())
                .balance(user.getBalance())
                .name(user.getName())
                .build();
    }

}
