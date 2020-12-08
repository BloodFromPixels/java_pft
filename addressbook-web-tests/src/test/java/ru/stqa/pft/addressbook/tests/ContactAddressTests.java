package ru.stqa.pft.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;

public class ContactAddressTests extends TestBase{
  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().all().size() == 0) {
      app.goTo().creationPage();
      app.contact().create(new ContactData()
              .withFirstname("test1")
              .withLastname("test2")
              .withAddress("Something like 123 !№;%:?*() Кремль @#$%^&{}[]\nВторая строка"), true);
      app.contact().goToHomePage();
    }
  }

  @Test
  public void testContactAddress() {
    app.contact().goToHomePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    MatcherAssert.assertThat(contact.getAddress(), equalTo(Address(contactInfoFromEditForm)));
  }

  private String Address(ContactData contact) {
    return contact.getAddress();
  }
}
