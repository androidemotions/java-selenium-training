package multilayered.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainStorePage extends Page {

    public MainStorePage(WebDriver driver) {
        super(driver);
    }

    public void logoutCurrentUser()
    {
       wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*=logout]"))).click();
    }
}
