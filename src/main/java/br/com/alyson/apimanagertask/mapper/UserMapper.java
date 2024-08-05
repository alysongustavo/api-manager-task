package br.com.alyson.apimanagertask.mapper;

import br.com.alyson.apimanagertask.domain.model.User;
import br.com.alyson.apimanagertask.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }
}
