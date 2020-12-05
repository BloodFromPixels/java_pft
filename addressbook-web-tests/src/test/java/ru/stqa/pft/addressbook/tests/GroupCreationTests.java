package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  // Провайдер тестовых данных
  @DataProvider
  public Iterator<Object[]> validGroups() throws IOException {
    /*
    Заполнение списка массивов набором данных для одного запуска тестового метода (сколько наборов, столько и запусков)
    */
    List<Object[]> list = new ArrayList<Object[]>();
    // Чтение данных
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.csv")));
    String line = reader.readLine();
    while (line != null) {
      // Делим полученные строки точкой с запятой
      String[] split = line.split(";");
      // Заполняем список полученными значениями
      list.add(new Object[] {new GroupData().withName(split[0]).withHeader(split[1]).withFooter(split[2])});
      line = reader.readLine();
    }
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
