package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().creationPage();
      app.contact().create(new ContactData()
              .withFirstname("test1")
              .withLastname("test2"), true);
      app.contact().goToHomePage();
    }

    if (app.db().groups().size() == 0) {
      app.goTo().GroupPage();
      app.group().create(new GroupData().withName("test1"));
      app.contact().goToHomePage();
    }
  }

  @Test
  public void testContactAddToGroup() {
    Contacts listContacts = app.db().contacts();
    ContactData selectedContact = listContacts.iterator().next();
    Groups listGroups = app.db().groups();
    GroupData selectedGroup = listGroups.iterator().next();
    app.contact().goToHomePage();
    if (!selectedContact.getGroups().isEmpty() && selectedContact.getGroups().contains(selectedGroup)) {
      app.contact().removeContactFromGroup(selectedContact, selectedGroup);
      assertThat(selectedContact.getGroups().without(selectedGroup), equalTo(app.db().contacts().stream().
              filter((c) -> c.getId() == selectedContact.getId()).collect(Collectors.toList()).get(0).getGroups()));
      app.contact().goToHomePage();
    }
    app.contact().selectDisplayGroup("[all]");
    app.contact().addContactToGroup(selectedContact, selectedGroup);
    assertThat(selectedContact.getGroups().withAdded(selectedGroup), equalTo(app.db().contacts().stream().
            filter((c) -> c.getId() == selectedContact.getId()).collect(Collectors.toList()).get(0).getGroups()));
  }
}