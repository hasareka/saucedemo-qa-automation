package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class InventoryPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(className = "title")
    WebElement pageTitle; // "Products"

    @FindBy(className = "inventory_item")
    List<WebElement> inventoryItems;

    @FindBy(id = "shopping_cart_container")
    WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    WebElement cartBadge;

    @FindBy(className = "product_sort_container")
    WebElement sortDropdown;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isLoaded() {
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        return pageTitle.isDisplayed() && pageTitle.getText().equalsIgnoreCase("Products");
    }

    public int getProductCount() {
        wait.until(ExpectedConditions.visibilityOfAllElements(inventoryItems));
        return inventoryItems.size();
    }

    // Add to cart by product name (e.g., "Sauce Labs Backpack")
    public void addToCart(String productName) {
        By addBtn = By.xpath("//div[@class='inventory_item_name ' and text()='" + productName + "']/ancestor::div[@class='inventory_item']//button[contains(text(),'Add to cart')]");
        wait.until(ExpectedConditions.elementToBeClickable(addBtn)).click();
    }

    public void removeFromCart(String productName) {
        By removeBtn = By.xpath("//div[@class='inventory_item_name ' and text()='" + productName + "']/ancestor::div[@class='inventory_item']//button[contains(text(),'Remove')]");
        wait.until(ExpectedConditions.elementToBeClickable(removeBtn)).click();
    }

    public boolean isRemoveButtonVisible(String productName) {
        By removeBtn = By.xpath("//div[@class='inventory_item_name ' and text()='" + productName + "']/ancestor::div[@class='inventory_item']//button[contains(text(),'Remove')]");
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(removeBtn)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getCartBadgeCount() {
        try {
            wait.until(ExpectedConditions.visibilityOf(cartBadge));
            return Integer.parseInt(cartBadge.getText().trim());
        } catch (Exception e) {
            return 0; // badge not visible means 0
        }
    }

    public CartPage goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
        return new CartPage(driver);
    }

    public void sortByVisibleText(String text) {
        wait.until(ExpectedConditions.visibilityOf(sortDropdown));
        new Select(sortDropdown).selectByVisibleText(text);
    }

    public double getFirstItemPrice() {
        // after sorting, check first visible price
        By firstPrice = By.cssSelector(".inventory_item_price");
        String p = wait.until(ExpectedConditions.visibilityOfElementLocated(firstPrice)).getText().replace("$", "");
        return Double.parseDouble(p);
    }
}
