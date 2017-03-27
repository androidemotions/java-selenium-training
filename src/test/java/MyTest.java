import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

public class MyTest {

    private WebDriver driver;

    @Before
    public void start() {
        //driver = new ChromeDriver();
        driver = new RemoteWebDriver(DesiredCapabilities.chrome());
    }

    @Test
    public void MyFirstTest() {
        driver.get("http://www.google.com");

        List<WebElement> input = (List<WebElement>) ((JavascriptExecutor) driver).executeScript("return document.getElementsByName('q')");
        input.get(0).sendKeys("webdriver");



        if(isElementPresent(By.name("xxx")))
        {
            System.out.println("Element found");
        }
        else
        {
            System.out.println("Element not found");
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        }
        catch (InvalidSelectorException ex)
        {
            throw ex;
        }
        catch (NoSuchElementException ex) {
            return false;
        }
    }

    boolean areElementsPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }

}