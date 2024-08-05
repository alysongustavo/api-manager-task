package br.com.alyson.apimanagertask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String name;


}
