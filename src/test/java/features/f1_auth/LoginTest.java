package features.f1_auth;

import Mocks.MockUserServices;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import static org.testng.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

import helpers.Transformer.JsonHelper;
import Config.Config;

import javax.imageio.ImageIO;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() throws FileNotFoundException {
        driver = new ChromeDriver();
        driver.get(Config.baseURL);
        loginPage = new LoginPage(driver);

    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws IOException {
        JsonArray dataArray = JsonParser.parseReader(new FileReader(Config.testDataPath+Config.FileNames.LOGIN_TEST_DATA.getFileName())).getAsJsonArray();
        Object[][] data = new Object[dataArray.size()][];

        for (int i = 0; i < dataArray.size(); i++) {
            String[] parsedData = JsonHelper.parseJSONLoginPage(Config.testDataPath+Config.FileNames.LOGIN_TEST_DATA.getFileName(), i);
            String expectedOutcome = dataArray.get(i).getAsJsonObject().get("expectedResult").getAsString();
            data[i] = new Object[]{parsedData[0], parsedData[1], parsedData[2]};
        }
        return data;
    }

    @Test(dataProvider="loginData")
    public void testLogin(String userName, String password, String expectedOutcome){
        loginPage.proceedLogin(userName, password);
        if(driver.getCurrentUrl().equalsIgnoreCase(Config.productURL)){
            boolean isImageFound = loginPage.isImageTagPresent(driver, Config.problematicImag);
            if(isImageFound)
                assertTrue(isImageFound,"Problematic User");
            else{
                System.out.println("Successful Login");
            }
        }else{
            String errMsg = loginPage.getErrorMessage();
            System.out.println(loginPage.getErrorMessage());
            Assert.assertTrue(errMsg.equals(expectedOutcome));
        }
    }

    @Test (priority = 1)
    public void testSuccessLogin() throws FileNotFoundException {
        String[] testData = JsonHelper.parseJSONLoginPage(Config.testDataPath+Config.FileNames.LOGIN_TEST_DATA.getFileName(),0);
        loginPage.proceedLogin(testData[0], testData[1]);
        if(driver.getCurrentUrl().equalsIgnoreCase(Config.productURL)) {
            System.out.println(loginPage.getCartXPosition());
            System.out.println(loginPage.getCartYPosition());
            System.out.println("Successful Login");
        }
    }

    @Test (priority = 2)
    public void testLockedUser() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONLoginPage(Config.testDataPath+Config.FileNames.LOGIN_TEST_DATA.getFileName(),1);
        loginPage.proceedLogin(testData[0], testData[1]);
        assertTrue(loginPage.getErrorMessage().equals(testData[2]));
    }

    @Test (priority = 3)
    public void testProblematicUser() throws IOException {
        String[] testData = JsonHelper.parseJSONLoginPage(Config.testDataPath+Config.FileNames.LOGIN_TEST_DATA.getFileName(),2);
        loginPage.proceedLogin(testData[0], testData[1]);
        if(driver.getCurrentUrl().equalsIgnoreCase(Config.productURL)) {
            boolean isImageFound = loginPage.isImageTagPresent(driver, Config.problematicImag);
            assertTrue(isImageFound);
            System.out.println("Problematic User");
        }
    }

    @Test (priority = 4)
    public void testPerformanceGlitchUser() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONLoginPage(Config.testDataPath+Config.FileNames.LOGIN_TEST_DATA.getFileName(),3);
        long StartTime = System.currentTimeMillis();
        loginPage.proceedLogin(testData[0], testData[1]);
        long EndTime = System.currentTimeMillis() - StartTime;
        if(driver.getCurrentUrl().equalsIgnoreCase(Config.productURL)) {
            if(EndTime >= loginPage.preformanceTime ){
                System.out.println("Performance Glitch User");
                System.out.println("LoginResponse Time:"+EndTime);
            }
        }
    }

    @Test (priority = 5)
    public void testErrorUser() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONLoginPage(Config.testDataPath+Config.FileNames.LOGIN_TEST_DATA.getFileName(),4);
        loginPage.proceedLogin(testData[0], testData[1]);
        if(driver.getCurrentUrl().equalsIgnoreCase(Config.productURL)) {
            System.out.println("Error User");
        }
    }

    @Test (priority = 6)
    public void testVisualUser() throws FileNotFoundException{

        String[] testData = JsonHelper.parseJSONLoginPage(Config.testDataPath+Config.FileNames.LOGIN_TEST_DATA.getFileName(),5);
        try{Thread.sleep(3000);}catch (InterruptedException e){}
        loginPage.proceedLogin(testData[0], testData[1]);
        try{Thread.sleep(3000);}catch (InterruptedException e){}
        if(driver.getCurrentUrl().equalsIgnoreCase(Config.productURL)) {
            boolean isImageFound = loginPage.isImageTagPresent(driver, Config.problematicImag);
            System.out.println(loginPage.getCartXPosition());
            System.out.println(loginPage.getCartYPosition());
            boolean locationFlag = (loginPage.xLocation != loginPage.getCartXPosition() || loginPage.yLocation != loginPage.getCartYPosition()) ? true : false;
            assertTrue(isImageFound && locationFlag);
            System.out.println("visual User");
        }

    }

    @Test (priority = 7)
    public void testEmptyUserName() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONLoginPage(Config.testDataPath+Config.FileNames.LOGIN_TEST_DATA.getFileName(),6);
        loginPage.proceedLogin(testData[0], testData[1]);
        assertTrue(loginPage.getErrorMessage().equals(testData[2]));
    }

    @Test (priority = 8)
    public void testEmptyPassword() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONLoginPage(Config.testDataPath+Config.FileNames.LOGIN_TEST_DATA.getFileName(),7);
        loginPage.proceedLogin(testData[0], testData[1]);
        assertTrue(loginPage.getErrorMessage().equals(testData[2]));
    }

    @Test (priority = 9)
    public void testEmptyUserNameAndPassword() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONLoginPage(Config.testDataPath+Config.FileNames.LOGIN_TEST_DATA.getFileName(),8);
        loginPage.proceedLogin(testData[0], testData[1]);
        assertTrue(loginPage.getErrorMessage().equals(testData[2]));
    }

    @Test (priority = 10)
    public void testLogout() throws FileNotFoundException{
        MockUserServices.mockLoginPage(driver);
        driver.get(Config.productURL);
        try{Thread.sleep(3000);}catch (InterruptedException e){}
        loginPage.proceedLogout();
        assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Config.baseURL));
    }

    @Test (priority = 11)
    public void testInvalidUsername() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONLoginPage(Config.testDataPath+Config.FileNames.LOGIN_TEST_DATA.getFileName(),9);
        loginPage.proceedLogin(testData[0], testData[1]);
        assertTrue(loginPage.getErrorMessage().equals(testData[2]));
    }

    @Test (priority = 12)
    public void testInvalidPassword() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONLoginPage(Config.testDataPath+Config.FileNames.LOGIN_TEST_DATA.getFileName(),10);
        loginPage.proceedLogin(testData[0], testData[1]);
        assertTrue(loginPage.getErrorMessage().equals(testData[2]));
    }

    @Test (priority = 13)
    public void testInvalidUsernameAndPassword() throws FileNotFoundException{
        String[] testData = JsonHelper.parseJSONLoginPage(Config.testDataPath+Config.FileNames.LOGIN_TEST_DATA.getFileName(),11);
        loginPage.proceedLogin(testData[0], testData[1]);
        assertTrue(loginPage.getErrorMessage().equals(testData[2]));
    }


    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

}
