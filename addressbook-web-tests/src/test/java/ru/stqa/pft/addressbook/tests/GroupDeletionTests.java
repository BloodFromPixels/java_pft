package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().GroupPage();

    // Поиск групп для модификации. Если нет - создаём:
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupDeletion() {

    // Множество групп до удаления
    Groups before = app.group().all();

    // Возращение первого попавшего элемента множества и помещение его в объект deletedGroup
    GroupData deletedGroup = before.iterator().next();

    // Выбор группы и удаление. Возвращение на исходную страницу с группами
    app.group().delete(deletedGroup);

    // Множество групп после удаления
    Groups after = app.group().all();

    // Сравнение размера множеств до и после удаления
    assertEquals(after.size(), before.size() - 1);

    assertThat(after, equalTo(before.without(deletedGroup)));
  }
}
