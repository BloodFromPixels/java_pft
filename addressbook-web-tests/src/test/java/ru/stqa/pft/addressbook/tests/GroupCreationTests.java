package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroups() {
    /*
    Заполнение списка массивов набором данных для одного запуска тестового метода (сколько наборов, столько и запусков)
    */
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[] {new GroupData().withName("test1").withHeader("header 1").withFooter("footer 1")});
    list.add(new Object[] {new GroupData().withName("test2").withHeader("header 2").withFooter("footer 2")});
    list.add(new Object[] {new GroupData().withName("test3").withHeader("header 3").withFooter("footer 3")});
    /*
    Возвращение итератора списка, который вытаскивает один набор параметров за другим, запускает тестовый метод
    несколько раз и помещает полученную информацию в отчёт
    */
    return list.iterator();
  }

  @Test(dataProvider = "validGroups")
  public void testGroupCreation(GroupData group) {
    app.goTo().GroupPage();
    // Множество групп до добавления
    Groups before = app.group().all();
    // Создаём группу
    app.group().create(group);
    // Сравнение размера множеств до и после добавления
    assertThat(app.group().count(), equalTo(before.size() + 1));
    // Множество групп после добавления
    Groups after = app.group().all();
    // Сравнение содержимого нового и старого множеств
    //assertThat(after, equalTo(before));
    assertThat(after, equalTo(before.withAdded(group
            .withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test (enabled = false)
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
