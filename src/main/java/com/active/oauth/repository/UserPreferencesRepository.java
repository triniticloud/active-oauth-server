package com.active.oauth.repository;

import com.active.oauth.model.UserPreferences;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Pritesh Soni
 *
 */
@Transactional
@Repository
public interface UserPreferencesRepository extends CrudRepository<UserPreferences, String> {

  public List<UserPreferences> findAll();

  public Optional<UserPreferences> findById(String username);

}
