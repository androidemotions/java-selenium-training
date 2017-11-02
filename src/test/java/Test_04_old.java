import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


@RunWith(Parameterized.class)
public class Test_04_old {

    @Parameterized.Parameter
    public WebDriver driver;


    public WebDriverWait wait;

    private String baseURL = "http://192.168.56.101/litecart/";

    @Before
    public void setUp() {
        wait = new WebDriverWait(driver, 5);
        driver.manage().window().maximize();
    }

    @Test
    public void homePageItemsStickerTest() {

        driver.navigate().to(baseURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#box-campaigns")));

        //Home screen verifications
        List<WebElement> items = driver.findElements(By.cssSelector("div#box-campaigns li.product.column.shadow"));

        if (items.size() == 0) Assert.fail("There is no Campaigns items on home screen");

        By itemName = By.cssSelector("div.name");
        By regularPrice = By.cssSelector("div.price-wrapper s.regular-price");
        By campaignsPrice = By.cssSelector("div.price-wrapper strong.campaign-price");

        WebElement regularPriceElement = items.get(0).findElement(regularPrice);
        WebElement campaignsPriceElement = items.get(0).findElement(campaignsPrice);

        String regularPriceValue = regularPriceElement.getText();
        String campaignsPriceValue = campaignsPriceElement.getText();
        String itemNameValue = items.get(0).findElement(itemName).getText();

        verifyCommonParametersOfFont(regularPriceElement, campaignsPriceElement);

        if (driver instanceof FirefoxDriver) {
            Assert.assertEquals(msgGrayColor, regularPriceElement.getCssValue("color"), "rgb(119, 119, 119)");
        } else {
            Assert.assertEquals(msgGrayColor, regularPriceElement.getCssValue("color"), "rgba(119, 119, 119, 1)");
        }
        if (!(driver instanceof ChromeDriver)) {
            Assert.assertEquals(msgBoldTextCamp, campaignsPriceElement.getCssValue("font-weight"), "900");
        }

        //Item Details page verifications
        items.get(0).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.title")));

        regularPriceElement = driver.findElement(regularPrice);
        campaignsPriceElement = driver.findElement(campaignsPrice);

        Assert.assertEquals("Name on item page is differ than on home screen",
                driver.findElement(By.cssSelector("h1.title")).getText(), itemNameValue);
        Assert.assertEquals("Regular price on item page is differ than on home screen",
                regularPriceElement.getText(), regularPriceValue);
        Assert.assertEquals("Campaigns price on item page is differ than on home screen",
                campaignsPriceElement.getText(), campaignsPriceValue);

        verifyCommonParametersOfFont(regularPriceElement, campaignsPriceElement);

        if (driver instanceof FirefoxDriver) {
            Assert.assertEquals(msgGrayColor, regularPriceElement.getCssValue("color"), "rgb(102, 102, 102)");
        } else {
            Assert.assertEquals(msgGrayColor, regularPriceElement.getCssValue("color"), "rgba(102, 102, 102, 1)");
        }
        if (!(driver instanceof ChromeDriver)) {
            Assert.assertEquals(msgBoldTextCamp, campaignsPriceElement.getCssValue("font-weight"), "700");
        }
    }

    private void verifyCommonParametersOfFont(WebElement regularPriceElement, WebElement campaignsPriceElement) {

        Float regularPriceFontSize = Float.parseFloat(regularPriceElement.getCssValue("font-size").replace("px", ""));
        Float campaignsPriceFontSize = Float.parseFloat(campaignsPriceElement.getCssValue("font-size").replace("px", ""));

        Assert.assertTrue(msgSizeFont, campaignsPriceFontSize > regularPriceFontSize);
        Assert.assertEquals(msgStrikeOutText, regularPriceElement.getCssValue("text-decoration"), "line-through");

        if (driver instanceof ChromeDriver) {
            Assert.assertEquals(msgNormalTextReg, regularPriceElement.getCssValue("font-weight"), "normal");
            Assert.assertEquals(msgBoldTextCamp, campaignsPriceElement.getCssValue("font-weight"), "bold");
        } else {
            Assert.assertEquals(msgNormalTextReg, regularPriceElement.getCssValue("font-weight"), "400");
        }

        if (driver instanceof FirefoxDriver) {
            Assert.assertEquals(msgRedColor, campaignsPriceElement.getCssValue("color"), "rgb(204, 0, 0)");
        } else {
            Assert.assertEquals(msgRedColor, campaignsPriceElement.getCssValue("color"), "rgba(204, 0, 0, 1)");
        }
        Assert.assertEquals(msgNormalTextCamp, campaignsPriceElement.getCssValue("text-decoration"), "none");
    }

    @After
    public void shutDown() {
        driver.quit();
        driver = null;
    }

    @Parameterized.Parameters
    public static WebDriver[] getDriver() {

        ChromeDriverManager.getInstance().setup();
        InternetExplorerDriverManager.getInstance().setup();
        FirefoxDriverManager.getInstance().setup();

        FirefoxOptions fopt= new FirefoxOptions();
        fopt.setCapability(FirefoxDriver.MARIONETTE,false);

        return new WebDriver[]{new FirefoxDriver(fopt), new ChromeDriver(), new InternetExplorerDriver()};
    }

    private static final String msgRedColor = "Text color is not Red for campaigns price";
    private static final String msgGrayColor = "Text color is not Gray for regular price";
    private static final String msgNormalTextReg = "Font style is non normal for regular price";
    private static final String msgNormalTextCamp = "Font style is non normal for campaigns price";
    private static final String msgBoldTextCamp = "Font style is non bold for campaigns price";
    private static final String msgStrikeOutText = "Text is not line-through for regular price";
    private static final String msgSizeFont = "Campaigns font is not bigger than regular";
}
