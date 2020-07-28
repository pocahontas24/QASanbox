package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

public class CreateUseCasePage extends UseCasePage {

    public CreateUseCasePage(WebDriver driver) throws IOException {
        super(driver);
    }

    @FindBy(name = "title")
    private WebElement inpTitle;

    @FindBy(name = "description")
    private WebElement inpDescription;

    @FindBy(name = "expected_result")
    private WebElement inpExpectedResult;

    @FindBy(id = "stepId")
    private WebElement inpStep;

    @FindBy(xpath = "//button[@data-testid='add_step_btn']")
    private WebElement btnAddStep;

    @FindBy(xpath = "//button[@data-testid='submit_btn']")
    private WebElement btnSubmit;

    @FindBy(xpath = "//label[@class='pt-1 ml-2']")
    private WebElement chkSwitch;

    @FindBy(name = "testStepId-0")
    private WebElement inpStep0;

    @FindBy(name = "testStepId-1")
    private WebElement inpStep1;

    @FindBy(xpath = "//a[@href='/use-cases']")
    private WebElement btnBack;

    protected WebDriverWait wait = new WebDriverWait(driver, 20);


    public void validateNewUseCasePage() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit));
        assertEquals("Create Use Case Page: Placeholder for input field title is not correct!", inpTitle.getAttribute("placeholder"), "* Title");
        assertEquals("Create Use Case Page: Placeholder for input field description is not correct!", inpDescription.getAttribute("placeholder"), "Description");
        assertEquals("Create Use Case Page: Placeholder for input field expected result is not correct!", inpExpectedResult.getAttribute("placeholder"), "* Expected Result");
        assertEquals("Create Use Case Page: Placeholder for input field steps is not correct!", inpStep.getAttribute("placeholder"), "* Use case step 1");
        assertTrue("Input field title should be enabled!", inpTitle.isEnabled());
        assertTrue("Input field description should be enabled!", inpDescription.isEnabled());
        assertTrue("Input field expected result should be enabled!", inpExpectedResult.isEnabled());
        assertTrue("Input field steps should be enabled!", inpStep.isEnabled());
    }

    public void verifyUseCase(String title, String description, String expectedResult, List<String> listOfSteps) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        assertEquals("The latest use case is not displayed in the list of use cases!", title,
                getListOfUseCases().get(0).getText().trim());
        CreateUseCasePage newCase = openUseCase();
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit));
        assertEquals("Title is not correct!", title, inpTitle.getAttribute("value"));
        assertEquals("Description is not correct!", description, inpDescription.getAttribute("value"));
        assertEquals("Expected result is not correct!", expectedResult, inpExpectedResult.getAttribute("value"));
        for (int i = 0; i < listOfSteps.size(); i++) {
            WebElement inpStep = driver.findElement(By.name("testStepId-" + i));
            assertEquals("Step one is not correct!", listOfSteps.get(i), inpStep.getAttribute("value"));
        }
        btnBack.click();
    }

    public void clearAllInputFields() {
        inpTitle.clear();
        inpDescription.clear();
        inpExpectedResult.clear();
        inpStep.clear();
    }

    private List<WebElement> getSteps() {
        return driver.findElements(By.id("stepId"));
    }


    public void createNewUseCase(String title, String description, String expectedResult, List<String> listOfSteps) {
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit));
        wait.until(ExpectedConditions.elementToBeClickable(chkSwitch));
        clearAllInputFields();
        inpTitle.sendKeys(title);
        inpDescription.sendKeys(description);
        inpExpectedResult.sendKeys(expectedResult);

        for (int i = 0; i < listOfSteps.size(); i++) {
            WebElement inpStep = driver.findElement(By.name("testStepId-" + i));
            inpStep.sendKeys(listOfSteps.get(i));
            btnAddStep.click();
        }

        chkSwitch.click();
        btnSubmit.click();
    }

    public int letterCounter(String sentence) {
        int lenghtOfSentence = sentence.length();
        return lenghtOfSentence;
    }


    public void editUseCase() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit));
        int countTitle = letterCounter(inpTitle.getAttribute("value"));
        int countDescription = letterCounter(inpDescription.getAttribute("value"));
        int countExpectedResult = letterCounter(inpExpectedResult.getAttribute("value"));
        inpTitle.clear();
        inpTitle.sendKeys("The field previously had " + countTitle + " characters.");
        inpDescription.clear();
        inpDescription.sendKeys("The field previously had " + countDescription + " characters.");
        inpExpectedResult.clear();
        inpExpectedResult.sendKeys("The field previously had " + countExpectedResult + " characters.");

        int countStep;
        List<WebElement> stepList = getSteps();
        for (int i = 0; i < stepList.size(); i++) {
            countStep = letterCounter(stepList.get(i).getAttribute("value"));
            stepList.get(i).clear();
            stepList.get(i).sendKeys("The field previously had " + countStep + " characters.");
        }

        btnSubmit.click();

    }
}
