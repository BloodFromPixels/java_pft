package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

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
  public void selectGroup(int index) {

    // Находим все элементы с локатором "selected[]", выбираем нужный по индексу и кликаем по нему:
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public void createGroup(GroupData group) {
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    returnToGroupPage();
  }

  public boolean isThereAGroup() {
    return isElementPresent(By.name("selected[]"));
  }

  // Ищем все элементы на странице и считаем их количество:
  public int getGroupCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  // Создание списка с группами и возращение полученного значения:
  public List<GroupData> getGroupList() {
    List<GroupData> groups = new ArrayList<GroupData>();

    // Найти все элементы с css селектором span = group:
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));

    // Смотрим на все найденные элементы и получаем их имя:
    for (WebElement element : elements) {
      String name = element.getText();

      // В элементе "input" берём атрибут с именем "value":
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));

      // Создаём объект GroupData, который заполняем полученными выше значениями:
      GroupData group = new GroupData(id, name, null, null);

      // Заполняем список полученными объектами:
      groups.add(group);
    }
    return groups;
  }
}
