package ru.stqa.pft.mantis.model;

public class IssueStatus {
  private int id;
  private String status;

  public IssueStatus withId(int id) {
    this.id = id;
    return this;
  }

  public IssueStatus withStatus(String status) {
    this.status = status;
    return this;
  }

  public int getId() {
    return id;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "IssueStatus{" +
            "id=" + id +
            ", status='" + status + '\'' +
            '}';
  }
}
