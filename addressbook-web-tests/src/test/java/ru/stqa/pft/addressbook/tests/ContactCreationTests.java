package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() {
    // Множество контактов до добавления
    Contacts before = app.contact().all();

    app.goTo().creationPage();
    ContactData contact = new ContactData().withFirstname("test1").withLastname("test2").withGroup("test1");
    app.contact().create(contact, true);
    app.contact().returnToHomePage();

    // Множество контактов после добавления
    Contacts after = app.contact().all();

    // Сравнение размера множеств до и после добавления
    assertThat(after.size(), equalTo(before.size() + 1));

    // Сравнение содержимого нового и старого множеств
    assertThat(after, equalTo(before.withAdded(contact
            .withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }
}
