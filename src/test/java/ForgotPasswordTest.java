import org.testng.annotations.Test;
import page.ForgotPasswordPage;
import page.WelcomePage;

import java.io.IOException;

public class ForgotPasswordTest extends BaseTest {

    public ForgotPasswordTest() throws IOException {
        super();
    }

    private ForgotPasswordPage getForgotPasswordPage() throws IOException {
        WelcomePage welcomePage = new WelcomePage(driver);
        welcomePage.validateWelcomePage();
        ForgotPasswordPage forgotPasswordPage = welcomePage.navigateToForgotPasswordPage();
        forgotPasswordPage.verifyForgotPasswordPage();
        return forgotPasswordPage;
    }

    @Test
    public void testChangePassword() throws IOException {
        ForgotPasswordPage forgotPasswordPage = getForgotPasswordPage();
        forgotPasswordPage.changePassword(properties.getValue("USERNAME"));
        forgotPasswordPage.verifyAlert();
    }
}
