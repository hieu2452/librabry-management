package com.demo.book.domain.dto;

import com.demo.book.helper.annotation.Password;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class StaffDto extends UserDto {
    private String username;
    @Password(minLength = 4, hasUppercase = true, hasLowercase = true, hasNumber = true, hasSpecial = true)
    private String password;
}
