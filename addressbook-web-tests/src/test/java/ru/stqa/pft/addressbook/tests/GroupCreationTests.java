package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.getNavigationHelper().goToGroupPage();

    // Список групп до добавления:
    List<GroupData> before = app.getGroupHelper().getGroupList();

    // Индекс последнего добавленного объекта в таблице:
    int index = before.size() + 1;

    // Создаём группу:
    GroupData group = new GroupData("test2", null, null);
    app.getGroupHelper().createGroup(group);

    // Список групп после добавления:
    List<GroupData> after = app.getGroupHelper().getGroupList();

    // Сравнение размера списков до и после добавления:
    Assert.assertEquals(after.size(), index);

    before.add(group);

    //Сортируем списки (с помощью анонимных функций и лямбда-выражений):
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);

    // Теперь сравниваем содержимое нового и старого списков:
    Assert.assertEquals(before, after);
  }
}
