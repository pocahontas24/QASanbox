package base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ConnectionConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import util.PropertiesUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class RestAPIBase {

    protected static final String ADMIN_PROPERTIES = "admin.properties";
    protected PropertiesUtil properties = new PropertiesUtil(ADMIN_PROPERTIES);
    private RestAssuredConfig restAssuredConfig;
    private RequestSpecBuilder builder;

    public RestAPIBase() throws IOException {
        super();
        RestAssured.baseURI = properties.getValue("BASE.URI");
        RestAssured.basePath = properties.getValue("BASE.PATH");
        ConnectionConfig connectionConfig = new ConnectionConfig().closeIdleConnectionsAfterEachResponseAfter(120,
                TimeUnit.SECONDS);
        restAssuredConfig = new RestAssuredConfig().connectionConfig(connectionConfig);

    }

    public int methodLoginPOST() {
        Map<String, Object> jsonAsMap = new HashMap();
        jsonAsMap.put("email", properties.getValue("USERNAME"));
        jsonAsMap.put("password", properties.getValue("PASSWORD"));
        Response response = given().contentType("application/json").body(jsonAsMap).when().post("users/login");
        JsonPath jsonPath = response.jsonPath();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeader("content-type", "application/json;charset=UTF-8");
        requestSpecBuilder.addHeader("authorization", "Bearer " + jsonPath.get("token").toString());
        builder = requestSpecBuilder;
        return response.getStatusCode();
    }

    public Response methodGET(String endpoint) {
        RequestSpecification requestSpec = builder.build();
        return given().config(restAssuredConfig).spec(requestSpec).when().get(endpoint);
    }

    public Response methodPOSTLoginInvalidEmail() {
        Map<String, Object> jsonAsMap = new HashMap();
        jsonAsMap.put("email", properties.getValue("FALSE.EMAIL"));
        jsonAsMap.put("password", properties.getValue("FALSE.PASSWORD"));
        Response response = given().contentType("application/json").body(jsonAsMap).when().post("users/login");
        return response;
    }

    public Response methodPOSTLoginInvalidPassword() {
        Map<String, Object> jsonAsMap = new HashMap();
        jsonAsMap.put("email", properties.getValue("FALSE.EMAIL"));
        jsonAsMap.put("password", properties.getValue("SHORT.PASSWORD"));
        Response response = given().contentType("application/json").body(jsonAsMap).when().post("users/login");
        return response;
    }

    public Response methodPOSTCreateUseCase() {
        Map<String, Object> jsonAsMap = new HashMap();
        jsonAsMap.put("title", "Use case created using REST API /usecases/usecase");
        jsonAsMap.put("teststeps", new String[]{"Login to application", "Initialize header values", "Execute POST /usecases/usecase", "Check response code"});
        jsonAsMap.put("expected_result", "Response code 200");
        jsonAsMap.put("description", "This use case describes process of creation new use case using library Rest Assured in Selenium");
        jsonAsMap.put("automated", "true");
        RequestSpecification requestSpec = builder.build();
        Response response = given().spec(requestSpec).body(jsonAsMap).when().post("usecases/usecase");
        return response;
    }

    public Response methodDELETEUseCase(String endpoint) {
        RequestSpecification requestSpec = builder.build();
        return given().config(restAssuredConfig).spec(requestSpec).when().delete(endpoint);
    }


}
