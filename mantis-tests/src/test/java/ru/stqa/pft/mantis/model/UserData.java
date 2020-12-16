package ru.stqa.pft.mantis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mantis_user_table")
public class UserData {

  @Id
  @Column(name = "id")
  private int id = Integer.MAX_VALUE;

  @Column(name = "username")
  private String login;

  @Column(name = "email")
  private String email;
  private String password;


  public UserData withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserData withLogin(String login) {
    this.login = login;
    return this;
  }

  public UserData withPassword(String password) {
    this.password = password;
    return this;
  }

  public UserData withId(int id) {
    this.id = id;
    return this;
  }

  public String getLogin() {
    return login;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public int getId() {
    return id;
  }

  @Override
  public String toString() {
    return "UserData{" +
            "id=" + id +
            ", login='" + login + '\'' +
            ", email='" + email + '\'' +
            '}';
  }
}
