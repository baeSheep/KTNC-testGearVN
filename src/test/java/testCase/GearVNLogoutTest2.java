package testCase;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import dataProvider.LogoutDataProvider;

import java.util.concurrent.TimeUnit;

public class GearVNLogoutTest2 {
    public String baseUrl = "https://gearvn.com/";
    String driverPath = "F:\\FPT Polytechnic\\SPRING 2024\\BLOCK 2\\SOF304_Kiem thu nang cao\\chromedriver-win64\\chromedriver.exe";
    public WebDriver driver;

    @BeforeTest
    public void launchBrowser() {
        System.out.println("Launching chrome browser");
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        driver.get(baseUrl);
    }

    @Test(dataProvider = "logoutData", dataProviderClass = LogoutDataProvider.class)
    public void GearVNLogout(String email, String password, String expectedResult) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement loginElement = driver.findElement(By.xpath("/html/body/div[1]/header/div/div/div/div[2]/div[6]/div[2]/div/div[1]/div[2]/button[1]"));
        js.executeScript("arguments[0].click();", loginElement);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement emailInput = driver.findElement(By.id("login-customer[email]"));
        emailInput.clear();
        emailInput.sendKeys(email);
        WebElement passwordInput = driver.findElement(By.id("login-customer[password]"));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        WebElement loginBtn = driver.findElement(By.xpath("/html/body/div[1]/div[11]/div/div/div[2]/div/div/div[1]/form/div[4]/button"));
        loginBtn.click();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Đăng xuất
        WebElement logout = driver.findElement(By.xpath("/html/body/div[1]/header/div/div/div/div[2]/div[6]/div[2]/div/div[3]/ul/li/a/div[2]/div"));
        js.executeScript("arguments[0].click();", logout);
        WebElement agreeBtn = driver.findElement(By.xpath("/html/body/div[4]/div/div[6]/button[1]"));
        js.executeScript("arguments[0].click();", agreeBtn);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Kiểm tra có truy cập được vào tra cứu đơn hàng sau khi đăng xuất không
        boolean canAccessOrderTrackingAfterLogout = true;
        try {
            WebElement orderTrackingLink = driver.findElement(By.xpath("//a[contains(@class,'header-action_text')]"));
            if (orderTrackingLink.getText().equals("Tra cứu đơn hàng")) {
                canAccessOrderTrackingAfterLogout = false;
            }
        } catch (NoSuchElementException e) {
            System.out.println("Cannot access order tracking after logout.");
        }

        // Kiểm tra kết quả dự kiến với Assert
        if (Boolean.parseBoolean(expectedResult)) {
            Assert.assertFalse(canAccessOrderTrackingAfterLogout, "Cannot access order tracking after logout.");
        } else {
            Assert.assertTrue(canAccessOrderTrackingAfterLogout, "Accessing order tracking after logout unexpectedly.");
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
