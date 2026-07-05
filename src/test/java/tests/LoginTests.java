package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.TestListener;

@Listeners(TestListener.class)

public class LoginTests extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void openLogin() {
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
    }

    @Test
    public void login_standardUser_shouldNavigateToInventory() {

        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(
                loginPage.isOnInventoryPage(),
                "Standard user should be redirected to the Inventory page."
        );
    }

    @Test
    public void login_lockedOutUser_shouldShowLockedOutError() {

        loginPage.login("locked_out_user", "secret_sauce");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Sorry, this user has been locked out."
        );
    }

    @Test
    public void login_performanceGlitchUser_shouldNavigateToInventory() {

        loginPage.login("performance_glitch_user", "secret_sauce");

        Assert.assertTrue(
                loginPage.isOnInventoryPage(),
                "Performance glitch user should be redirected to the Inventory page."
        );
    }

    @Test
    public void login_problemUser_shouldNavigateToInventory() {

        loginPage.login("problem_user", "secret_sauce");

        Assert.assertTrue(
                loginPage.isOnInventoryPage(),
                "Problem user should be redirected to the Inventory page."
        );
    }

    @Test
    public void login_visualUser_shouldNavigateToInventory() {

        loginPage.login("visual_user", "secret_sauce");

        Assert.assertTrue(
                loginPage.isOnInventoryPage(),
                "Visual user should be redirected to the Inventory page."
        );
    }

    @Test
    public void login_invalidUsername_shouldShowErrorMessage() {

        loginPage.login("invalid_user", "secret_sauce");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service"
        );
    }

    @Test
    public void login_invalidPassword_shouldShowErrorMessage() {

        loginPage.login("standard_user", "wrong_password");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service"
        );
    }

    @Test
    public void login_emptyUsername_shouldShowUsernameRequiredMessage() {

        loginPage.login("", "secret_sauce");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username is required"
        );
    }

    @Test
    public void login_emptyPassword_shouldShowPasswordRequiredMessage() {

        loginPage.login("standard_user", "");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Password is required"
        );
    }

    @Test
    public void login_emptyUsernameAndPassword_shouldShowUsernameRequiredMessage() {

        loginPage.login("", "");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username is required"
        );
    }
}