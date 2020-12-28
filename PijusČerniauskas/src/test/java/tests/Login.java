import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ComponentUtils;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class Login {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

    @Test
    public void opencart() throws InterruptedException {
        driver.manage().window().maximize();
        driver.get("https://demo.opencart.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement loginError = driver.findElement(By.id("login-error"));
        System.out.println(loginError.isDisplayed());

        driver.findElement(By.partialLinkText("MyAccount")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("Login"))));
        driver.findElement(By.name("username")).sendKeys("demo.opencart@demo.opencart.com");
        driver.findElement(By.name("password")).sendKeys("slaptazodis");
        driver.findElement(By.id("login-button")).click();
    }

    @Test(dataProvider = "AddToCart")
    public void categoryTest(String mainCategory, String subCategory) {
        boolean allProductOutOfStock = true;

        driver.findElement(By.linkText("MP3 Players")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("dropdown-menu")));
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.partialLinkText(mainCategory))).build().perform();

        driver.findElement(By.partialLinkText(subCategory)).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("see-all")));
        String title = driver.getTitle();
        assertTrue(title.contains(subCategory), "Blogas title: " + title);

        List<WebElement> products = driver.findElements(By.cssSelector(".product-list-product"));
        for (WebElement product : products) {
            ComponentUtils.scrollToElement(driver, product);
            boolean inStock = product.findElement(By.cssSelector(".product-list-product-stock-status img")).getAttribute("alt").equals("in stock");
            if (inStock) {

                product.findElement(By.cssSelector(".btn-add-cart")).click();
                allProductOutOfStock = false;
                break;
            }
        }


    }
    @DataProvider(name = "AddToCart")
    public static Object[][] AddToCart() {
        return new Object[][]{
                {"iPod Nano"},
                {"iPod Touch"},
                {"iPod Shuffle"}
        };
    }
}
