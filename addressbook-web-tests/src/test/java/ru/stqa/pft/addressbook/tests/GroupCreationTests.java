package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.goTo().GroupPage();

    // Множество групп до добавления:
    Set<GroupData> before = app.group().all();

    // Создаём группу:
    GroupData group = new GroupData().withName("test2");
    app.group().create(group);

    // Множество групп после добавления:
    Set<GroupData> after = app.group().all();

    // Сравнение размера множеств до и после добавления:
    Assert.assertEquals(after.size(), before.size() + 1);

    // Получаем максимальный идентификатор среди всех наших групп:
    group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    before.add(group);

    // Теперь сравниваем содержимое нового и старого множеств:
    Assert.assertEquals(before, after);
  }
}
