package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @Test
  public void testBadGroupCreation() {
    app.goTo().GroupPage();
    // Множество групп до добавления
    Groups before = app.group().all();
    // Создаём группу
    GroupData group = new GroupData().withName("test'");
    app.group().create(group);
    // Сравнение размера множеств до и после добавления
    assertThat(app.group().count(), equalTo(before.size()));
    // Множество групп после добавления
    Groups after = app.group().all();
    // Сравнение содержимого нового и старого множеств
    assertThat(after, equalTo(before));
  }
}
