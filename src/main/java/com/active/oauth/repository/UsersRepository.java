package com.active.oauth.repository;

import com.active.oauth.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Pritesh Soni
 *
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {

  Optional<Users> findByName(String username);

}
