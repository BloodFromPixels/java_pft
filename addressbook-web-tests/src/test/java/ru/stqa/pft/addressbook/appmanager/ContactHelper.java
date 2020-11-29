package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToHomePage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("home page"));
  }

  public void submitContactCreation() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void deleteSelectedContact() {
    click(By.xpath("(//input[@value='Delete'])"));
    wd.switchTo().alert().accept();
  }

  public void initContactModification(int index) {
    wd.findElements(By.xpath("(//img[@alt='Edit'])")).get(index).click();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public void create(ContactData contact, boolean creation) {

    fillContactForm(contact, creation);
    submitContactCreation();
  }

  public void modify(int index, ContactData contact) {
    initContactModification(index);
    fillContactForm(contact, false);
    submitContactModification();
    returnToHomePage();
  }

  public void delete(int index) {
    selectContact(index);
    deleteSelectedContact();

    // Ожидание в 4 секунды, чтобы после удаления сработал редирект и мы правильно подсчитали список контактов
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();

    // Найти все элементы с тегом tr:
    List<WebElement> rows = wd.findElements(By.tagName("tr"));

    // Фильтруем полученные строки по аттрибуту name (name должен быть равен entry)
    List<WebElement> elements = rows.stream()
            .filter(row -> "entry".equals(row.getAttribute("name")))
            .collect(Collectors.toList());

    // Смотрим на все найденные элементы и получаем их имя:
    for (WebElement element : elements) {
      List<WebElement> columns = element.findElements(By.tagName("td"));

      if (columns.size() >= 3) {
        String lastName = columns.get(1).getText();
        String firstName = columns.get(2).getText();

        // Создаём объект ContactData, который заполняем полученными выше значениями:
        ContactData contact = new ContactData(firstName, lastName, null);

        // Заполняем список полученными объектами:
        contacts.add(contact);
      }
    }
    return contacts;
  }
}

