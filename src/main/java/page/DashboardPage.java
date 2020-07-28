package page;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.*;

import java.io.IOException;

public class DashboardPage extends BasePage {


    public DashboardPage(WebDriver driver) throws IOException{
        super(driver);
    }

    @FindBy(tagName = "h5")
    private WebElement lblDashboard;

    @FindBy(className = "progress-bar")
    private WebElement progressBar;

    @FindBy(xpath = "//h5[@class='card-title text-center pb-3']")
    private WebElement lblFirstLastName;

    @FindBy(xpath = "//a[@href='/profile']")
    private WebElement profileTab;

    @FindBy(xpath = "//a[@href='/use-cases']")
    private WebElement usecaseTab;

    @FindBy(xpath = "//a[@href='/projects']")
    private WebElement projectsTab;

    @FindBy(xpath = "//a[@href='/reports']")
    private WebElement reportsTab;

    @FindBy(linkText = "Logout")
    private WebElement btnLogout;

    @FindBy(linkText = "Intro")
    private WebElement btnIntro;

    @FindBy(linkText = "Exam")
    private WebElement btnExam;

    @FindBy(linkText = "Sandbox")
    private WebElement btnSandbox;

    @FindBy(tagName = "b")
    private WebElement lblHeader;

    @FindBy(xpath = "//img[@class='card-img-top rounded-circle']")
    private WebElement imgUser;

    protected WebDriverWait wait = new WebDriverWait(driver, 20);

    public void verifyDashboardPage(){
        wait.until(ExpectedConditions.elementToBeClickable(usecaseTab));
        wait.until(ExpectedConditions.elementToBeClickable(btnLogout));
        assertEquals("Dashboard Page: Header is not correct!", "Dashboard", lblDashboard.getText().trim());
        assertTrue("Dashboard Page: Use case button should be enabled!", usecaseTab.isEnabled());
        assertTrue("Dashboard Page: Projects button should be enabled!", projectsTab.isEnabled());
        assertTrue("Dashboard Page: Reports button should be enabled!", reportsTab.isEnabled());
    }

    public void verifyHeader(){
        wait.until(ExpectedConditions.elementToBeClickable(btnLogout));
        assertEquals("Dashboard Page: Header is not correct!", "QA Sandbox", lblHeader.getText().trim());
        assertEquals("Dashboard Page: Link text for Dashboard is not correct!", "Sandbox", btnSandbox.getText().trim());
        assertEquals("Dashboard Page: Link text for Exam is not correct", "Exam", btnExam.getText().trim());
        assertEquals("Dashboard Page: Link text for Intro is not correct", "Intro", btnIntro.getText().trim());
        assertEquals("Dashboard Page: Link text for Logout is not correct", "Logout", btnLogout.getText().trim());
    }

    public LoginPage logout() throws IOException{
        wait.until(ExpectedConditions.elementToBeClickable(btnLogout));
        btnLogout.click();
        return new LoginPage(driver);
    }

    public UseCasePage navigateToUseCasePage() throws IOException {
        wait.until(ExpectedConditions.elementToBeClickable(usecaseTab));
        usecaseTab.click();
        return new UseCasePage(driver);
    }


}
