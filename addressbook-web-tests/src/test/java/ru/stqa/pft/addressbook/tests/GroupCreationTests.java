package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.getNavigationHelper().goToGroupPage();

    // Список групп до добавления:
    List<GroupData> before = app.getGroupHelper().getGroupList();

    // Создаём группу:
    app.getGroupHelper().createGroup(new GroupData("test1", null, null));

    // Список групп после добавления:
    List<GroupData> after = app.getGroupHelper().getGroupList();

    // Сравнение размера списков до и после добавления:
    Assert.assertEquals(after.size(), before.size() + 1);
  }

}
