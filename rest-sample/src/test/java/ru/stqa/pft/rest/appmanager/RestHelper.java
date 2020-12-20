package ru.stqa.pft.rest.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.message.BasicNameValuePair;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

import static org.apache.http.client.fluent.Executor.newInstance;
import static org.apache.http.client.fluent.Request.Get;
import static org.apache.http.client.fluent.Request.Post;

public class RestHelper {

  private ApplicationManager app;

  public RestHelper(ApplicationManager app) {
    this.app = app;
  }

  public Set<Issue> getIssues() throws IOException {
    String json = getExecutor()
            .execute(Get("https://bugify.stqa.ru/api/issues.json"))
            .returnContent()
            .asString();
    JsonElement parsedJson = new JsonParser().parse(json);
    JsonElement issues = parsedJson.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  private Executor getExecutor() {
    return newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  public int createIssue(Issue issue) throws IOException {
    String json = getExecutor().execute(Post("https://bugify.stqa.ru/api/issues.json")
            .bodyForm(new BasicNameValuePair("subject", issue.getSubject()),
                      new BasicNameValuePair("description", issue.getDescription())))
            .returnContent()
            .asString();
    JsonElement parsedJson = new JsonParser().parse(json);
    return parsedJson.getAsJsonObject().get("issue_id").getAsInt();
  }
}
