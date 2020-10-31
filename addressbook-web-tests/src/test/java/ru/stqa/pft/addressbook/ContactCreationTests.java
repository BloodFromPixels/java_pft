package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    goToCreationPage();
    fillContactForm(new ContactData("test1", "test2", "test3", "test4"));
    submitContactCreation();
    returnToHomePage();
  }
}
