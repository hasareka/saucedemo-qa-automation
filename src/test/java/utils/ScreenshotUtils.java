package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtils {

    public static String captureScreenshot(WebDriver driver, String testName) {

        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Absolute path to save the image
        String destination = System.getProperty("user.dir")
                + "/test-output/screenshots/"
                + testName
                + ".png";

        File target = new File(destination);
        target.getParentFile().mkdirs();

        try {
            Files.copy(source.toPath(), target.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return relative path for Extent Report
        return "./screenshots/" + testName + ".png";
    }
}