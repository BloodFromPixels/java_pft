package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    if (! app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().goToCreationPage();
      app.getContactHelper().createContact(new ContactData("test1", "test2", "test1"), true);
      app.getNavigationHelper().returnToHomePage();
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("test1", "test2", null), false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnToHomePage();
  }
}
