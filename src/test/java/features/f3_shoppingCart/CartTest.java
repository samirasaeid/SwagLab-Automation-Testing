package features.f3_shoppingCart;

import Config.Config;
import Mocks.MockUserServices;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.List;

public class CartTest {

    WebDriver driver;
    CartPage cartPage;

    @BeforeMethod
    public void setUp() throws FileNotFoundException {
        driver = new ChromeDriver();
        MockUserServices.mockLoginPage(driver);
        cartPage = new CartPage(driver);

        driver.get(Config.productURL);
    }

    @Test(priority = 1)
    public void testAddSingleProductToCart() {
        String singleProduct = cartPage.proceedAddToCart();
        cartPage.verifyProductsInCart(singleProduct);
        Assert.assertTrue(cartPage.getNumberOfProductAdded()==1);
    }

    @Test(priority = 2)
    public void testAddMultipleProductsToCart() {
        int number = 3;
        cartPage.verifyMultipleProductsInCart(cartPage.proceedMultipleAddsToCart(number));
        Assert.assertTrue(cartPage.getNumberOfProductAdded()==number);
    }
    @Test(priority = 3)
    public void testDisplayCart() {
        cartPage.displayCartItems();
        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Config.cartURL));
    }

    @Test (priority = 4)
    public void testRemoveProductFromCart() {
        cartPage.restoreAddedProductsToCart();
        cartPage.verifyRemoveFromCart(cartPage.removeProductsFromCart("Sauce Labs Fleece Jacket"));
        try{Thread.sleep(2000);}catch (InterruptedException e){}
    }

    @Test(priority = 5)
    public void testContinueShoppingCart() {
        cartPage.continueShopping();
        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Config.productURL));
    }



    @AfterMethod
    public void tearDown(){
        driver.quit();
    }



}
