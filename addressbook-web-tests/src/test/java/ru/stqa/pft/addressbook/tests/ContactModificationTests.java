package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().all().size() == 0) {
      app.goTo().creationPage();
      app.contact().create(new ContactData()
              .withFirstname("test1")
              .withLastname("test2")
              .withGroup("test1"), true);
      app.contact().returnToHomePage();
    }
  }

  @Test
  public void testContactModification() {

    // Множество контактов до модификации
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact
            .getId()).withFirstname("test1").withLastname("test2");
    app.contact().modify(contact);
    // Множество контактов после модификации
    Contacts after = app.contact().all();
    // Сравнение размера множеств до и после модификации
    assertEquals(after.size(), before.size());
    // Сравнение содержимого нового и старого множеств
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }
}
