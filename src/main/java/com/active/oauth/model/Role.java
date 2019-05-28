package com.active.oauth.model;

import javax.persistence.*;

/**
 * @author Pritesh Soni
 *
 */
@Entity
@Table(name = "role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "role_id")
  private int roleId;

  @Column(name = "role")
  private String userRole;

  public Role() {
    // empty because of serialization
  }

  public int getRoleId() {
    return roleId;
  }

  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

  public String getUserRole() {
    return userRole;
  }

  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }
}
