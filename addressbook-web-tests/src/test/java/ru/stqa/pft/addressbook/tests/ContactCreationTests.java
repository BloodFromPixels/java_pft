package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() {
    // Список контактов до добавления:
    List<ContactData> before = app.contact().list();

    app.goTo().creationPage();
    ContactData contact = new ContactData("test1", "test2", "test1");
    app.contact().create(contact, true);
    app.contact().returnToHomePage();

    // Список контактов после добавления:
    List<ContactData> after = app.contact().list();

    // Сравнение размера списков до и после добавления:
    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(contact);

    //Сортируем списки (с помощью анонимных функций и лямбда-выражений):
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);

    // Теперь сравниваем содержимое нового и старого списков:
    Assert.assertEquals(before, after);
  }
}
