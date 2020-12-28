package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ComponentUtils {
    /**
     * Scrolls to the needed WebElement
     *
     * @param driver  WebDriver
     * @param element WebElement
     */
    public static void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    /**
     * Hovers over the elements
     *
     * @param driver  WebDriver
     * @param element WebElement
     */
    public static void hoverOverElement(WebDriver driver, WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }
}
