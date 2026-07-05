package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import org.testng.annotations.Listeners;
import utils.TestListener;

@Listeners(TestListener.class)

public class ShoppingFlowTest extends BaseTest {

    @BeforeMethod
    public void loginAsStandardUser() {

        driver.get("https://www.saucedemo.com/");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(loginPage.isOnInventoryPage());
    }

    @Test
    public void shoppingFlow_completePurchase_shouldSucceed() {

        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutInfoPage infoPage = cartPage.checkout();

        infoPage.fillInformation("Test", "User", "12345");

        CheckoutOverviewPage overviewPage = infoPage.continueCheckout();

        CheckoutCompletePage completePage = overviewPage.finishCheckout();

        Assert.assertTrue(completePage.isLoaded());

        Assert.assertEquals(
                completePage.getHeaderText(),
                "Thank you for your order!"
        );
    }
}