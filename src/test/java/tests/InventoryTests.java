package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;


public class InventoryTests extends BaseTest {

    @BeforeMethod
    public void loginToInventory() throws InterruptedException {
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isOnInventoryPage(), "Login failed in setup");
    }

    @Test
    public void inventory_shouldLoadAndShowProducts() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page not loaded");
        Assert.assertTrue(inventoryPage.getProductCount() > 0, "No products visible");
    }

    @Test
    public void inventory_shouldSortByPriceLowToHigh() throws InterruptedException {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.sortByVisibleText("Price (low to high)");
        double firstPrice = inventoryPage.getFirstItemPrice();
        Assert.assertTrue(firstPrice > 0, "First item price is invalid after sorting");

    }
}
