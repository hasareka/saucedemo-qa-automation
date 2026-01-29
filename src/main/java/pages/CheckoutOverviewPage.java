package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CheckoutOverviewPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "finish")
    WebElement finishBtn;

    @FindBy(className = "summary_total_label")
    WebElement totalLabel;

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isLoaded() {
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
        return finishBtn.isDisplayed();
    }

    public String getTotalText() {
        wait.until(ExpectedConditions.visibilityOf(totalLabel));
        return totalLabel.getText();
    }

    public CheckoutCompletePage finish() {
        wait.until(ExpectedConditions.elementToBeClickable(finishBtn)).click();
        return new CheckoutCompletePage(driver);
    }
}
