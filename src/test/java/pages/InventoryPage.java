package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(id = "shopping_cart_container")
    private WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Verify the Inventory page is loaded.
     */
    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.urlContains("inventory.html"));
            return pageTitle.isDisplayed() &&
                    pageTitle.getText().equalsIgnoreCase("Products");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns the total number of products displayed.
     */
    public int getProductCount() {
        wait.until(ExpectedConditions.visibilityOfAllElements(inventoryItems));
        return inventoryItems.size();
    }

    /**
     * Returns the names of all displayed products.
     */
    public List<String> getProductNames() {
        return driver.findElements(By.className("inventory_item_name"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns the prices of all displayed products.
     */
    public List<Double> getProductPrices() {
        return driver.findElements(By.className("inventory_item_price"))
                .stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    /**
     * Adds a product to the cart by product name.
     */
    public void addToCart(String productName) {

        String productId = productName.toLowerCase()
                .replace(" ", "-");

        By addButton = By.id("add-to-cart-" + productId);

        WebElement button = wait.until(
                ExpectedConditions.presenceOfElementLocated(addButton));

        wait.until(ExpectedConditions.elementToBeClickable(button));

        button.click();
    }
    /**
     * Remove a product from the cart.
     */
    public void removeFromCart(String productName) {

        String productId = productName.toLowerCase()
                .replace(" ", "-");

        By removeButton = By.cssSelector(
                "button[data-test='remove-" + productId + "']");

        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();
    }

    /**
     * Checks whether the Remove button is displayed.
     */
    public boolean isRemoveButtonVisible(String productName) {
        By removeButton = By.xpath(
                "//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button[text()='Remove']");

        try {
            return wait.until(
                            ExpectedConditions.visibilityOfElementLocated(removeButton))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns the cart badge count.
     */
    public int getCartBadgeCount() {
        try {
            wait.until(ExpectedConditions.visibilityOf(cartBadge));
            return Integer.parseInt(cartBadge.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Opens the cart page.
     */
    public CartPage goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
        return new CartPage(driver);
    }

    /**
     * Selects an option from the sort dropdown.
     */
    public void sortByVisibleText(String option) {
        wait.until(ExpectedConditions.visibilityOf(sortDropdown));
        new Select(sortDropdown).selectByVisibleText(option);
    }

    /**
     * Returns the first displayed product price.
     */
    public double getFirstItemPrice() {
        By firstPrice = By.cssSelector(".inventory_item_price");

        String price = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(firstPrice))
                .getText()
                .replace("$", "");

        return Double.parseDouble(price);
    }

    /**
     * Returns the first displayed product name.
     */
    public String getFirstProductName() {
        return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.className("inventory_item_name")))
                .getText();
    }
}