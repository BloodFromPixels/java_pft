package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.getNavigationHelper().goToGroupPage();

    // Количество групп до добавления:
    int before = app.getGroupHelper().getGroupCount();

    // Создаём группу:
    app.getGroupHelper().createGroup(new GroupData("test1", null, null));

    // Количество групп после добавления:
    int after = app.getGroupHelper().getGroupCount();

    // Сравнение количества групп до и после добавления:
    Assert.assertEquals(after, before + 1);
  }

}
