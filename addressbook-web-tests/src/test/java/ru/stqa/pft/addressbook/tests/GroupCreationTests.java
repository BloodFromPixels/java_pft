package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  // Провайдер тестовых данных
  @DataProvider
  public Iterator<Object[]> validGroups() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.xml")));
    String xml = "";
    String line = reader.readLine();
    while (line != null) {
      xml += line;
      line = reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.processAnnotations(GroupData.class);
    List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
    return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
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
