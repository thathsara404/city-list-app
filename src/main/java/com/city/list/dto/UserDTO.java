package com.city.list.dto;

import com.city.list.entity.schema.User;
import com.city.list.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO extends RepresentationModel implements ISuccessResponse{

    private Long id;

    private UUID userId;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String tele;

    @NotNull
    private UserRole userRole;

    public UserDTO(User user) {
        this.setEmail(user.getEmail());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setUserId(user.getUserId());
        this.setPassword(user.getPassword());
        this.setId(user.getId());
        this.setTele(user.getTele());
        this.setUsername(user.getUsername());
        this.setUserRole(user.getUserRole());
    }

}
