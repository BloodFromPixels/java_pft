package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.getNavigationHelper().goToGroupPage();

    // Ищем, есть ли группы для модификации. Если нет - создаём:
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
  }

  @Test
  public void testGroupDeletion() {

    // Список групп до удаления:
    List<GroupData> before = app.getGroupHelper().getGroupList();

    // Индекс последнего добавленного объекта в таблице:
    int index = before.size() - 1;

    // Выбираем конкретную группу и удаляем. Возвращаемся на исходную страницу с группами:
    app.getGroupHelper().deleteGroup(index);

    // Список групп после удаления:
    List<GroupData> after = app.getGroupHelper().getGroupList();

    // Сравнение размера списков до и после удаления:
    Assert.assertEquals(after.size(), index);

    // Чтобы сравнивать сами списки, нужно удалить лишний элемент из старого списка
    before.remove(index);

    // Теперь сравниваем содержимое нового и старого списков:
    Assert.assertEquals(before, after);
  }
}
