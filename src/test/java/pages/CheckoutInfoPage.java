package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutInfoPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "first-name")
    private WebElement firstName;

    @FindBy(id = "last-name")
    private WebElement lastName;

    @FindBy(id = "postal-code")
    private WebElement postalCode;

    @FindBy(id = "continue")
    private WebElement continueBtn;

    @FindBy(id = "cancel")
    private WebElement cancelBtn;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    public CheckoutInfoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
            return continueBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void fillInformation(String first, String last, String zip) {

        firstName.clear();
        firstName.sendKeys(first);

        lastName.clear();
        lastName.sendKeys(last);

        postalCode.clear();
        postalCode.sendKeys(zip);
    }

    public CheckoutOverviewPage continueCheckout() {

        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();

        return new CheckoutOverviewPage(driver);
    }

    public CartPage cancelCheckout() {

        wait.until(ExpectedConditions.elementToBeClickable(cancelBtn)).click();

        return new CartPage(driver);
    }

    public String getErrorMessage() {

        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }
}