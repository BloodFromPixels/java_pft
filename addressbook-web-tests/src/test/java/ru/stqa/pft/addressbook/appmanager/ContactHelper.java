package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

import static java.lang.String.format;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.name;
import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void goToHomePage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("home"));
  }

  public void submitContactCreation() {
    click(xpath("(//input[@name='submit'])[2]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(name("firstname"), contactData.getFirstname());
    type(name("lastname"), contactData.getLastname());
    type(name("address"), contactData.getAddress());
    type(name("email"), contactData.getEmail());
    type(name("email2"), contactData.getEmail2());
    type(name("email3"), contactData.getEmail3());
    type(name("home"), contactData.getHomePhone());
    type(name("mobile"), contactData.getMobilePhone());
    type(name("work"), contactData.getWorkPhone());
    //attach(name("photo"), contactData.getPhoto());
    if (creation) {
      if (contactData.getGroups().size() > 0) {
        assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
    } else {
      assertFalse(isElementPresent(name("new_group")));
    }
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value = '" + id + "']")).click();
  }

  public void deleteSelectedContact() {
    click(xpath("(//input[@value='Delete'])"));
    wd.switchTo().alert().accept();
  }

  public void initContactModificationById(ContactData contact) {
    wd.findElement(xpath(format("//input[@id='%s']/../../td[8]//img", contact.getId()))).click();
  }

  public void submitContactModification() {
    click(name("update"));
  }

  public boolean isThereAContact() {
    return isElementPresent(name("selected[]"));
  }

  public void create(ContactData contact, boolean creation) {

    fillContactForm(contact, creation);
    submitContactCreation();
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact);
    fillContactForm(contact, false);
    submitContactModification();
    goToHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContact();
    // Ожидание в 4 секунды, чтобы после удаления сработал редирект, и мы правильно подсчитали список контактов
    try {
      sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void selectDisplayGroup(String name) {
    new Select(wd.findElement(name("group"))).selectByVisibleText(name);
  }

  public void removeFromGroup(String name) {
    click(name("remove"));
  }

  public void addContactToGroup(ContactData contact, GroupData group) {
    selectContactById(contact.getId());
    new Select(wd.findElement(name("to_group"))).selectByVisibleText(group.getName());
    click(name("add"));
  }

  public void removeContactFromGroup(ContactData contact, GroupData group) {
    selectDisplayGroup(group.getName());
    selectContactById(contact.getId());
    removeFromGroup(group.getName());
  }

  public Contacts all() {
    Contacts contacts = new Contacts();
    List<WebElement> rows = wd.findElements(name("entry"));
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
      String lastName = cells.get(1).getText();
      String firstName = cells.get(2).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      contacts.add(new ContactData()
              .withId(id)
              .withFirstname(firstName)
              .withLastname(lastName)
              .withAddress(address)
              .withAllEmails(allEmails)
              .withAllPhones(allPhones));
    }
    return contacts;
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact);
    String firstname = wd.findElement(name("firstname")).getAttribute("value");
    String lastname = wd.findElement(name("lastname")).getAttribute("value");
    String home = wd.findElement(name("home")).getAttribute("value");
    String mobile = wd.findElement(name("mobile")).getAttribute("value");
    String work = wd.findElement(name("work")).getAttribute("value");
    String address = wd.findElement(name("address")).getAttribute("value");
    String email = wd.findElement(name("email")).getAttribute("value");
    String email2 = wd.findElement(name("email2")).getAttribute("value");
    String email3 = wd.findElement(name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData()
            .withId(contact.getId())
            .withFirstname(firstname)
            .withLastname(lastname)
            .withAddress(address)
            .withEmail(email)
            .withEmail2(email2)
            .withEmail3(email3)
            .withHomePhone(home)
            .withMobilePhone(mobile)
            .withWorkPhone(work);
  }
}

