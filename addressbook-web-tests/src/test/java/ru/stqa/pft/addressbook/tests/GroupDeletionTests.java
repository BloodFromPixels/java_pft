package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupDeletionTests extends TestBase {

  @Test
  public void testGroupDeletion() {
    app.getNavigationHelper().goToGroupPage();

    // Количество групп до добавления:
    int before = app.getGroupHelper().getGroupCount();

    // Ищем, есть ли уже созданные группы, которые можно удалить. Если нет - создаём:
    if (! app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }

    // Выбираем группу и удаляем. Возвращаемся на исходную страницу с группами:
    app.getGroupHelper().selectGroup(before - 1);
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnToGroupPage();

    // Количество групп после добавления:
    int after = app.getGroupHelper().getGroupCount();

    // Сравнение количества групп до и после добавления:
    Assert.assertEquals(after, before - 1);
  }

}
