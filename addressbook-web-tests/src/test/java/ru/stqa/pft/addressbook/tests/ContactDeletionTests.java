package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    if (! app.getContactHelper().isThereAContact()) {
      app.goTo().goToCreationPage();
      app.getContactHelper().createContact(new ContactData("test1", "test2", "test1"), true);
      app.goTo().returnToHomePage();
    }

    // Список контактов до удаления:
    List<ContactData> before = app.getContactHelper().getContactList();

    app.getContactHelper().selectContact(before.size() - 1);
    app.getContactHelper().deleteSelectedContact();

    // Ожидание в 4 секунды, чтобы после удаления сработал редирект и мы правильно подсчитали список контактов
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Список контактов после удаления:
    List<ContactData> after = app.getContactHelper().getContactList();

    // Сравнение размера списков до и после удаления:
    Assert.assertEquals(after.size(), before.size() - 1);

    // Чтобы сравнивать сами списки, нужно удалить лишний элемент из старого списка
    before.remove(before.size() - 1);

    // Теперь сравниваем содержимое нового и старого списков:
    Assert.assertEquals(before, after);
  }
}