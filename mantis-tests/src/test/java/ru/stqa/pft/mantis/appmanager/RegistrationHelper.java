package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.model.UserData;

import static java.lang.String.format;
import static org.openqa.selenium.By.*;

public class RegistrationHelper extends HelperBase {

  public RegistrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), username);
    type(By.name("email"), email);
    click(cssSelector("input[value='Зарегистрироваться']"));
  }

  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(cssSelector("button[type='submit']"));
  }

  public void pressResetByAdmin(UserData user) {
    goToUsersLists();
    goToEditUserPageById(user.getId());
    resetPassword();
  }

  public void login(UserData user) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), user.getLogin());
    click(cssSelector("input[value='Войти']"));
    type(By.name("password"), user.getPassword());
    click(cssSelector("input[value='Войти']"));
  }

  public void logout() {
    wd.findElement(xpath("//div[@id='navbar-container']/div[2]/ul/li[3]/a/i[2]")).click();
    wd.findElement(linkText("Logout")).click();
  }

  public void goToUsersLists() {
    wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
  }

  public void goToEditUserPageById(int id) {
    click(cssSelector(format("a[href='manage_user_edit_page.php?user_id=%s']", id)));
  }

  public void resetPassword() {
    click(cssSelector("input[value='Reset Password']"));
  }
}
