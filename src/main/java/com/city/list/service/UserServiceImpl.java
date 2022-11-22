package com.city.list.service;

import com.city.list.consts.ErrorConst;
import com.city.list.dto.UserAuthRequestDTO;
import com.city.list.dto.UserDTO;
import com.city.list.entity.schema.User;
import com.city.list.exception.UnAuthorizedException;
import com.city.list.exception.UnExpectedErrorOccurredException;
import com.city.list.repository.specification.IUserRepository;
import com.city.list.service.specification.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;


/**
 * Implementation of IUserService
 * */
@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtEncoder jwtEncoder;

    /**
     * Save new user and return safely copied UserDTO
     * @param user UserDTO
     * @return UserDTO
     * */
    @Override
    public UserDTO addNewUser(final UserDTO user) {
        UserDTO userDTO = null;
        try {

            User userInsert = new User();
            userInsert.setEmail(user.getEmail());
            userInsert.setUsername(user.getUsername());
            userInsert.setFirstName(user.getFirstName());
            userInsert.setLastName(user.getLastName());
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            userInsert.setPassword(encodedPassword);
            userInsert.setTele(user.getTele());
            userInsert.setUserRole(user.getUserRole());

            User userInserted = userRepository.save(userInsert);
            userDTO = new UserDTO(userInserted);
            return userDTO;
        } catch (DataIntegrityViolationException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new UnExpectedErrorOccurredException(ErrorConst.UNEXPECTED_ERROR_OCCURRED_MESSAGE,
                    exception.getCause());
        }
    }

    /**
     * Login user
     * @param userDTO UserAuthRequestDTO
     * @return String
     * */
    @Override
    public String login(UserAuthRequestDTO userDTO) {
        String token = null;
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
            User user = (User) authentication.getPrincipal();

            Instant now = Instant.now();
            Long expiry = 36000L;

            String scope = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(joining(" "));

            JwtClaimsSet claims =
                    JwtClaimsSet.builder()
                            .issuer("city-list-app")
                            .issuedAt(now)
                            .expiresAt(now.plusSeconds(expiry))
                            .subject(format("%s,%s", user.getId(), user.getUsername()))
                            .claim("roles", scope)
                            .claim("userid", user.getUserId())
                            .build();
            token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
            return token;
        } catch (Exception exception) {
            throw new UnAuthorizedException(ErrorConst.UNAUTHORIZED_MESSAGE, exception.getCause());
        }
    }

}
