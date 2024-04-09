package testCase;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GearVNResgisterTest {

    public String baseUrl = "https://gearvn.com/";
    String driverPath = "F:\\FPT Polytechnic\\SPRING 2024\\BLOCK 2\\SOF304_Kiem thu nang cao\\chromedriver-win64\\chromedriver.exe"; // doi path chrome-driver theo may
    public WebDriver driver;

    @BeforeTest
    public void launchBrowser() {
        System.out.println("launch chrome browser");
        driver = new ChromeDriver();
        driver.get(baseUrl);
    }

    @Test(dataProvider = "registrationData", dataProviderClass = dataProvider.RegistrationDataProvider.class)
    public void GearVNResgister(String name, String email, String password, String expectedResult) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement registerElement = driver.findElement(By.xpath("/html/body/div[1]/header/div/div/div/div[2]/div[6]/div[2]/div/div[1]/div[2]/button[2]"));
        js.executeScript("arguments[0].click();", registerElement);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            // Handle exception
        }
        WebElement nameInput = driver.findElement(By.id("register-customer[name]"));
        nameInput.clear();
        nameInput.sendKeys(name);
        WebElement emailInput = driver.findElement(By.id("register-customer[email]"));
        emailInput.clear();
        emailInput.sendKeys(email);
        WebElement passwordInput = driver.findElement(By.id("register-customer[password]"));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        WebElement checkBox = driver.findElement(By.xpath("/html/body/div[1]/div[11]/div/div/div[2]/div/div/div[2]/form/div[4]/div/input"));
        js.executeScript("arguments[0].click();", checkBox);
        WebElement registerBtn = driver.findElement(By.xpath("/html/body/div[1]/div[11]/div/div/div[2]/div/div/div[2]/form/div[5]/button"));
        js.executeScript("arguments[0].click();", registerBtn);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            // Handle exception
        }
        WebElement logout = driver.findElement(By.xpath("/html/body/div[1]/header/div/div/div/div[2]/div[6]/div[2]/div/div[3]/ul/li/a/div[2]/div"));
        js.executeScript("arguments[0].click();", logout);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            // Handle exception
        }
        WebElement agreeBtn = driver.findElement(By.xpath("/html/body/div[4]/div/div[6]/button[1]"));
        js.executeScript("arguments[0].click();", agreeBtn);
        WebElement home = driver.findElement(By.xpath("/html/body/div[1]/header/div/div/div/div[1]/div[2]/a/picture/img"));
        js.executeScript("arguments[0].click();", home);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}