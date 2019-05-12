import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.theguardian.bean.Article;
import com.theguardian.bean.Response;
import com.theguardian.client.GuardianContentApi;

import junit.framework.TestCase;

public class GuardianContentApiTest extends TestCase {
  private final String apiKey = "replace-here-your-key";

  // I thought it was useful to insert some tests to check that the endpoint is working well as expected

  @org.junit.Test
  public void testApiEndpoint1() {

    given().when()
        .get("http://content.guardianapis.com/search?from-date=2016-07-09&q=a&api-key=" + apiKey)
        .then().body("response.status", equalTo("ok"));
  }

  @org.junit.Test
  public void testApiEndpoint2() {

    given().when()
        .get("http://content.guardianapis.com/search?q=bitcoin&api-key=" + apiKey)
        .then().body("response.pageSize", equalTo(10));
  }

  @org.junit.Test
  public void testApiEndpoint3() {

    given().when()
        .get("http://content.guardianapis.com/search?q=bitcoin&api-key=" + apiKey)
        .then().body("response.results.webTitle", hasItems(containsString("bitcoin")));
  }

  @org.junit.Test
  public void testApiEndpoint4() {

    given().when()
        .get("http://content.guardianapis.com/search?q=bitcoin&api-key=" + apiKey)
        .then().body("response.results.sectionName", hasItems(containsString("Technology")));
  }

  @org.junit.Test
  public void testApiEndpoint5() {

    given().when()
        .get("http://content.guardianapis.com/search?q=bitcoin&api-key=" + apiKey)
        .then().body("response.results.sectionId", hasItems(containsString("technology")));
  }


  @org.junit.Test
  public void testMockingClient1() throws UnirestException {

    GuardianContentApi test = mock(GuardianContentApi.class);
    Response response = new Response();
    response.setCurrentPage(3);
    response.setPages(10);
    when( test.getContent("bitcoin")).thenReturn(response);
    Response responseMocked = test.getContent("bitcoin");
    assertEquals(3, responseMocked.getCurrentPage());
    assertEquals(10, responseMocked.getPages());
  }

  @org.junit.Test
  public void testMockingClient3() throws UnirestException {

    GuardianContentApi test = mock(GuardianContentApi.class);
    Response response = new Response();
    Article[] articles = new Article[]{new Article()};
    response.setResults(articles);
    when( test.getContent("bitcoin")).thenReturn(response);
    Response responseMocked = test.getContent("bitcoin");
    assertEquals(1, responseMocked.getResults().length);
  }
  
}