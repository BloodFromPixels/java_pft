package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ResetPasswordTests extends TestBase {

  @Test
  public void passwordResetTest() throws MessagingException, IOException {
    app.registration().login(new UserData().withLogin("administrator").withPassword("root"));
    UserData user = app.db().users()
            .stream().filter((u) -> (!u.getLogin().equals("administrator")))
            .collect(Collectors.toList()).iterator().next();
    app.james().drainEmail(user.getLogin(), "password");
    app.registration().pressResetByAdmin(user);
    List<MailMessage> mailMessages = app.james().waitForMail(user.getLogin(), "password", 60000);
    String confirmationLink = findConfirmationLink(mailMessages, user.getEmail());
    String newPassword = "password2";
    app.registration().finish(confirmationLink, newPassword);
    app.newSession().login(user.getLogin(), newPassword);
  }

  public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }
}
