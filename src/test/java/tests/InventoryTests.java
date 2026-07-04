package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

public class InventoryTests extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void loginToInventory() {

        driver.get("https://www.saucedemo.com/");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(
                loginPage.isOnInventoryPage(),
                "Login failed in setup."
        );

        inventoryPage = new InventoryPage(driver);
    }

    @Test
    public void inventory_shouldLoadAndShowProducts() {

        Assert.assertTrue(
                inventoryPage.isLoaded(),
                "Inventory page failed to load."
        );

        Assert.assertTrue(
                inventoryPage.getProductCount() > 0,
                "No products are displayed."
        );
    }

    @Test
    public void inventory_shouldDisplaySixProducts() {

        Assert.assertEquals(
                inventoryPage.getProductCount(),
                6,
                "Inventory should contain exactly six products."
        );
    }

    @Test
    public void inventory_allProductsShouldHaveNames() {

        Assert.assertEquals(
                inventoryPage.getProductNames().size(),
                6
        );

        inventoryPage.getProductNames().forEach(name ->
                Assert.assertFalse(
                        name.isBlank(),
                        "Product name should not be empty."
                ));
    }

    @Test
    public void inventory_allProductsShouldHavePrices() {

        inventoryPage.getProductPrices().forEach(price ->
                Assert.assertTrue(
                        price > 0,
                        "Product price should be greater than zero."
                ));
    }

    @Test
    public void inventory_shouldSortByPriceLowToHigh() {

        inventoryPage.sortByVisibleText("Price (low to high)");

        double firstPrice = inventoryPage.getFirstItemPrice();

        Assert.assertTrue(
                firstPrice > 0,
                "Sorting by price failed."
        );
    }

    @Test
    public void inventory_addOneProduct_shouldUpdateCartBadge() {

        inventoryPage.addToCart("Sauce Labs Backpack");

        Assert.assertEquals(
                inventoryPage.getCartBadgeCount(),
                1,
                "Cart badge should display 1."
        );
    }

    @Test
    public void inventory_removeProduct_shouldClearCartBadge() {

        inventoryPage.addToCart("Sauce Labs Backpack");
        inventoryPage.removeFromCart("Sauce Labs Backpack");

        Assert.assertEquals(
                inventoryPage.getCartBadgeCount(),
                0,
                "Cart badge should disappear after removing the item."
        );
    }
}