package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutCompletePage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "complete-header")
    private WebElement completeHeader;

    @FindBy(id = "back-to-products")
    private WebElement backHomeButton;

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isLoaded() {

        try {
            wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
            return completeHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getHeaderText() {

        return completeHeader.getText();
    }

    public InventoryPage backToProducts() {

        wait.until(ExpectedConditions.elementToBeClickable(backHomeButton)).click();

        return new InventoryPage(driver);
    }
}