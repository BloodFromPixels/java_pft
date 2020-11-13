package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.getNavigationHelper().goToGroupPage();

    // Список групп до добавления:
    List<GroupData> before = app.getGroupHelper().getGroupList();

    // Создаём группу:
    GroupData group = new GroupData("test1", null, null);
    app.getGroupHelper().createGroup(group);

    // Список групп после добавления:
    List<GroupData> after = app.getGroupHelper().getGroupList();

    // Сравнение размера списков до и после добавления:
    Assert.assertEquals(after.size(), before.size() + 1);

    // Среди всех элементов списка ищем элемент с самым большим идентификатором:
    int max = 0;
    for (GroupData g : after) {
      if (g.getId() > max) {
        max = g.getId();
      }
    }
    group.setId(max);
    before.add(group);

    // Теперь сравниваем содержимое нового и старого списков таким образом:
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
  }
}
