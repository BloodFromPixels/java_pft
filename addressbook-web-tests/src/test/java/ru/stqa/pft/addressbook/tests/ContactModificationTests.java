package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().list().size() == 0) {
      app.goTo().creationPage();
      app.contact().create(new ContactData("test1", "test2", "test1"), true);
      app.contact().returnToHomePage();
    }
  }

  @Test
  public void testContactModification() {

    // Список контактов до модификации:
    List<ContactData> before = app.contact().list();

    int index = before.size() - 1;

    ContactData contact = new ContactData(before
            .get(index)
            .getId(),"test1", "test2", null);

    app.contact().modify(index, contact);

    // Список контактов после модификации:
    List<ContactData> after = app.contact().list();

    // Сравнение размера списков до и после модификации:
    Assert.assertEquals(after.size(), before.size());

    // Чтобы сравнивать сами списки, нужно удалить лишний элемент из старого списка:
    before.remove(index);
    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);

    // Теперь сравниваем содержимое нового и старого списков:
    Assert.assertEquals(before, after);
  }
}
