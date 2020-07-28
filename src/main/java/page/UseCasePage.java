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

public class UseCasePage extends BasePage {

    public UseCasePage(WebDriver driver) throws IOException {
        super(driver);
    }

    @FindBy(xpath = "//a[@href='/dashboard']")
    private WebElement btnBack;

    @FindBy(className = "text-center page-title")
    private WebElement lblHeader;

    @FindBy(linkText = "CREATE USE CASE")
    private WebElement btnCreateUseCase;

    protected WebDriverWait wait = new WebDriverWait(driver, 20);

    public List<WebElement> getListOfUseCases() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(btnCreateUseCase));
        return driver.findElements(By.xpath("//a[contains(@href,'use-cases')]"));
    }

    public CreateUseCasePage openCreateNewUseCaseForm() throws IOException {
        wait.until(ExpectedConditions.elementToBeClickable(btnCreateUseCase));
        btnCreateUseCase.click();
        return new CreateUseCasePage(driver);
    }

    public CreateUseCasePage openUseCase() throws InterruptedException, IOException {
        wait.until(ExpectedConditions.elementToBeClickable(btnCreateUseCase));
        getListOfUseCases().get(0).click();
        return new CreateUseCasePage(driver);
    }


}
