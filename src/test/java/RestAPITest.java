import base.RestAPIBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

public class RestAPITest extends BaseTest {

    public RestAPITest() throws IOException {
        super();
    }

    //Test for REST API -> GET /usecases/all to get all use cases
    @Test
    public void testGETAllUseCases() throws IOException {
        RestAPIBase restAPIBase = new RestAPIBase();
        int responseCodeLogin = restAPIBase.methodLoginPOST();
        assertEquals("REST API Test: Action not authorized!", 200, responseCodeLogin);
        Response response = restAPIBase.methodGET("usecases/all");
        JsonPath usecases = response.jsonPath();
        int responseCode = response.getStatusCode();
        ArrayList<String> listOFUseCases = usecases.get("usecase_id");
        assertEquals("Status code should be 200!", 200, responseCode);
        assertTrue("Response is empty!", listOFUseCases.size()!=0);
    }

    @Test
    public void testPOSTLogin() throws IOException {
        RestAPIBase restAPIBase = new RestAPIBase();
        int statusCode = restAPIBase.methodLoginPOST();
        assertEquals("User was not logged successfully!", 200, statusCode);
    }

    //Test for REST API -> POST /users/login but with incorrect data
    @Test
    public void testPOSTLoginWithWrongEmail() throws IOException {
        RestAPIBase restAPIBase = new RestAPIBase();
        Response response = restAPIBase.methodPOSTLoginInvalidEmail();
        int statusCode = response.getStatusCode();
        JsonPath jsonPath = response.jsonPath();
        assertEquals("User should not be able to login!", 404, statusCode);
        assertEquals("Message in response should be User not found!", "User not found", jsonPath.get("email"));
    }

    @Test
    public void testPOSTLoginWithInvalidPassword() throws IOException {
        RestAPIBase restAPIBase = new RestAPIBase();
        Response response = restAPIBase.methodPOSTLoginInvalidPassword();
        JsonPath jsonPath = response.jsonPath();
        assertEquals("User should not be able to login!", 400, response.getStatusCode());
        assertEquals("Password error message is not correct.", properties.getValue("ERR.PASSWORD.REQUIREMENTS"), jsonPath.get("password"));
    }

    // Test for REST API -> POST /usecases/usecase to create new use case
    @Test
    public void testPOSTCreateUseCase() throws IOException {
        RestAPIBase restAPIBase = new RestAPIBase();
        int responseCodeLogin = restAPIBase.methodLoginPOST();
        assertEquals("User should be logged in!", 200, responseCodeLogin);
        Response response = restAPIBase.methodPOSTCreateUseCase();
        JsonPath jsonPath = response.jsonPath();
        int statusCode = response.getStatusCode();
        assertEquals("Use case not created successfully!", 200, statusCode);
    }

    // Test for REST API -> DELETE /usecases/usecase/usecase_id (create use case and delete it)
    @Test
    public void testDELETEUseCase() throws IOException {
        RestAPIBase restAPIBase = new RestAPIBase();
        int responseCodeLogin = restAPIBase.methodLoginPOST();
        assertEquals("User should be logged in!", 200, responseCodeLogin);
        JsonPath usecasesBefore = restAPIBase.methodGET("usecases/all").jsonPath();
        ArrayList<Integer> listOFUseCasesBefore = usecasesBefore.get("usecase_id");
        int numberOfUseCasesBefore = listOFUseCasesBefore.size();
        restAPIBase.methodPOSTCreateUseCase();
        JsonPath usecasesAfter = restAPIBase.methodGET("usecases/all").jsonPath();
        ArrayList<Integer> listOFUseCasesAfter = usecasesAfter.get("usecase_id");
        int numberOfUseCasesAfter = listOFUseCasesAfter.size();
        assertEquals("Use case is not created successfully!",numberOfUseCasesBefore+1 ,numberOfUseCasesAfter);
        Response response = restAPIBase.methodDELETEUseCase("usecases/usecase/" + listOFUseCasesAfter.get(0));
        JsonPath jsonPath = response.jsonPath();
        JsonPath usecasesAfterDeletion = restAPIBase.methodGET("usecases/all").jsonPath();
        ArrayList<Integer> listOFUseCasesAfterDeletion = usecasesAfterDeletion.get("usecase_id");
        int numberOfUseCasesAfterDeletion = listOFUseCasesAfterDeletion.size();
        assertEquals("Use case was not deleted successfully!", numberOfUseCasesBefore, numberOfUseCasesAfterDeletion);
        assertEquals("Use case was not deleted successfully.", 200, response.getStatusCode());
        assertEquals("Message in response is not as expected!", "Entry removed successfully", jsonPath.get("Success"));
    }


}
