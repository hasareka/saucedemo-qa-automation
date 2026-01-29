package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CheckoutInfoPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "first-name")
    WebElement firstName;

    @FindBy(id = "last-name")
    WebElement lastName;

    @FindBy(id = "postal-code")
    WebElement postalCode;

    @FindBy(id = "continue")
    WebElement continueBtn;

    @FindBy(css = "h3[data-test='error']")
    WebElement error;

    public CheckoutInfoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isLoaded() {
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        return continueBtn.isDisplayed();
    }

    public void fillInfo(String f, String l, String zip) {
        wait.until(ExpectedConditions.visibilityOf(firstName)).clear();
        firstName.sendKeys(f);

        lastName.clear();
        lastName.sendKeys(l);

        postalCode.clear();
        postalCode.sendKeys(zip);
    }

    public CheckoutOverviewPage clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
        return new CheckoutOverviewPage(driver);
    }

    public String getErrorMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(error));
            return error.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
