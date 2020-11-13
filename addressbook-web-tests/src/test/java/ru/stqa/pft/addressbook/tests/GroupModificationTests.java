package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupModificationTests extends TestBase {

  @Test
  public void testGroupModification() {
    app.getNavigationHelper().goToGroupPage();

    // Ищем, есть ли группы для модификации. Если нет - создаём:
    if (! app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }

    // Список групп до модификации:
    List<GroupData> before = app.getGroupHelper().getGroupList();

    // Выбираем конкретную группу и изменяем:
    app.getGroupHelper().selectGroup(before.size() - 1);
    app.getGroupHelper().initGroupModification();
    app.getGroupHelper().fillGroupForm(new GroupData("test1", "test2", "test3"));
    app.getGroupHelper().submitGroupModification();
    app.getGroupHelper().returnToGroupPage();

    // Список групп после модификации:
    List<GroupData> after = app.getGroupHelper().getGroupList();

    // Сравнение размера списков до и после модификации:
    Assert.assertEquals(after.size(), before.size());
  }
}
