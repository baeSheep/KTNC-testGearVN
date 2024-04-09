package testCase;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GearVNSearchTest2 {
    WebDriver driver;
    String baseUrl = "https://gearvn.com/";

    @BeforeTest
    public void setUp() {
        // Khởi tạo WebDriver
        System.setProperty("webdriver.chrome.driver", "F:\\FPT Polytechnic\\SPRING 2024\\BLOCK 2\\SOF304_Kiem thu nang cao\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Mở cửa sổ trình duyệt toàn màn hình
    }

    @Test
    public void searchProductTest() {
        // Truy cập trang web GearVN
        driver.get(baseUrl);

        // Nhập từ khóa tìm kiếm vào ô tìm kiếm
        WebElement searchInput = driver.findElement(By.id("inputSearchAuto"));
        searchInput.sendKeys("laptop Dell XPS 15" + Keys.ENTER);

        // Chờ cho kết quả tìm kiếm xuất hiện
        WebElement searchResults = driver.findElement(By.xpath("//div[@class='row product-list']"));
        Assert.assertTrue(searchResults.isDisplayed(), "Search results are not displayed.");

        // Kiểm tra số lượng sản phẩm trả về
        int numberOfProducts = driver.findElements(By.xpath("//div[@class='product-wrapper']")).size();
        Assert.assertTrue(numberOfProducts > 0, "No products found.");

        // Kiểm tra thông tin chi tiết của sản phẩm đầu tiên trong kết quả
        WebElement firstProduct = driver.findElement(By.xpath("//div[@class='product-wrapper'][1]"));
        String productName = firstProduct.findElement(By.className("product-name")).getText();
        String productPrice = firstProduct.findElement(By.className("product-price")).getText();
        System.out.println("First product name: " + productName);
        System.out.println("First product price: " + productPrice);

        // Click vào sản phẩm đầu tiên để xem thông tin chi tiết
        firstProduct.click();

        // Chờ trang chi tiết sản phẩm hiển thị
        WebElement productDetailPage = driver.findElement(By.xpath("//div[@class='product-details']"));
        Assert.assertTrue(productDetailPage.isDisplayed(), "Product detail page is not displayed.");

        // Kiểm tra thông tin chi tiết của sản phẩm trên trang chi tiết
        String productDetailName = driver.findElement(By.xpath("//h1")).getText();
        String productDetailPrice = driver.findElement(By.xpath("//div[@class='price']")).getText();
        System.out.println("Product detail name: " + productDetailName);
        System.out.println("Product detail price: " + productDetailPrice);

        // So sánh thông tin chi tiết của sản phẩm trên trang tìm kiếm và trang chi tiết
        Assert.assertEquals(productName, productDetailName, "Product name on search results does not match product name on detail page.");
        Assert.assertEquals(productPrice, productDetailPrice, "Product price on search results does not match product price on detail page.");
    }

    @AfterTest
    public void tearDown() {
        // Đóng trình duyệt sau khi hoàn thành
        if (driver != null) {
            driver.quit();
        }
    }
}
