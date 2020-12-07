package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().GroupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupDeletion() {
    // Множество групп до удаления
    Groups before = app.db().groups();
    // Возращение первого попавшего элемента множества и помещение его в объект deletedGroup
    GroupData deletedGroup = before.iterator().next();
    // Выбор группы и удаление. Возвращение на исходную страницу с группами
    app.goTo().GroupPage();
    app.group().delete(deletedGroup);
    // Сравнение размера множеств до и после удаления
    assertThat(app.group().count(), equalTo(before.size() - 1));
    // Множество групп после удаления
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.without(deletedGroup)));
  }
}
