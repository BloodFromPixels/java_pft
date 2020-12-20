package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

import static java.lang.Integer.parseInt;
import static org.openqa.selenium.By.*;

public class GroupHelper extends HelperBase {

  public GroupHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToGroupPage() {
    click(linkText("group page"));
  }

  public void submitGroupCreation() {
    click(name("submit"));
  }

  public void fillGroupForm(GroupData groupData) {
    type(name("group_name"), groupData.getName());
    type(name("group_header"), groupData.getHeader());
    type(name("group_footer"), groupData.getFooter());
  }

  public void initGroupCreation() {
    click(name("new"));
  }

  public void deleteSelectedGroups() {
    click(name("delete"));
  }

  // Выбор группы
  public void selectGroupById(int id) {
    // Находим элемент с локатором, содержащим нужный нам идентификатор, и кликаем по нему
    wd.findElement(cssSelector("input[value = '" + id + "']")).click();
  }

  public void initGroupModification() {
    click(name("edit"));
  }

  public void submitGroupModification() {
    click(name("update"));
  }

  public void create(GroupData group) {
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    // Сброс кэша
    groupCache = null;
    returnToGroupPage();
  }

  public void modify(GroupData group) {
    selectGroupById(group.getId());
    initGroupModification();
    fillGroupForm(group);
    submitGroupModification();
    // Сброс кэша
    groupCache = null;
    returnToGroupPage();
  }

  public void delete(GroupData group) {
    selectGroupById(group.getId());
    deleteSelectedGroups();
    // Сброс кэша
    groupCache = null;
    returnToGroupPage();
  }

  public boolean isThereAGroup() {
    return isElementPresent(name("selected[]"));
  }

  // Ищем все элементы на странице и считаем их количество
  public int count() {
    return wd.findElements(name("selected[]")).size();
  }

  // Кэш
  private Groups groupCache = null;

  // Создание множества с группами и возращение полученного значения
  public Groups all() {
    // Использование кэша
    if (groupCache != null) {
      return new Groups(groupCache);
    }
    groupCache = new Groups();
    // Найти все элементы с css селектором span = group
    List<WebElement> elements = wd.findElements(cssSelector("span.group"));
    // Смотрим на все найденные элементы и получаем их имя
    for (WebElement element : elements) {
      String name = element.getText();
      // В элементе "input" берём атрибут с именем "value"
      int id = parseInt(element.findElement(tagName("input")).getAttribute("value"));
      // Полученными выше значениями заполняем GroupData, после чего заполняем множество объектами
      groupCache.add(new GroupData().withId(id).withName(name));
    }
    return groupCache;
  }
}
