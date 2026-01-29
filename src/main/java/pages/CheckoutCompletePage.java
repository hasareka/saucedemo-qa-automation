package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CheckoutCompletePage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(className = "complete-header")
    WebElement completeHeader;

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isLoaded() {
        wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
        return completeHeader.isDisplayed();
    }

    public String getHeaderText() {
        return completeHeader.getText();
    }
}
