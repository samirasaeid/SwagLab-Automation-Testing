package features.f1_auth;

import Config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class LoginPage {
    WebDriver driver;
    long preformanceTime = 1000;
    public int  xLocation = 959;
    public int  yLocation = 10;

    By userNameField = By.id("user-name");
    By passwordField = By.id("password");
    By loginButton = By.id("login-button");
    By errorMessage = By.xpath("//h3[@data-test='error']");
    By imageTagName = By.tagName("img");
    By logoutDropMenu = By.id("react-burger-menu-btn");
    By logoutButton = By.id("logout_sidebar_link");

    By shoppingCart = By.xpath("//a[@class='shopping_cart_link']");


    public LoginPage(WebDriver driver) {this.driver = driver;}

    public void enterUserName(String userName) {driver.findElement(userNameField).sendKeys(userName);}
    public void enterPassword(String password) {driver.findElement(passwordField).sendKeys(password);}
    public void clickLoginButton() {driver.findElement(loginButton).click();}
    public String getErrorMessage(){ return driver.findElement(errorMessage).getText();}

    public WebElement getCartIcon() {return driver.findElement(shoppingCart);}
    public void proceedLogin(String userName, String password) {
        enterUserName(userName);
        enterPassword(password);
        clickLoginButton();
    }

    public List<WebElement> getImageTag() { return driver.findElements(imageTagName);}
    public boolean isImageTagPresent(WebDriver driver, String imageUrl) {
        try{
            for(WebElement image : getImageTag()){
                String src = image.getAttribute("src");
                if(src.equals(imageUrl)){
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void clickLogoutDropMenu() {driver.findElement(logoutDropMenu).click();}
    public void clickLogoutButton() {driver.findElement(logoutButton).click();}
    public void proceedLogout() {clickLogoutDropMenu();try{Thread.sleep(3000);}catch (InterruptedException e){}clickLogoutButton();}

    public int getCartXPosition(){
        int initialX = getCartIcon().getLocation().getX();
        return initialX;
    }

    public int getCartYPosition(){
        int initialY = getCartIcon().getLocation().getY();
        return initialY;
    }
}
