package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.By.*;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void GroupPage() {
    if (isElementPresent(tagName("h1"))
            && wd.findElement(tagName("h1")).getText().equals("Groups")
            && isElementPresent(name("new"))) {
      return;
    }
    click(linkText("groups"));
  }

  public void creationPage() {
    click(linkText("add new"));
  }
}
