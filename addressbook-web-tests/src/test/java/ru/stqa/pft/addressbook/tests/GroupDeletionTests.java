package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().GroupPage();

    // Ищем, есть ли группы для модификации. Если нет - создаём:
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData("test1", null, null));
    }
  }

  @Test
  public void testGroupDeletion() {

    // Список групп до удаления:
    List<GroupData> before = app.group().list();

    // Индекс последнего добавленного объекта в таблице:
    int index = before.size() - 1;

    // Выбираем конкретную группу и удаляем. Возвращаемся на исходную страницу с группами:
    app.group().delete(index);

    // Список групп после удаления:
    List<GroupData> after = app.group().list();

    // Сравнение размера списков до и после удаления:
    Assert.assertEquals(after.size(), before.size() - 1);

    // Чтобы сравнивать сами списки, нужно удалить лишний элемент из старого списка
    before.remove(index);

    // Теперь сравниваем содержимое нового и старого списков:
    Assert.assertEquals(before, after);
  }
}
