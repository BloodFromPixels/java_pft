package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

  @Test
  public void testGroupDeletion() {
    app.getNavigationHelper().goToGroupPage();

    // Ищем, есть ли уже созданные группы, которые можно удалить. Если нет - создаём:
    if (! app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }

    // Список групп до удаления:
    List<GroupData> before = app.getGroupHelper().getGroupList();

    // Выбираем конкретную группу и удаляем. Возвращаемся на исходную страницу с группами:
    app.getGroupHelper().selectGroup(before.size() - 1);
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnToGroupPage();

    // Список групп после удаления:
    List<GroupData> after = app.getGroupHelper().getGroupList();

    // Сравнение размера списков до и после удаления:

    Assert.assertEquals(after.size(), before.size() - 1);

    // Чтобы сравнивать сами списки, нужно удалить лишний элемент из старого списка
    before.remove(before.size() - 1);

    // Теперь сравниваем содержимое нового и старого списков:
    Assert.assertEquals(before, after);
  }

}
