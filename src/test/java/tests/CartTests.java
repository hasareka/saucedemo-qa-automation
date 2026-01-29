package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

public class CartTests extends BaseTest {

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
    public void cart_shouldShowAddedItem() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addToCart("Sauce Labs Backpack");
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 1, "Cart badge not 1");

        CartPage cartPage = inventoryPage.goToCart();
        Assert.assertTrue(cartPage.isLoaded(), "Cart page not loaded");
        Assert.assertTrue(cartPage.isItemPresent("Sauce Labs Backpack"), "Item not present in cart");
    }


    @Test
    public void cart_shouldRemoveItem() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();
        Assert.assertTrue(cartPage.isItemPresent("Sauce Labs Backpack"), "Item not present before remove");

        cartPage.removeItem("Sauce Labs Backpack");
        Assert.assertFalse(cartPage.isItemPresent("Sauce Labs Backpack"), "Item still present after remove");
    }
}
