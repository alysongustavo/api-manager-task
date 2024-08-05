package br.com.alyson.apimanagertask.dto;

import br.com.alyson.apimanagertask.domain.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSaveDto {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Size(max = 255, message = "Email must be less than 255 characters")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(max = 255, message = "Password must be less than 255 characters")
    private String password;

    private Status status = Status.ACTIVE;
}
