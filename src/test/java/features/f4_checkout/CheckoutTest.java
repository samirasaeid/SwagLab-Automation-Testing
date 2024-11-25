package features.f4_checkout;

import Mocks.MockUserServices;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.Cookie;

import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import helpers.Transformer.JsonHelper;
import Config.Config;


public class CheckoutTest {
    WebDriver driver;
    CheckoutPage checkoutPage;

    @BeforeMethod
    public void setup() throws FileNotFoundException {
        driver = new ChromeDriver();
        MockUserServices.mockLoginPage(driver);
        driver.get(Config.checkoutURL);
        checkoutPage = new CheckoutPage(driver);
    }

    @DataProvider(name = "checkoutData")
    public Object[][] checkoutData() throws IOException {
        JsonArray dataArray = JsonParser.parseReader(new FileReader(Config.testDataPath+Config.FileNames.CHECKOUT_TEST_DATA.getFileName())).getAsJsonArray();
        Object[][] data = new Object[dataArray.size()][];

        for (int i = 0; i < dataArray.size(); i++) {
            String[] parsedData = JsonHelper.parseJSONCheckoutPage(Config.testDataPath+Config.FileNames.CHECKOUT_TEST_DATA.getFileName(), i);
            String expectedOutcome = dataArray.get(i).getAsJsonObject().get("expectedResult").getAsString();
            data[i] = new Object[]{parsedData[0], parsedData[1], parsedData[2], expectedOutcome};
        }
        return data;
    }
    @Test (dataProvider = "checkoutData")
    public void checkoutTest(String firstName, String secondName, String postalCode, String expectedOutcome) {
        checkoutPage.proceedCheckout(firstName, secondName, postalCode);
        if(driver.getCurrentUrl().equalsIgnoreCase(Config.successCheckoutURL)){
            System.out.println("Successful Checkout");
            checkoutPage.clickFinishButton();
            try{Thread.sleep(3000);}catch (InterruptedException e){}
            if(driver.getCurrentUrl().equalsIgnoreCase(Config.completeCheckout)){checkoutPage.clickBackHome();}
            try{Thread.sleep(3000);}catch (InterruptedException e){}
        }else{
            String errMsg = checkoutPage.getErrorMessage();
            System.out.println(checkoutPage.getErrorMessage());
            Assert.assertTrue(errMsg.equals(expectedOutcome));
        }
        //driver.navigate().refresh();
    }

    @Test (priority = 1)
    public void testSuccessCheckout() throws FileNotFoundException {
        String[] testData = JsonHelper.parseJSONCheckoutPage(Config.testDataPath+Config.FileNames.CHECKOUT_TEST_DATA.getFileName(),7);
        checkoutPage.proceedCheckout(testData[0], testData[1], testData[2]);
        if(driver.getCurrentUrl().equalsIgnoreCase(Config.successCheckoutURL)) {
            checkoutPage.clickFinishButton();
            try{Thread.sleep(3000);}catch (InterruptedException e){}
            if(driver.getCurrentUrl().equalsIgnoreCase(Config.completeCheckout)) {checkoutPage.clickBackHome();}
            try{Thread.sleep(3000);}catch (InterruptedException e){}
        }

    }

    @Test (priority = 2)
    public void testEmptyFirstName() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONCheckoutPage(Config.testDataPath+Config.FileNames.CHECKOUT_TEST_DATA.getFileName(),0);
        checkoutPage.proceedCheckout(testData[0], testData[1], testData[2]);
        assertTrue(checkoutPage.getErrorMessage().equals(testData[3]));
    }

    @Test (priority = 3)
    public void testEmptyLastName() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONCheckoutPage(Config.testDataPath+Config.FileNames.CHECKOUT_TEST_DATA.getFileName(),1);
        checkoutPage.proceedCheckout(testData[0], testData[1], testData[2]);
        assertTrue(checkoutPage.getErrorMessage().equals(testData[3]));
    }
    @Test (priority = 4)
    public void testEmptyPostalCode() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONCheckoutPage(Config.testDataPath+Config.FileNames.CHECKOUT_TEST_DATA.getFileName(),2);
        checkoutPage.proceedCheckout(testData[0], testData[1], testData[2]);
        assertTrue(checkoutPage.getErrorMessage().equals(testData[3]));
    }
   @Test (priority = 5)
   public void testEmptyFirstnameAndLastName() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONCheckoutPage(Config.testDataPath+Config.FileNames.CHECKOUT_TEST_DATA.getFileName(),3);
        checkoutPage.proceedCheckout(testData[0], testData[1], testData[2]);
        assertTrue(checkoutPage.getErrorMessage().equals(testData[3]));
   }
   @Test (priority = 6)
   public void testEmptyFirstnameAndPostalCode() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONCheckoutPage(Config.testDataPath+Config.FileNames.CHECKOUT_TEST_DATA.getFileName(),4);
        checkoutPage.proceedCheckout(testData[0], testData[1], testData[2]);
        assertTrue(checkoutPage.getErrorMessage().equals(testData[3]));
   }
   @Test (priority = 7)
   public void testEmptyLastNameAndPostalCode() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONCheckoutPage(Config.testDataPath+Config.FileNames.CHECKOUT_TEST_DATA.getFileName(),5);
        checkoutPage.proceedCheckout(testData[0], testData[1], testData[2]);
        assertTrue(checkoutPage.getErrorMessage().equals(testData[3]));
   }
   @Test (priority = 8)
   public void testEmptyFirstnameLastNameAndPostalCode() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONCheckoutPage(Config.testDataPath+Config.FileNames.CHECKOUT_TEST_DATA.getFileName(),6);
        checkoutPage.proceedCheckout(testData[0], testData[1], testData[2]);
        assertTrue(checkoutPage.getErrorMessage().equals(testData[3]));
   }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }






}
