package com.city.list.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAuthRequestDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;


}
