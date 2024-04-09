package testCase;

import java.util.concurrent.TimeUnit;
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

public class GearVNResgisterTest2 {

    public String baseUrl = "https://gearvn.com/";
    String driverPath = "F:\\FPT Polytechnic\\SPRING 2024\\BLOCK 2\\SOF304_Kiem thu nang cao\\chromedriver-win64\\chromedriver.exe";
    public WebDriver driver;

    @BeforeTest
    public void launchBrowser() {
        System.out.println("Launching chrome browser");
        driver = new ChromeDriver();
        driver.get(baseUrl);
    }

    @Test(dataProvider = "registrationData", dataProviderClass = dataProvider.RegistrationDataProvider.class)
    public void GearVNResgister(String name, String email, String password, String expectedResult) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        // Click on the register button
        WebElement registerElement = driver.findElement(By.xpath("/html/body/div[1]/header/div/div/div/div[2]/div[6]/div[2]/div/div[1]/div[2]/button[2]"));
        js.executeScript("arguments[0].click();", registerElement);
        
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Fill in registration form
        WebElement nameInput = driver.findElement(By.id("register-customer[name]"));
        nameInput.clear();
        nameInput.sendKeys(name);
        
        WebElement emailInput = driver.findElement(By.id("register-customer[email]"));
        emailInput.clear();
        emailInput.sendKeys(email);
        
        WebElement passwordInput = driver.findElement(By.id("register-customer[password]"));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        
        WebElement checkBox = driver.findElement(By.xpath("//input[@name='register-customer[accept_terms]']"));
        js.executeScript("arguments[0].click();", checkBox);
        
        // Click on the register button
        WebElement registerBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        js.executeScript("arguments[0].click();", registerBtn);
        
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check for successful registration
        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(currentUrl, baseUrl, "Registration failed. Not redirected to expected page.");

        // Check if order tracking is accessible after registration
        boolean canAccessOrderTracking = isElementPresent("//a[contains(text(),'Tra cứu đơn hàng')]");
        boolean expectedAccess = Boolean.parseBoolean(expectedResult);

        Assert.assertEquals(canAccessOrderTracking, expectedAccess, "Accessing order tracking after registration doesn't match expected result.");

        // Logout
        WebElement logout = driver.findElement(By.xpath("//a[contains(@class,'header-action_text')]"));
        js.executeScript("arguments[0].click();", logout);
        
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        WebElement agreeBtn = driver.findElement(By.xpath("//button[contains(text(),'Đồng ý')]"));
        js.executeScript("arguments[0].click();", agreeBtn);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
    
    // Method to check if element is present
    private boolean isElementPresent(String xpath) {
        try {
            driver.findElement(By.xpath(xpath));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
