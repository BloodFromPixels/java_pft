package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupHelper extends HelperBase {

  public GroupHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToGroupPage() {
    click(By.linkText("group page"));
  }

  public void submitGroupCreation() {
    click(By.name("submit"));
  }

  public void fillGroupForm(GroupData groupData) {
    type(By.name("group_name"), groupData.getName());
    type(By.name("group_header"), groupData.getHeader());
    type(By.name("group_footer"), groupData.getFooter());
  }

  public void initGroupCreation() {
    click(By.name("new"));
  }

  public void deleteSelectedGroups() {
    click(By.name("delete"));
  }

  // Выбор группы:
  public void selectGroupById(int id) {

    // Находим элемент с локатором, содержащим нужный нам идентификатор, и кликаем по нему:
    wd.findElement(By.cssSelector("input[value = '" + id + "']")).click();
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public void create(GroupData group) {
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    returnToGroupPage();
  }

  public void modify(GroupData group) {
    selectGroupById(group.getId());
    initGroupModification();
    fillGroupForm(group);
    submitGroupModification();
    returnToGroupPage();
  }

  public void delete(GroupData group) {
    selectGroupById(group.getId());
    deleteSelectedGroups();
    returnToGroupPage();
  }

  public boolean isThereAGroup() {
    return isElementPresent(By.name("selected[]"));
  }

  // Ищем все элементы на странице и считаем их количество:
  public int getGroupCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  // Создание множества с группами и возращение полученного значения:
  public Set<GroupData> all() {
    Set<GroupData> groups = new HashSet<GroupData>();

    // Найти все элементы с css селектором span = group:
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));

    // Смотрим на все найденные элементы и получаем их имя:
    for (WebElement element : elements) {
      String name = element.getText();

      // В элементе "input" берём атрибут с именем "value":
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));

      // Полученными выше значениями заполняем GroupData, после чего заполняем множество объектами:
      groups.add(new GroupData().withId(id).withName(name));
    }
    return groups;
  }
}
