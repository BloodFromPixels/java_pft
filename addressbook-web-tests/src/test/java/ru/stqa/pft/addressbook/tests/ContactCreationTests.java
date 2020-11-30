package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() {
    // Список контактов до добавления:
    Set<ContactData> before = app.contact().all();

    app.goTo().creationPage();
    ContactData contact = new ContactData().withFirstname("test1").withLastname("test2").withGroup("test1");
    app.contact().create(contact, true);
    app.contact().returnToHomePage();

    // Список контактов после добавления:
    Set<ContactData> after = app.contact().all();

    // Сравнение размера списков до и после добавления:
    Assert.assertEquals(after.size(), before.size() + 1);

    contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
    before.add(contact);

    // Теперь сравниваем содержимое нового и старого списков:
    Assert.assertEquals(before, after);
  }
}
