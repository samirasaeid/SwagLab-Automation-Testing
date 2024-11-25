package features.f1_product;

import Config.Config;
import Mocks.MockUserServices;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import helpers.Transformer.JsonHelper;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class ProductTest {
    WebDriver driver;
    ProuctPage prouctPage;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() throws Exception {
        driver = new ChromeDriver();
        MockUserServices.mockLoginPage(driver);
        driver.get(Config.productURL);
        prouctPage = new ProuctPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

   @DataProvider (name="checkProductDetails")
   public Object[][] getProductDetails() throws FileNotFoundException {
       JsonArray dataArray = JsonParser.parseReader(new FileReader(Config.testDataPath+Config.FileNames.PRODUCT_TEST_DATA.getFileName())).getAsJsonArray();
       Object[][] data = new Object[dataArray.size()][];

       System.out.println("Number of products in JSON: " + dataArray.size());

       for (int i = 0; i < dataArray.size(); i++) {
           String[] parsedData = JsonHelper.parseJSONProductPage(Config.testDataPath+Config.FileNames.PRODUCT_TEST_DATA.getFileName(), i);
           data[i] = new Object[]{parsedData[0], parsedData[1], parsedData[2], parsedData[3]};
       }
       return data;
   }

   @Test (dataProvider = "checkProductDetails")
   public void verifyAllProductDetails(String productName, String productDescription, String productImage, String productPrice) {
       try{Thread.sleep(3000);}catch (InterruptedException e){}
        List<Product> websiteProducts = prouctPage.getProducts();
       try{Thread.sleep(3000);}catch (InterruptedException e){}

       boolean productFound = false;
       for (Product product : websiteProducts) {
           System.out.println("Expected Product Name: " + productName);
           System.out.println("Actual Product Name: " + product.getName());
           System.out.println("Expected Product Description: " + productDescription);
           System.out.println("Actual Product Description: " + product.getDescription());
           System.out.println("Expected Image Path: " + productImage);
           System.out.println("Actual Image Path: " + product.getImage());
           if(product.getName().equals(productName)) {
               productFound = true;
               Assert.assertTrue(product.getDescription().contains(productDescription));
               Assert.assertTrue(product.getImage().contains(productImage));
               Assert.assertTrue(product.getPrice().contains(productPrice));
               break;
           }
       }
       assertTrue(productFound);
   }

   @Test (priority = 1)
   public void testSortingProductNamesAZ(){
       try{Thread.sleep(3000);}catch (InterruptedException e){}
        prouctPage.sortProductNamesAZ();
       try{Thread.sleep(3000);}catch (InterruptedException e){}
        boolean isSorted = prouctPage.areProductsSortedAZ();
        assertTrue(isSorted);
   }

   @Test (priority = 2)
   public void testSortingProductNamesZA(){
        try{Thread.sleep(3000);}catch (InterruptedException e){}
        prouctPage.sortProductNamesZA();
        try{Thread.sleep(3000);}catch (InterruptedException e){}
        boolean isSorted = prouctPage.areProductsSortedZA();
        assertTrue(isSorted);
   }

   @Test (priority = 3)
   public void testSortingProductPriceLowToHigh(){
        try{Thread.sleep(3000);}catch (InterruptedException e){}
        prouctPage.sortProductPriceLowToHigh();
        try{Thread.sleep(3000);}catch (InterruptedException e){}
        boolean isSorted = prouctPage.areProductsSortedPriceLowToHigh();
        assertTrue(isSorted);
   }

   @Test (priority = 4)
   public void testSortingProductPriceHighToLow(){
        try{Thread.sleep(3000);}catch (InterruptedException e){}
        prouctPage.sortProductPriceHighToLow();
        try{Thread.sleep(3000);}catch (InterruptedException e){}
        boolean isSorted = prouctPage.areProductsSortedPriceHighToLow();
        assertTrue(isSorted);
   }

   @Test (priority = 5)
   public void testAddToCartAllProducts(){
       try{Thread.sleep(1000);}catch (InterruptedException e){}
       prouctPage.addToCartAllProducts();
       try{Thread.sleep(1000);}catch (InterruptedException e){}
   }

   @Test (priority = 6)
   public void testRemoveFromCartAllProducts(){
        prouctPage.removeFromCartAllProducts();
   }

   @Test (priority = 7)
   public void testAboutButton(){
        try{Thread.sleep(1000);}catch (InterruptedException e){}
        prouctPage.selectAboutButton();
        try{Thread.sleep(1000);}catch (InterruptedException e){}
        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Config.aboutURL));
    }

    @Test (priority = 8)
    public void testAboutEndButton(){
        try{Thread.sleep(1000);}catch (InterruptedException e){}
        prouctPage.openReactMeanu();
        try{Thread.sleep(1000);}catch (InterruptedException e){}
        prouctPage.closeReactMenu();
    }

    @Test (priority = 9)
    public void testTwitterPage(){
        try{Thread.sleep(1000);}catch (InterruptedException e){}
        prouctPage.clickTwitterIcon();
        try{Thread.sleep(1000);}catch (InterruptedException e){}

        String originalTab = driver.getWindowHandle();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for(String windowHandle : driver.getWindowHandles()){
            if(!windowHandle.equals(originalTab)){
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Config.twitterURL));

    }

    @Test (priority = 10)
    public void testFacebookPage(){
        try{Thread.sleep(1000);}catch (InterruptedException e){}
        prouctPage.clickFacebookIcon();
        try{Thread.sleep(1000);}catch (InterruptedException e){}

        String originalTab = driver.getWindowHandle();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for(String windowHandle : driver.getWindowHandles()){
            if(!windowHandle.equals(originalTab)){
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Config.facebookURL));
    }

    @Test (priority = 11)
    public void testLinkedinPage(){
        try{Thread.sleep(1000);}catch (InterruptedException e){}
        prouctPage.clickLinkedinIcon();
        try{Thread.sleep(1000);}catch (InterruptedException e){}

        String originalTab = driver.getWindowHandle();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for(String windowHandle : driver.getWindowHandles()){
            if(!windowHandle.equals(originalTab)){
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Config.linkedinURL));
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }



}
