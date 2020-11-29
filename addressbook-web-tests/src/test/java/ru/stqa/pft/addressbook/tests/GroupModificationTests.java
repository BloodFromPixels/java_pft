package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().GroupPage();

    // Ищем, есть ли группы для модификации. Если нет - создаём:
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupModification () {

    // Множество групп до модификации:
    Set<GroupData> before = app.group().all();

    // Возращение первого попавшего элемента множества и помещение его в объект deletedGroup:
    GroupData modifiedGroup = before.iterator().next();

    // Выбираем конкретную группу и изменяем:
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId()).withName("test1").withHeader("test2").withFooter("test3");
    app.group().modify(group);

    // Множество групп после модификации:
    Set<GroupData> after = app.group().all();

    // Сравнение множеств до и после модификации:
    Assert.assertEquals(after.size(), before.size());

    // Чтобы сравнивать сами списки, нужно удалить лишний элемент из старого списка:
    before.remove(modifiedGroup);
    before.add(group);

    // Теперь сравниваем содержимое нового и старого списков:
    Assert.assertEquals(before, after);
  }
}