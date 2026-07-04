package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingBtn;

    @FindBy(id = "checkout")
    private WebElement checkoutBtn;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Verify Cart page is loaded.
     */
    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.urlContains("cart.html"));
            return pageTitle.isDisplayed()
                    && pageTitle.getText().equalsIgnoreCase("Your Cart");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks whether a product exists in the cart.
     */
    public boolean isItemPresent(String productName) {
        By item = By.xpath("//div[@class='inventory_item_name' and text()='" + productName + "']");

        return driver.findElements(item).size() > 0;
    }

    /**
     * Removes a product from the cart.
     */
    public void removeItem(String productName) {

        By removeButton = By.xpath(
                "//div[@class='inventory_item_name' and text()='" + productName +
                        "']/ancestor::div[@class='cart_item']//button");

        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();
    }

    /**
     * Click Continue Shopping.
     */
    public InventoryPage continueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingBtn)).click();
        return new InventoryPage(driver);
    }

    /**
     * Click Checkout.
     */
    public CheckoutInfoPage checkout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
        return new CheckoutInfoPage(driver);
    }

    /**
     * Returns the number of items in the cart.
     */
    public int getCartItemCount() {
        return driver.findElements(By.className("cart_item")).size();
    }

    /**
     * Returns the name of the first product.
     */
    public String getFirstItemName() {
        return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.className("inventory_item_name")))
                .getText();
    }

    /**
     * Returns the first product price.
     */
    public String getFirstItemPrice() {
        return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.className("inventory_item_price")))
                .getText();
    }
}