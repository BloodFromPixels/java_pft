package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.goTo().GroupPage();

    // Множество групп до добавления
    Groups before = app.group().all();

    // Создаём группу
    GroupData group = new GroupData().withName("test2");
    app.group().create(group);

    // Множество групп после добавления
    Groups after = app.group().all();

    // Сравнение размера множеств до и после добавления
    assertThat(after.size(), equalTo(before.size() + 1));

    // Сравнение содержимого нового и старого множеств
    assertThat(after, equalTo(before.withAdded(group.withId(after
            .stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }
}
