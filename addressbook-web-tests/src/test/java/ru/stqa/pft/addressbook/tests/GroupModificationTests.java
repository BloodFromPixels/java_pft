package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().GroupPage();
    // Ищем, есть ли группы для модификации. Если нет - создаём
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupModification () {
    // Множество групп до модификации
    Groups before = app.group().all();
    // Возращение первого попавшего элемента множества и помещение его в объект deletedGroup
    GroupData modifiedGroup = before.iterator().next();
    // Выбор конкретной группы и изменение
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId()).withName("test1").withHeader("test2").withFooter("test3");
    app.group().modify(group);
    // Сравнение множеств до и после модификации
    assertThat(app.group().count(), equalTo(before.size()));
    // Множество групп после модификации
    Groups after = app.group().all();
    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
  }
}