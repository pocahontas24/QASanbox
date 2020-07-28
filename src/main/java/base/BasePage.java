package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import util.PropertiesUtil;

import java.io.IOException;
import java.util.UUID;

public class BasePage {

    public WebDriver driver;
    public PropertiesUtil properties;

    public BasePage(WebDriver driver) throws IOException {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        properties = new PropertiesUtil(PropertiesUtil.ADMIN_PROPERTIES);
    }

    public static String getRandomEmailAddress(String email) {
        return email.split("@")[0] + "+" + UUID.randomUUID().toString().substring(0, 5) + "@" + email.split("@")[1];
    }

    public static String getRandomString(int length) {
        return UUID.randomUUID().toString().substring(0, length);
    }

}
