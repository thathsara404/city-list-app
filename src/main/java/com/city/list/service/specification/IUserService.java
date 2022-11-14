package com.city.list.service.specification;

import com.city.list.dto.UserAuthRequestDTO;
import com.city.list.dto.UserDTO;

/**
 * Represent the specification which includes user related business logic operations.
 * */
public interface IUserService {

    /**
     * Save new user
     * @param user UserDTO
     * @return UserDTO
     * */
    public abstract UserDTO addNewUser(UserDTO user);

    /**
     * Login user
     * @param user UserAuthRequestDTO
     * @return String
     * */
    public abstract String login(UserAuthRequestDTO user);

}
