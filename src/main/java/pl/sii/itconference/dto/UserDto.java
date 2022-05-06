package pl.sii.itconference.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class UserDto {
    @Size(min = 4, max = 32)
    private String username;

    @Email
    private String email;
}
