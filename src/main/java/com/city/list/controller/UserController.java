package com.city.list.controller;

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
import java.util.Arrays;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * User specific Endpoints
 * */
@CrossOrigin
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private IUserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<UserDTO>> addUser (@RequestBody @NotNull UserDTO user) {

        UserDTO userDTO = userService.addNewUser(user);
        userDTO.add(linkTo(methodOn(UserController.class).addUser(user)).withSelfRel());

        ResponseDTO responseDTO = new ResponseDTO(Arrays.asList(userDTO), null);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }

    @CrossOrigin(origins = "http://localhost:80")
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid UserAuthRequestDTO request) {

            String token = userService.login(request);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token).build();

    }




}
