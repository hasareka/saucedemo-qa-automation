package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutOverviewPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "finish")
    private WebElement finishBtn;

    @FindBy(id = "cancel")
    private WebElement cancelBtn;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isLoaded() {

        try {
            wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
            return finishBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getTotalText() {

        wait.until(ExpectedConditions.visibilityOf(totalLabel));

        return totalLabel.getText();
    }

    public CheckoutCompletePage finishCheckout() {

        wait.until(ExpectedConditions.elementToBeClickable(finishBtn)).click();

        return new CheckoutCompletePage(driver);
    }

    public InventoryPage cancelCheckout() {

        wait.until(ExpectedConditions.elementToBeClickable(cancelBtn)).click();

        return new InventoryPage(driver);
    }
}