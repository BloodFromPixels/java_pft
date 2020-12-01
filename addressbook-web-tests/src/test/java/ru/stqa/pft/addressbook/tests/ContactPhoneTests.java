package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactPhoneTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().all().size() == 0) {
      app.goTo().creationPage();
      app.contact().create(new ContactData()
              .withFirstname("test1")
              .withLastname("test2")
              .withGroup("test1")
              .withHomePhone("111")
              .withMobilePhone("222")
              .withWorkPhone("333"), true);
      app.contact().goToHomePage();
    }
  }

  @Test
  public void testContactPhones() {
    app.contact().goToHomePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    MatcherAssert.assertThat(contact.getHomePhone(), CoreMatchers.equalTo(cleaned(contactInfoFromEditForm
            .getHomePhone())));
    MatcherAssert.assertThat(contact.getMobilePhone(), CoreMatchers.equalTo(cleaned(contactInfoFromEditForm
            .getMobilePhone())));
    MatcherAssert.assertThat(contact.getWorkPhone(), CoreMatchers.equalTo(cleaned(contactInfoFromEditForm
            .getWorkPhone())));
  }

  public String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }
}