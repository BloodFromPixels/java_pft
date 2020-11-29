package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().GroupPage();

    // Ищем, есть ли группы для модификации. Если нет - создаём:
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupDeletion() {

    // Множество групп до удаления:
    Set<GroupData> before = app.group().all();

    // Возращение первого попавшего элемента множества и помещение его в объект deletedGroup:
    GroupData deletedGroup = before.iterator().next();

    // Выбираем группу и удаляем. Возвращаемся на исходную страницу с группами:
    app.group().delete(deletedGroup);

    // Множество групп после удаления:
    Set<GroupData> after = app.group().all();

    // Сравнение размера множеств до и после удаления:
    Assert.assertEquals(after.size(), before.size() - 1);

    // Чтобы сравнивать сами списки, нужно удалить лишний элемент из старого множества:
    before.remove(deletedGroup);

    // Теперь сравниваем содержимое нового и старого списков:
    Assert.assertEquals(before, after);
  }
}
