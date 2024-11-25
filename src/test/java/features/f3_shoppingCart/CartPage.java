package features.f3_shoppingCart;

import Config.Config;
import features.f1_product.ProuctPage;
import helpers.Transformer.GenericHelper;
import helpers.Transformer.JsonHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CartPage {
    WebDriver driver;
    WebDriverWait wait;

    By addToCartSelector = By.cssSelector("button[data-test^='add-to-cart']");
    By removeFromCartSelector = By.cssSelector("button[data-test^='remove']");
    By anyButton = By.tagName("button");
    By cartContainer = By.cssSelector("#cart_contents_container .cart_item");
    By productSelectorName = By.xpath(".//div[@data-test='inventory-item-name']");
    By productSelector = By.xpath("ancestor::div[@data-test='inventory-item']");
    By cart = By.xpath("//a[@class='shopping_cart_link']");
    By continueShopping = By.xpath("//button[@id='continue-shopping']");

    By shoppingCartPadge = By.xpath("//span[@class='shopping_cart_badge']");

    public CartPage(WebDriver driver) {this.driver = driver; wait = new WebDriverWait(driver, Duration.ofSeconds(10));}



    public List<WebElement> getAllButtons() {return driver.findElements(anyButton);}
    public WebElement selectRandomButton (List<WebElement> anyButtons) {return anyButtons.get(new Random().nextInt(anyButtons.size()));}
    public List<WebElement> getCartItems() {return driver.findElements(cartContainer);}
    public WebElement cartButton() {return driver.findElement(cart);}
    public WebElement countineShoppingButton() {return driver.findElement(continueShopping);}
    public WebElement getAddToCartButton(WebElement product) {return driver.findElement(addToCartSelector);}

    public WebElement getCartPadge() {return driver.findElement(shoppingCartPadge);}
    public List<WebElement> getAddToCartButtons(List<WebElement> buttons) {return buttons.stream().filter(button -> button.getText().equals("Add to cart")).toList();}
    public void clickButton (WebElement button) {button.click();}

    public String getProductFromButton(WebElement button){
        WebElement inventoryItem = button.findElement(productSelector);
        WebElement productNameElement = inventoryItem.findElement(productSelectorName);
        return productNameElement.getText();
    }

    public String proceedAddToCart() {
        try{Thread.sleep(1000);}catch (InterruptedException e){}
        if(getAddToCartButtons(getAllButtons()).size() > 0) {
            WebElement addToCartButton = selectRandomButton(getAddToCartButtons(getAllButtons()));
            String productName = getProductFromButton(addToCartButton);
            System.out.println(productName);
            try{Thread.sleep(1000);}catch (InterruptedException e){}
            clickButton(addToCartButton);
            try{Thread.sleep(2000);}catch (InterruptedException e){}
            return productName;
        }
        return null;
    }


    public List<String> proceedMultipleAddsToCart(int count){
        List<WebElement> addToCartButtons = getAddToCartButtons(getAllButtons());
        List<String> productNamesList = new ArrayList<>();
        int totalProducts = addToCartButtons.size();
        if(count > totalProducts) {
            count = totalProducts;
        }
        List<Integer> randomIndexes = GenericHelper.getRandomUniqueIndices(totalProducts,count);
        for(int index: randomIndexes) {
            WebElement addToCartButton = addToCartButtons.get(index);
            String productName = getProductFromButton(addToCartButton);
            System.out.println(productName);
            productNamesList.add(productName);
            try{Thread.sleep(1000);}catch (InterruptedException e){}
            clickButton(addToCartButton);
            try{Thread.sleep(2000);}catch (InterruptedException e){}
        }
        JsonHelper.writeProductNamesToJSON(productNamesList);
        return productNamesList;
    }


    public void verifyProductsInCart(String... expectedProductNames){
        driver.get(Config.cartURL);
        List<WebElement> cartItems = getCartItems();

        List<String> cartProductNames = new ArrayList<>();
        for (WebElement cartItem : cartItems) {
            WebElement productNameElement = cartItem.findElement(productSelectorName);
            cartProductNames.add(productNameElement.getText());
            System.out.println(productNameElement.getText());
        }
        for (String expectedProductName : expectedProductNames) {
            boolean isProductInCart = cartProductNames.contains(expectedProductName);
            Assert.assertTrue(isProductInCart);
        }

    }

    public void verifyMultipleProductsInCart(List<String> expectedProductNames) {
        verifyProductsInCart(expectedProductNames.toArray(new String[0]));
    }

    public void addProductToCartByName(String productName){
        List<WebElement> productElements = driver.findElements(productSelectorName);
        for(WebElement product : productElements){
            String name = product.getText();
            if(name.contains(productName)){
                clickButton(getAddToCartButton(product));
                try{Thread.sleep(1000);}catch (InterruptedException e){}
            }
        }
    }
    public void restoreAddedProductsToCart(){

            List<String> productNames = JsonHelper.readProductNamesFromJSON();
            for (String productName : productNames){
                addProductToCartByName(productName);
            }
    }

    public void displayCartItems(){
        try{Thread.sleep(2000);}catch (InterruptedException e){}
        clickButton(cartButton());
        try{Thread.sleep(2000);}catch (InterruptedException e){}
    }

    public String removeProductsFromCart(String... productNames) {
        displayCartItems();
        List<WebElement> cartItems = getCartItems();

        for(WebElement cartItem : cartItems) {
            String cartItemName = cartItem.getText();
            for(String productName : productNames) {
                if(cartItemName.contains(productName)) {
                    WebElement removeFromCartButton = cartItem.findElement(removeFromCartSelector);
                    try{Thread.sleep(2000);}catch (InterruptedException e){}
                    removeFromCartButton.click();
                    try{Thread.sleep(2000);}catch (InterruptedException e){}
                    System.out.println(productName+" removed from Cart");
                }
            }
        }
        return String.join(", ", productNames);
    }

    public void verifyRemoveFromCart(String... productNames) {
        List<WebElement> cartItems = getCartItems();
        try{Thread.sleep(2000);}catch (InterruptedException e){}
        for(String productName : productNames) {
            Assert.assertFalse(cartItems.contains(productName));
        }
    }

    public void continueShopping() {
        displayCartItems();
        clickButton(countineShoppingButton());
    }

    public int getNumberOfProductAdded(){
        int count = Integer.parseInt(getCartPadge().getText());
        return count;
    }
}
