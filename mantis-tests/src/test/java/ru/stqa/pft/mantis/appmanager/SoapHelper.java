package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.IssueStatus;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.math.BigInteger.valueOf;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class SoapHelper {
  private ApplicationManager app;

  public SoapHelper(ApplicationManager app) {
    this.app = app;
  }

  public Set<Project> getProjects() throws RemoteException, MalformedURLException, ServiceException {
    MantisConnectPortType mc = getMantisConnect();
    ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root");
    return asList(projects).stream()
            .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName()))
            .collect(Collectors.toSet());
  }

  private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
    MantisConnectPortType mc = new MantisConnectLocator()
            .getMantisConnectPort(new URL("http://localhost/mantisbt-2.24.2/api/soap/mantisconnect.php"));
    return mc;
  }

  public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    String[] categories = mc.mc_project_get_categories("administrator", "root", valueOf(issue.getProject().getId()));
    IssueData issueData = new IssueData();
    issueData.setSummary(issue.getSummary());
    issueData.setDescription(issue.getDescription());
    issueData.setProject(new ObjectRef(valueOf(issue.getProject().getId()), issue.getProject().getName()));
    issueData.setCategory(categories[0]);
    BigInteger issueId = mc.mc_issue_add("administrator", "root", issueData);
    IssueData createdIssueData = mc.mc_issue_get("administrator", "root", issueId);
    return new Issue()
            .withId(createdIssueData.getId().intValue())
            .withSummary(createdIssueData.getSummary())
            .withDescription(createdIssueData.getDescription())
            .withProject(new Project().withId(createdIssueData.getProject().getId().intValue())
                                      .withName(createdIssueData.getProject().getName()));
  }

  public String getIssueStatus(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    IssueData issueData = mc.mc_issue_get("administrator", "root", valueOf(issueId));
    ObjectRef status = issueData.getStatus();
    String statusName = status.getName();
    return statusName;
  }

  public void getStatusList() throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    List<IssueStatus> issueStatusList = asList(mc.mc_enum_status("administrator", "root")).stream()
            .map((s) -> new IssueStatus().withId(s.getId().intValue()).withStatus(s.getName()))
            .collect(toList());
    for (IssueStatus status: issueStatusList){
      System.out.println(status.getStatus());
    }
  }
}
