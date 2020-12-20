package ru.stqa.pft.rest.test;

import com.jayway.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;

import java.util.Set;

import static com.jayway.restassured.RestAssured.basic;
import static org.testng.Assert.*;

public class RestAssuredTests extends TestBase {

  @BeforeClass
  public void init() {
    RestAssured.authentication = basic(app.getProperty("bugify.login"), app.getProperty("bugify.password"));
  }

  @Test
  public void testCreateIssue() {
    try {
      skipIfNotFixed(429);
    } catch (SkipException e) {
      System.out.println(e.getMessage());
      throw e;
    }

    Set<Issue> oldIssues = app.restAssured().getIssues();
    Issue newIssue = new Issue()
            .withSubject("Test issue")
            .withDescription("New test issue");
    int issueId = app.restAssured().createIssue(newIssue);
    Set<Issue> newIssues = app.restAssured().getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }

  @Test(enabled = false)
  public void testOpenIssue() {
    Issue issue = app.restAssured().getIssue(429);
    assertEquals(issue.getStatus(), "Open");
    assertTrue(isIssueOpen(issue.getId()));
  }

  @Test(enabled = false)
  public void testClosedIssue() {
    Issue issue = app.restAssured().getIssue(428);
    assertEquals(issue.getStatus(), "Closed");
    assertFalse(isIssueOpen(issue.getId()));
  }
}
