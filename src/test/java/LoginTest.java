import base.BasePage;
import org.testng.annotations.*;
import page.DashboardPage;
import page.LoginPage;

import java.io.IOException;

public class LoginTest extends BaseTest {

    public LoginTest() throws IOException {
        super();
    }

    private LoginPage getLoginPage() throws IOException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.verifyLoginPage();
        return loginPage;
    }

    @Test
    public void testLoginLogout() throws IOException {
        LoginPage loginPage = getLoginPage();
        DashboardPage dashboardPage = loginPage.login(properties.getValue("USERNAME"), properties.getValue("PASSWORD"));
        dashboardPage.verifyHeader();
        dashboardPage.verifyDashboardPage();
        loginPage = dashboardPage.logout();
        loginPage.verifyLoginPage();
    }

    @Test
    public void testLoginWithoutCredentials() throws IOException {
        LoginPage loginPage = getLoginPage();
        loginPage.loginWithoutCredentials();
    }

    @Test
    public void testLoginWithWrongUsername() throws IOException {
        LoginPage loginPage = getLoginPage();
        String wrongEmail = BasePage.getRandomEmailAddress(properties.getValue("EXAMPLE.EMAIL"));
        loginPage.loginWithWrongUsername(wrongEmail);
    }

    @Test
    public void testLoginWithWrongPassword() throws IOException {
        LoginPage loginPage = getLoginPage();
        String randomPass = BasePage.getRandomString(4);
        loginPage.loginWithWrongPassword(randomPass);
    }

}
