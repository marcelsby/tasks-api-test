package br.ce.wcaquino.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

  @BeforeClass
  public static void setup() {
    RestAssured.baseURI = System.getProperty("api.baseuri");
  }

  @Test
  public void deveRetornarTarefas() {
    RestAssured
        .when().get("/todo")
        .then().statusCode(200);
  }

  @Test
  public void deveAdicionarTarefaComSucesso() {
    RestAssured
        .given().body("{\"task\":\"Teste via RESTAssured\",\"dueDate\":\"2999-01-01\"}").contentType(ContentType.JSON)
        .when().post("/todo")
        .then().statusCode(201);
  }

  @Test
  public void naoDeveAdicionarTarefaInvalida() {
    RestAssured
        .given().body("{\"task\":\"Teste via RESTAssured\",\"dueDate\":\"2001-01-01\"}").contentType(ContentType.JSON)
        .when().post("/todo")
        .then().statusCode(400).body("message", CoreMatchers.is("Due date must not be in past"));
  }

  @Test
  public void deveRemoverTarefaComSucesso() {
    Integer savedTaskId = RestAssured
        .given().body("{\"task\":\"Teste via RESTAssured\",\"dueDate\":\"2999-01-01\"}").contentType(ContentType.JSON)
        .when().post("/todo")
        .then().statusCode(201)
        .extract().path("id");

    RestAssured
        .when().delete("/todo/" + savedTaskId)
        .then().statusCode(204);
  }
}


