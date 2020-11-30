package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

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
  public void testContactDeletion() {

    // Множество контактов до удаления
    Contacts before = app.contact().all();

    ContactData deletedContact = before.iterator().next();

    app.contact().delete(deletedContact);

    // Множество контактов после удаления
    Contacts after = app.contact().all();

    // Сравнение размера множеств до и после удаления
    assertEquals(after.size(), before.size() - 1);

    // Сравнение содержимого нового и старого множеств
    assertThat(after, equalTo(before.without(deletedContact)));
  }
}