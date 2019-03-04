package multilayered.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl = "http://localhost/litecart";

    public Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }
}