package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import org.testng.annotations.Listeners;
import utils.TestListener;

@Listeners(TestListener.class)

public class CheckoutTests extends BaseTest {

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
    public void checkout_shouldCompleteSuccessfully() {

        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutInfoPage infoPage = cartPage.checkout();

        Assert.assertTrue(infoPage.isLoaded());

        infoPage.fillInformation("Test", "User", "12345");

        CheckoutOverviewPage overviewPage = infoPage.continueCheckout();

        Assert.assertTrue(overviewPage.isLoaded());

        CheckoutCompletePage completePage = overviewPage.finishCheckout();

        Assert.assertTrue(completePage.isLoaded());

        Assert.assertEquals(
                completePage.getHeaderText(),
                "Thank you for your order!"
        );
    }

    @Test
    public void checkout_shouldShowErrorWhenFirstNameMissing() {

        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutInfoPage infoPage = cartPage.checkout();

        infoPage.fillInformation("", "User", "12345");

        infoPage.continueCheckout();

        Assert.assertEquals(
                infoPage.getErrorMessage(),
                "Error: First Name is required"
        );
    }

    @Test
    public void checkout_shouldShowErrorWhenLastNameMissing() {

        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutInfoPage infoPage = cartPage.checkout();

        infoPage.fillInformation("Test", "", "12345");

        infoPage.continueCheckout();

        Assert.assertEquals(
                infoPage.getErrorMessage(),
                "Error: Last Name is required"
        );
    }

    @Test
    public void checkout_shouldShowErrorWhenPostalCodeMissing() {

        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutInfoPage infoPage = cartPage.checkout();

        infoPage.fillInformation("Test", "User", "");

        infoPage.continueCheckout();

        Assert.assertEquals(
                infoPage.getErrorMessage(),
                "Error: Postal Code is required"
        );
    }

    @Test
    public void checkout_cancelFromInformationPage_shouldReturnToCart() {

        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutInfoPage infoPage = cartPage.checkout();

        CartPage returnedCartPage = infoPage.cancelCheckout();

        Assert.assertTrue(
                returnedCartPage.isLoaded(),
                "Cart page should be displayed after cancelling checkout."
        );
    }

    @Test
    public void checkout_shouldDisplayOrderTotal() {

        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutInfoPage infoPage = cartPage.checkout();

        infoPage.fillInformation("Test", "User", "12345");

        CheckoutOverviewPage overviewPage = infoPage.continueCheckout();

        Assert.assertTrue(
                overviewPage.getTotalText().contains("Total")
        );
    }

    @Test
    public void checkout_backToProducts_shouldNavigateToInventory() {

        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutInfoPage infoPage = cartPage.checkout();

        infoPage.fillInformation("Test", "User", "12345");

        CheckoutOverviewPage overviewPage = infoPage.continueCheckout();

        CheckoutCompletePage completePage = overviewPage.finishCheckout();

        InventoryPage inventory = completePage.backToProducts();

        Assert.assertTrue(
                inventory.isLoaded()
        );
    }
}