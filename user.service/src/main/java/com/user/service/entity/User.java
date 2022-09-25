package com.user.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Builder
@Getter
@Table("users")
public class User {

    @Id
    private Integer id;
    private String name;
    private Integer balance;

}
