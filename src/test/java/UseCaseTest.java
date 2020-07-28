
import base.BasePage;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import page.CreateUseCasePage;
import page.DashboardPage;
import page.LoginPage;
import page.UseCasePage;

import java.io.IOException;
import java.util.*;

public class UseCaseTest extends BaseTest {

    public UseCaseTest() throws IOException {
        super();
    }

    private UseCasePage getUseCasePage() throws IOException {
        LoginPage loginPage;
        loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.login(properties.getValue("USERNAME"), properties.getValue("PASSWORD"));
        UseCasePage useCasePage = dashboardPage.navigateToUseCasePage();
        return useCasePage;
    }

    @Test
    public void testCreate4UseCases() throws IOException, InterruptedException {
        UseCasePage useCasePage = getUseCasePage();
        int i = 0;
        while (i < 4) {
        String title = BasePage.getRandomString(7);
        String description = BasePage.getRandomString(8);
        String expectedResult = BasePage.getRandomString(8);
        List<String> useCaseSteps = Arrays.asList(BasePage.getRandomString(6), BasePage.getRandomString(6), BasePage.getRandomString(6), BasePage.getRandomString(6));
            CreateUseCasePage newUseCasePage = useCasePage.openCreateNewUseCaseForm();
            newUseCasePage.validateNewUseCasePage();
            newUseCasePage.createNewUseCase(title, description, expectedResult, useCaseSteps);
            newUseCasePage.verifyUseCase(title, description, expectedResult,useCaseSteps);
            i++;
        }
    }

    @Test
    public void testUpdateUseCaseFields() throws IOException, InterruptedException {
        UseCasePage useCasePage = getUseCasePage();
        List<WebElement> listOfUseCases;
        for (int i = 0; i < 4; i++) {
            listOfUseCases = useCasePage.getListOfUseCases();
            listOfUseCases.get(i).click();
            CreateUseCasePage useCase = new CreateUseCasePage(driver);
            useCase.editUseCase();
        }

    }


}

