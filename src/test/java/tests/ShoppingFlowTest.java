package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;

public class ShoppingFlowTest {

    WebDriver driver;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void loginAsStandardUser() {
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isOnInventoryPage(), "Login failed in setup");
    }

    @Test
    public void TC006_verifyInventoryPageLoads() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page did not load");
        Assert.assertTrue(inventoryPage.getProductCount() > 0, "No products visible");
    }

    @Test
    public void TC007_addSingleItemAndVerifyBadge() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addToCart("Sauce Labs Backpack");
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 1, "Cart badge did not update to 1");
        Assert.assertTrue(inventoryPage.isRemoveButtonVisible("Sauce Labs Backpack"), "Remove button not visible after add");
    }

    @Test
    public void TC010_goToCartAndVerifyItemPresent() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addToCart("Sauce Labs Backpack");
        CartPage cartPage = inventoryPage.goToCart();
        Assert.assertTrue(cartPage.isLoaded(), "Cart page not loaded");
        Assert.assertTrue(cartPage.isItemPresent("Sauce Labs Backpack"), "Item not present in cart");
    }

    @Test
    public void TC013_completeCheckoutHappyPath() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();
        CheckoutInfoPage infoPage = cartPage.checkout();
        Assert.assertTrue(infoPage.isLoaded(), "Checkout Info page not loaded");

        infoPage.fillInfo("Test", "User", "12345");
        CheckoutOverviewPage overviewPage = infoPage.clickContinue();
        Assert.assertTrue(overviewPage.isLoaded(), "Checkout Overview page not loaded");

        CheckoutCompletePage completePage = overviewPage.finish();
        Assert.assertTrue(completePage.isLoaded(), "Checkout Complete page not loaded");
        Assert.assertTrue(completePage.getHeaderText().toLowerCase().contains("thank you"), "Success message not shown");
    }

    @Test
    public void TC014_checkoutValidationMissingFirstName() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();
        CheckoutInfoPage infoPage = cartPage.checkout();

        infoPage.fillInfo("", "User", "12345");
        infoPage.clickContinue(); // stays on same page if error

        Assert.assertTrue(infoPage.getErrorMessage().toLowerCase().contains("first name"),
                "Validation message not shown for missing first name");
    }

    @Test
    public void TC015_sortByPriceLowToHigh() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.sortByVisibleText("Price (low to high)");

        double firstPrice = inventoryPage.getFirstItemPrice();
        Assert.assertTrue(firstPrice <= 10.00, "Sorting may not be applied (first item price not low)");
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
