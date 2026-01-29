package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTests extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void openLogin() {
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
    }

    @Test
    public void login_standardUser_shouldNavigateToInventory() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isOnInventoryPage(), "standard_user login failed");
    }

    @Test
    public void login_lockedOutUser_shouldShowLockedOutError() {
        loginPage.enterUsername("locked_out_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("locked out"),
                "Locked out error message not shown");
    }

    @Test
    public void login_performanceGlitchUser_shouldNavigateToInventory() {
        loginPage.enterUsername("performance_glitch_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isOnInventoryPage(), "performance_glitch_user login failed");
    }

    @Test
    public void login_problemUser_shouldNavigateToInventory() {
        loginPage.enterUsername("problem_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isOnInventoryPage(), "problem_user login failed");
    }

    @Test
    public void login_visualUser_shouldNavigateToInventory() {
        loginPage.enterUsername("visual_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isOnInventoryPage(), "visual_user login failed");
    }
}
