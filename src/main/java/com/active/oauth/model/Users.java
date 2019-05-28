package com.active.oauth.model;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Pritesh Soni
 *
 */
@Entity
@Table(name = "user")
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private int id;

  @Column(name = "email")
  private String email;

  @Column(name = "name")
  private String name;

  @Column(name = "password")
  private String password;

  @Column(name = "last_name")
  private String lastName;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns =
  @JoinColumn(name = "user_id"), inverseJoinColumns =
  @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  public Users() {
  }

  public Users(Users users) {
    this.email = users.email;
    this.id = users.id;
    this.lastName = users.lastName;
    this.name = users.name;
    this.password = users.password;
    this.roles = users.roles;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

}
