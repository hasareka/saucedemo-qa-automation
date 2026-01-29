package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CartPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(className = "title")
    WebElement pageTitle; // "Your Cart"

    @FindBy(id = "continue-shopping")
    WebElement continueShoppingBtn;

    @FindBy(id = "checkout")
    WebElement checkoutBtn;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isLoaded() {
        wait.until(ExpectedConditions.urlContains("cart.html"));
        return pageTitle.isDisplayed();
    }

    public boolean isItemPresent(String productName) {
        By item = By.xpath("//div[@class='inventory_item_name' and text()='" + productName + "']");
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(item)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void removeItem(String productName) {
        By removeBtn = By.xpath("//div[@class='inventory_item_name' and text()='" + productName + "']/ancestor::div[@class='cart_item']//button[contains(text(),'Remove')]");
        wait.until(ExpectedConditions.elementToBeClickable(removeBtn)).click();
    }

    public InventoryPage continueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingBtn)).click();
        return new InventoryPage(driver);
    }

    public CheckoutInfoPage checkout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
        return new CheckoutInfoPage(driver);
    }
}
