package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupModificationTests extends TestBase {

  @Test
  public void testGroupModification() {
    app.getNavigationHelper().goToGroupPage();

    // Количество групп до добавления:
    int before = app.getGroupHelper().getGroupCount();

    // Ищем, есть ли группы для модификации. Если нет - создаём:
    if (! app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }

    // Выбираем группу и изменяем:
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().initGroupModification();
    app.getGroupHelper().fillGroupForm(new GroupData("test1", "test2", "test3"));
    app.getGroupHelper().submitGroupModification();
    app.getGroupHelper().returnToGroupPage();

    // Количество групп после добавления:
    int after = app.getGroupHelper().getGroupCount();

    // Сравнение количества групп до и после добавления:
    Assert.assertEquals(after, before);
  }
}
