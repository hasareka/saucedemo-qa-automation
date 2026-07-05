package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import org.testng.annotations.Listeners;
import utils.TestListener;

@Listeners(TestListener.class)

public class CartTests extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void loginToInventory() {

        driver.get("https://www.saucedemo.com/");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(loginPage.isOnInventoryPage());

        inventoryPage = new InventoryPage(driver);
    }

    @Test
    public void cart_shouldShowAddedItem() {

        inventoryPage.addToCart("Sauce Labs Backpack");

        Assert.assertEquals(
                inventoryPage.getCartBadgeCount(),
                1
        );

        CartPage cartPage = inventoryPage.goToCart();

        Assert.assertTrue(cartPage.isLoaded());
        Assert.assertTrue(cartPage.isItemPresent("Sauce Labs Backpack"));
    }

    @Test
    public void cart_shouldRemoveItem() {

        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();

        cartPage.removeItem("Sauce Labs Backpack");

        Assert.assertFalse(
                cartPage.isItemPresent("Sauce Labs Backpack")
        );
    }

    @Test
    public void cart_shouldDisplayCorrectItemCount() {

        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();

        Assert.assertEquals(
                cartPage.getCartItemCount(),
                1
        );
    }

    @Test
    public void cart_shouldDisplayCorrectProductName() {

        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();

        Assert.assertEquals(
                cartPage.getFirstItemName(),
                "Sauce Labs Backpack"
        );
    }

    @Test
    public void cart_shouldDisplayCorrectPrice() {

        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();

        Assert.assertEquals(
                cartPage.getFirstItemPrice(),
                "$29.99"
        );
    }

    @Test
    public void cart_continueShopping_shouldNavigateToInventory() {

        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();

        InventoryPage inventory = cartPage.continueShopping();

        Assert.assertTrue(
                inventory.isLoaded()
        );
    }
}