package features.f1_product;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ProuctPage {

    WebDriver driver;

    By productContainer = By.cssSelector("#inventory_container .inventory_item");
    By productName = By.cssSelector(".inventory_item_name ");
    By productDescription = By.cssSelector(".inventory_item_desc");
    By productPrice = By.cssSelector(".inventory_item_price");
    By productImage = By.tagName("img");
    By productSortMenu = By.xpath("//select[@class='product_sort_container']");
    By sortNameAZ = By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[1]");
    By sortNameZA = By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[2]");
    By sortPriceLowToHigh = By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[3]");
    By sortPriceHighToLow = By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[4]");
    By reactMenu = By.id("react-burger-menu-btn");
    By aboutButton = By.id("about_sidebar_link");
    By closeReactMenu = By.id("react-burger-cross-btn");
    By addToCart= By.cssSelector("button[data-test^='add-to-cart']");
    By removeFromCart= By.cssSelector("button[data-test^='remove']");

    By twitterSelector = By.xpath("//a[normalize-space()='Twitter']");
    By facebookSelector = By.xpath("//a[normalize-space()='Facebook']");
    By linkedinSelector = By.xpath("//a[normalize-space()='LinkedIn']");


    public ProuctPage(WebDriver driver) {this.driver = driver;}
    public List<WebElement> getProductContainer() {return driver.findElements(productContainer);}
    public String getProductName(WebElement product) {return product.findElement(productName).getText();}
    public String getProductDescription(WebElement product) {return product.findElement(productDescription).getText();}
    public String getProductPrice(WebElement product) {return product.findElement(productPrice).getText();}
    public String getProductImage(WebElement product) {return product.findElement(productImage).getAttribute("src");}
    public void clickSortMenu() {driver.findElement(productSortMenu).click();}
    public void selectSortNameAZ() {driver.findElement(sortNameAZ).click();}
    public void selectSortNameZA() {driver.findElement(sortNameZA).click();}
    public void selectSortPriceLowToHigh() {driver.findElement(sortPriceLowToHigh).click();}
    public void selectSortPriceHighToLow() {driver.findElement(sortPriceHighToLow).click();}
    public void openReactMeanu() {driver.findElement(reactMenu).click();}
    public void clickAboutButton() {driver.findElement(aboutButton).click();}
    public void closeReactMenu() {driver.findElement(closeReactMenu).click();}
    public void clickAddToCart(WebElement button) {button.click();}
    public String addToCartText(WebElement button){return button.getText();}
    public WebElement addToCartButton() {return driver.findElement(addToCart);}
    public WebElement removeFromCartButton() {return driver.findElement(removeFromCart);}

    public void clickTwitterIcon() {driver.findElement(twitterSelector).click();}
    public void clickFacebookIcon() {driver.findElement(facebookSelector).click();}
    public void clickLinkedinIcon() {driver.findElement(linkedinSelector).click();}




    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        List<WebElement> productsElements = getProductContainer();

        if(productsElements != null) {
            for(WebElement productElement : productsElements) {
                String productName = getProductName(productElement);
                String productDescription = getProductDescription(productElement);
                String productImage = getProductImage(productElement);
                String productPrice = getProductPrice(productElement);

                Product product = new Product(productName,productDescription, productImage, productPrice);
                products.add(product);
            }
        }
        return products;
    }

    public void addToCartAllProducts() {
        List<WebElement> addToCartButtons = driver.findElements(addToCart);
        for(WebElement button : addToCartButtons) {
            try{Thread.sleep(1000);}catch (InterruptedException e){}
            Assert.assertTrue(button.isDisplayed(), "Add to Cart button not displayed.");
            clickAddToCart(button);
            try{Thread.sleep(1000);}catch (InterruptedException e){}
            Assert.assertTrue(removeFromCartButton().isDisplayed(), "Remove from Cart button not displayed.");


        }
    }

    public void removeFromCartAllProducts() {
        addToCartAllProducts();
        List<WebElement> removeFromCartButtons = driver.findElements(removeFromCart);
        for(WebElement button : removeFromCartButtons) {
            try{Thread.sleep(1000);}catch (InterruptedException e){}
            Assert.assertTrue(button.isDisplayed(), "Remove from Cart button not displayed.");
            clickAddToCart(button);
            try{Thread.sleep(1000);}catch (InterruptedException e){}
            Assert.assertTrue(addToCartButton().isDisplayed(), "Remove from Cart button not displayed.");
        }
    }



    public void sortProductNamesAZ() { clickSortMenu(); selectSortNameAZ();}
    public void sortProductNamesZA() { clickSortMenu(); selectSortNameZA();}
    public void sortProductPriceLowToHigh() { clickSortMenu(); selectSortPriceLowToHigh();}
    public void sortProductPriceHighToLow() { clickSortMenu(); selectSortPriceHighToLow();}


    public boolean areProductsSortedAZ() {
        List<Product> products = getProducts();
        boolean sorted = true;
        for (int i = 0; i < products.size() - 1; i++) {
            if(products.get(i).getName().compareTo(products.get(i+1).getName()) > 0) {
                sorted = false;
                break;
            }
        }
        return sorted;
    }

    public boolean areProductsSortedZA() {
        List<Product> products = getProducts();
        boolean sorted = true;
        for (int i = 0; i < products.size() - 1; i++) {
            if(products.get(i).getName().compareTo(products.get(i+1).getName()) < 0) {
                sorted = false;
                break;
            }
        }
        return sorted;
    }

    public boolean areProductsSortedPriceLowToHigh() {
        List<Product> products = getProducts();
        boolean sorted = true;
        for (int i = 0; i < products.size() - 1; i++) {
            if(products.get(i).getPrice() == null || products.get(i+1).getPrice() == null){
                sorted = false;
                break;
            }
            if(Double.parseDouble(products.get(i).getPrice().replace("$", "")) > (Double.parseDouble(products.get(i+1).getPrice().replace("$", "")))) {
                sorted = false;
                break;
            }
        }
        return sorted;
    }

    public boolean areProductsSortedPriceHighToLow() {
        List<Product> products = getProducts();
        boolean sorted = true;
        for (int i = 0; i < products.size() - 1; i++) {
            if(products.get(i).getPrice() == null || products.get(i+1).getPrice() == null){
                sorted = false;
                break;
            }
            if(Double.parseDouble(products.get(i).getPrice().replace("$", "")) < (Double.parseDouble(products.get(i+1).getPrice().replace("$", "")))) {
                sorted = false;
                break;
            }
        }
        return sorted;
    }

    public void selectAboutButton() {
        openReactMeanu();
        try{Thread.sleep(3000);}catch (InterruptedException e){}
        clickAboutButton();
    }



}
