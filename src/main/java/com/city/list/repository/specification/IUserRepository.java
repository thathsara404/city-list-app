package com.city.list.repository.specification;

import com.city.list.entity.schema.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Represent the specification which includes user related operations.
 * */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    public abstract User getByUserId(final UUID userId);

    public abstract Optional<User> getByUsername(final String username);

}
