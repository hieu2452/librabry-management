package com.demo.book.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class StaffDto extends UserDto {
    private String username;
    private String password;
}
