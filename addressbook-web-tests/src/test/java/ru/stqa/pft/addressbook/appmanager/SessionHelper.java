package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.By.name;
import static org.openqa.selenium.By.xpath;

public class SessionHelper extends HelperBase {

  public SessionHelper(WebDriver wd) {
    super(wd);
  }

  public void login(String username, String password) {
    type(name("user"), username);
    type(name("pass"), password);
    click(xpath("//input[@value='Login']"));
  }
}