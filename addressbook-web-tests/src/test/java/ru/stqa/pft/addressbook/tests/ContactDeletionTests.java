package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().list().size() == 0) {
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

    // Список контактов до удаления:
    List<ContactData> before = app.contact().list();

    int index = before.size() - 1;

    app.contact().delete(index);

    // Список контактов после удаления:
    List<ContactData> after = app.contact().list();

    // Сравнение размера списков до и после удаления:
    Assert.assertEquals(after.size(), before.size() - 1);

    // Чтобы сравнивать сами списки, нужно удалить лишний элемент из старого списка
    before.remove(index);

    // Теперь сравниваем содержимое нового и старого списков:
    Assert.assertEquals(before, after);
  }
}