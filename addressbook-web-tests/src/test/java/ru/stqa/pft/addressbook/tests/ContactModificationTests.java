package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    if (! app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().goToCreationPage();
      app.getContactHelper().createContact(new ContactData("test1", "test2", "test1"), true);
      app.getNavigationHelper().returnToHomePage();
    }

    // Список контактов до модификации:
    List<ContactData> before = app.getContactHelper().getContactList();

    app.getContactHelper().initContactModification(before.size() - 1);
    ContactData contact = new ContactData(before
            .get(before.size() - 1)
            .getId(),"test1", "test2", null);
    app.getContactHelper().fillContactForm(contact, false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnToHomePage();

    // Список контактов после модификации:
    List<ContactData> after = app.getContactHelper().getContactList();

    // Сравнение размера списков до и после модификации:
    Assert.assertEquals(after.size(), before.size());

    // Чтобы сравнивать сами списки, нужно удалить лишний элемент из старого списка:
    before.remove(before.size() - 1);
    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);

    // Теперь сравниваем содержимое нового и старого списков:
    Assert.assertEquals(before, after);
  }
}
