package page;

import base.BasePage;
import org.testng.asserts.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.*;
import java.io.IOException;

public class WelcomePage extends BasePage {


    public WelcomePage(WebDriver driver) throws IOException {
        super(driver);
        driver.get(properties.getValue("URL"));
    }

    @FindBy(tagName = "h1")
    private WebElement strHeader;

    @FindBy(xpath = "//a[@href='/forgot-password']")
    private WebElement btnForgotPassword;

    @FindBy(xpath = "//a[@href='/login']")
    private WebElement btnLogin;

    protected WebDriverWait wait = new WebDriverWait(driver, 20);

    public void validateWelcomePage(){
        wait.until(ExpectedConditions.elementToBeClickable(btnForgotPassword));
        assertEquals("Welcome Page: Title is not good!", properties.getValue("WELCOME.HEADER") , strHeader.getText().trim());
        assertTrue("Welcome Page: Forgot password button is not enabled!", btnForgotPassword.isEnabled());
        assertTrue("Welcome Page: Login button is not enabled!", btnLogin.isEnabled());
    }

    public ForgotPasswordPage navigateToForgotPasswordPage() throws IOException {
        wait.until(ExpectedConditions.elementToBeClickable(btnForgotPassword));
        btnForgotPassword.click();
        return new ForgotPasswordPage(driver);
    }

}
