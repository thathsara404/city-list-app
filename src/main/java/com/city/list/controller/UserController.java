package com.city.list.controller;

import com.city.list.dto.CityDTO;
import com.city.list.dto.ResponseDTO;
import com.city.list.dto.UserAuthRequestDTO;
import com.city.list.dto.UserDTO;
import com.city.list.service.specification.IUserService;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * User specific Endpoints
 * */

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:80")
public class UserController {

    private IUserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<UserDTO>> addUser (@RequestBody @NotNull UserDTO user) {

        UserDTO userDTO = userService.addNewUser(user);
        userDTO.add(linkTo(methodOn(UserController.class).addUser(user)).withSelfRel());

        ResponseDTO responseDTO = new ResponseDTO(Arrays.asList(userDTO), null);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid UserAuthRequestDTO request) {

            String token = userService.login(request);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token).build();

    }

    @GetMapping(path = "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<UserDTO>> getUserById (@PathVariable @NotNull UUID userId) {

        UserDTO userDTO = userService.getUser(userId);
        userDTO.add(linkTo(methodOn(UserController.class).getUserById(userId)).withSelfRel());
        List<UserDTO> user = new ArrayList(Arrays.asList(userDTO));
        ResponseDTO responseDTO = new ResponseDTO(user, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

    }

}
