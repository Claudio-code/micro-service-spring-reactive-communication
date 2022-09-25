package com.user.service.dto;

import lombok.Builder;

@Builder
public record UserDTO(Integer id, String name, Integer balance) {
}
