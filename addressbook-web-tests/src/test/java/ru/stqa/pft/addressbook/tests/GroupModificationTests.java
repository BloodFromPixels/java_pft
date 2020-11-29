package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().GroupPage();

    // Ищем, есть ли группы для модификации. Если нет - создаём:
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupModification () {

    // Список групп до модификации:
    List<GroupData> before = app.group().list();

    // Индекс последнего добавленного объекта в таблице:
    int index = before.size() - 1;

    // Выбираем конкретную группу и изменяем:
    GroupData group = new GroupData()
            .withId(before.get(index).getId()).withName("test1").withHeader("test2").withFooter("test3");
    app.group().modify(index, group);

    // Список групп после модификации:
    List<GroupData> after = app.group().list();

    // Сравнение размера списков до и после модификации:
    Assert.assertEquals(after.size(), before.size());

    // Чтобы сравнивать сами списки, нужно удалить лишний элемент из старого списка:
    before.remove(index);
    before.add(group);
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);

    // Теперь сравниваем содержимое нового и старого списков:
    Assert.assertEquals(before, after);
  }
}