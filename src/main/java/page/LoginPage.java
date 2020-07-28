package page;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

public class LoginPage extends BasePage {


    public LoginPage(WebDriver driver) throws IOException {
        super(driver);
        driver.get(properties.getValue("URL.AUTH.LOGIN"));
    }

    @FindBy(tagName = "h1")
    private WebElement strHeader;

    @FindBy(name = "email")
    private WebElement inpUsername;

    @FindBy(name = "password")
    private WebElement inpPassword;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement btnSubmit;

    @FindBy(xpath = "//*/form/div[1]/div")
    private WebElement errUsername;

    @FindBy(xpath = "//*/form/div[2]/div")
    private WebElement errPassword;

    private List<WebElement> getErrorMessages() {
        return driver.findElements(By.className("invalid-feedback"));
    }

    protected WebDriverWait wait = new WebDriverWait(driver, 20);

    public void verifyLoginPage() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit));
        assertEquals("Login Page: Title is not good!", properties.getValue("LOGIN.HEADER"), strHeader.getText().trim());
        assertTrue("Login Page: The username input is not enabled!", inpUsername.isEnabled());
        assertEquals("Login Page : Placeholder for input field username is not correct!", properties.getValue("PLACEHOLDER.USERNAME"), inpUsername.getAttribute("Placeholder"));
        assertEquals("Login Page : Placeholder for input field password is not correct!", properties.getValue("PLACEHOLDER.PASSWORD"), inpPassword.getAttribute("Placeholder"));
        assertTrue("Login Page: The Submit button is enabled!", btnSubmit.isEnabled());
        assertTrue("Login Page: There are some validation error messages!", getErrorMessages().isEmpty());
    }

    public DashboardPage login(String username, String password) throws IOException {
        enterCredentials(username, password);
        return new DashboardPage(driver);
    }

    public void enterCredentials(String username, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(inpUsername));
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit));
        inpUsername.clear();
        inpUsername.sendKeys(username);
        inpPassword.clear();
        inpPassword.sendKeys(password);
        btnSubmit.click();
    }

    public void loginWithoutCredentials() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit));
        inpUsername.clear();
        inpPassword.clear();
        btnSubmit.click();
        wait.until(ExpectedConditions.visibilityOf(errPassword));
        wait.until(ExpectedConditions.visibilityOf(errUsername));
        assertEquals("Login Page: Username field should not be empty!", properties.getValue("ERR.USERNAME.REQUIRED"), errUsername.getText().trim());
        assertEquals("Login Page: Password field should not be empty!", properties.getValue("ERR.PASSWORD.REQUIRED"), errPassword.getText().trim());
        assertEquals("Login Page: Number of error messages on page should be 2!", getErrorMessages().size(), 2);
    }

    public void loginWithWrongUsername(String username) {
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit));
        enterCredentials(username, properties.getValue("PASSWORD"));
        btnSubmit.click();
        wait.until(ExpectedConditions.visibilityOf(errUsername));
        assertEquals("Login Page: User is not registered!", properties.getValue("ERR.USERNAME.NOUSER"), errUsername.getText().trim());
        assertEquals("Login Page: Number of error messages should be 1!", getErrorMessages().size(), 1);
    }

    public void loginWithWrongPassword(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit));
        enterCredentials(properties.getValue("USERNAME"), password);
        btnSubmit.click();
        wait.until(ExpectedConditions.visibilityOf(errPassword));
        assertEquals("Login Page: Password must be at least 6 characters long!", properties.getValue("ERR.PASSWORD.REQUIREMENTS"), errPassword.getText().trim());
        assertEquals("Login Page: Number of error messages should be 1!", getErrorMessages().size(), 1);
    }



}
