package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.getNavigationHelper().goToGroupPage();

    // Ищем, есть ли группы для модификации. Если нет - создаём:
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
  }

  @Test
  public void testGroupModification () {

    // Список групп до модификации:
    List<GroupData> before = app.getGroupHelper().getGroupList();

    // Индекс последнего добавленного объекта в таблице:
    int index = before.size() - 1;

    // Выбираем конкретную группу и изменяем:
    GroupData group = new GroupData(before.get(index).getId(), "test1", "test2", "test3");
    app.getGroupHelper().modifyGroup(index, group);

    // Список групп после модификации:
    List<GroupData> after = app.getGroupHelper().getGroupList();

    // Сравнение размера списков до и после модификации:
    Assert.assertEquals(after.size(), before.size());

    // Чтобы сравнивать сами списки, нужно удалить лишний элемент из старого списка:
    before.remove(index);
    before.add(group);
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);

    // Теперь сравниваем содержимое нового и старого списков:
    Assert.assertEquals(before, after);
  }
}