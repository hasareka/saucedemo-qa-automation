package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;

public class LoginTest {

    WebDriver driver;
    LoginPage loginPage;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void launchApp() {
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
    }

    // ✅ standard_user
    @Test
    public void validLoginTest() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Assert.assertTrue(
                loginPage.isOnInventoryPage(),
                "Login failed for standard_user"
        );
    }

    // ✅ locked_out_user
    @Test
    public void lockedOutUserTest() {
        loginPage.enterUsername("locked_out_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(
                error.contains("locked out"),
                "Error message not displayed for locked_out_user"
        );
    }

    // ✅ performance_glitch_user
    @Test
    public void performanceGlitchUserTest() {
        loginPage.enterUsername("performance_glitch_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Assert.assertTrue(
                loginPage.isOnInventoryPage(),
                "Login failed for performance_glitch_user"
        );
    }

    // ✅ problem_user
    @Test
    public void problemUserTest() {
        loginPage.enterUsername("problem_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Assert.assertTrue(
                loginPage.isOnInventoryPage(),
                "Login failed for problem_user"
        );
    }

    // ✅ visual_user
    @Test
    public void visualUserTest() {
        loginPage.enterUsername("visual_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Assert.assertTrue(
                loginPage.isOnInventoryPage(),
                "Login failed for visual_user"
        );
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
