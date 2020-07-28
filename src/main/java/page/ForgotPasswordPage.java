package page;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

import java.io.IOException;

public class ForgotPasswordPage extends BasePage {

    public ForgotPasswordPage(WebDriver driver) throws IOException {
        super(driver);
    }

    @FindBy(name = "email")
    private WebElement inpEmail;

    @FindBy(xpath = "//a[@href='/dashboard']")
    private WebElement btnBack;

    @FindBy(className = "invalid-feedback")
    private WebElement errMsg;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement btnSubmit;

    @FindBy(xpath = "//div[@role='alert']")
    private WebElement msgAlert;

    protected WebDriverWait wait = new WebDriverWait(driver, 20);

    public void verifyForgotPasswordPage() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit));
        assertEquals("Forgot Password Page: Placeholder is not good!", inpEmail.getAttribute("placeholder"), "* Email Address");
        assertTrue("Forgot Password Page : Back button is not clickable!", btnBack.isEnabled());
        assertTrue("Forgot Password Page : Submit button is not clickable!", btnSubmit.isEnabled());
    }

    public void changePassword(String email) throws IOException {
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit));
        inpEmail.clear();
        inpEmail.sendKeys(email);
        btnSubmit.click();
    }

    public void verifyAlert() {
        wait.until(ExpectedConditions.visibilityOf(msgAlert));
        assertEquals("Alert message is not correct!", "Email successfully sent", msgAlert.getText());
    }


}
