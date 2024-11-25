package features.f4_checkout;

import Config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class CheckoutPage {

    WebDriver driver;

    By userFirstNameField = By.id("first-name");
    By userSecondNameField= By.id("last-name");
    By postalCodeField = By.id("postal-code");
    By continueButton = By.id("continue");
    By errorMessage = By.xpath("//h3[@data-test='error']");
    By productName = By.className("cart_item");
    By productPrice = By.className("inventory_item_price");
    By productQuantity = By.className("cart_quantity");
    By paymentInfo = By.xpath("//div[normalize-space()='SauceCard #31337']");
    By shippingInfo = By.xpath("//div[normalize-space()='Shipping Information:']");
    By totalPrice = By.xpath("//div[@class='summary_subtotal_label']");
    By tax = By.xpath("//div[@class='summary_tax_label']");
    By total = By.xpath("//div[@class='summary_total_label']");
    By cancelButton = By.id("cancel");
    By finishButton = By.id("finish");
    By backHome = By.id("back-to-products");

    //constructor
    public CheckoutPage(WebDriver driver) {this.driver = driver;}

    //Methods
    public void enterFirstName(String firstName) {driver.findElement(userFirstNameField).sendKeys(firstName);}
    public void enterSecondName(String secondName) {driver.findElement(userSecondNameField).sendKeys(secondName);}
    public void enterPostalCode(String postalCode) {driver.findElement(postalCodeField).sendKeys(postalCode);}
    public void clickContinueButton() {driver.findElement(continueButton).click();}
    public String getErrorMessage(){ return driver.findElement(errorMessage).getText();}

    public void proceedCheckout(String firstName, String secondName, String postalCode) {
        enterFirstName(firstName);
        enterSecondName(secondName);
        enterPostalCode(postalCode);
        clickContinueButton();
    }

    public String getPaymentInfo() {return driver.findElement(paymentInfo).getText();}
    public String getShippingInfo() {return driver.findElement(shippingInfo).getText();}
    public int getProductQuantity() {return Integer.parseInt(driver.findElement(productQuantity).getText());}
    public int getProductListSize() {
        List<WebElement> productList = driver.findElements(productName);
        return productList.size();
    }
    public String calculateTotalProductsPrice(){
        int total = 0;
        for (int i = 0; i < getProductListSize(); i++) {
            if(productQuantity.equals("1")){
                total += Integer.parseInt(driver.findElement(productPrice).getText());
            }else{
                total += Integer.parseInt(driver.findElement(productPrice).getText()) * Integer.parseInt(driver.findElement(productQuantity).getText());
            }
        }
        return String.valueOf(total);
    }
    public String getTotalPrice() {return driver.findElement(totalPrice).getText();}
    public String getTax() {return driver.findElement(tax).getText();}
    public String getTotal() {return driver.findElement(total).getText();}
    public void clickCancelButton() {driver.findElement(cancelButton).click();}
    public void clickFinishButton() {driver.findElement(finishButton).click();}
    public void clickBackHome() {driver.findElement(backHome).click();}





}
