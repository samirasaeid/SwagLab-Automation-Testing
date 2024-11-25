package Mocks;
import Config.Config;
import helpers.Transformer.JsonHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.mockito.Mockito;

import java.io.FileNotFoundException;

public class MockUserServices {


    public static void mockLoginPage(WebDriver driver) throws FileNotFoundException {
        driver.get(Config.baseURL);
        String[] mockData = JsonHelper.parseMockData(Config.mockDataPath + Config.FileNames.MOCK_DATA.getFileName(), 0);
        Cookie cookie = new Cookie(mockData[0],mockData[1]);
        driver.manage().addCookie(cookie);
    }
    public static WebElement mockAddToCart(WebDriver driver) throws FileNotFoundException {
        WebElement addToCartButton = Mockito.mock(WebElement.class);

        Mockito.when(driver.findElement(By.cssSelector("button[data-test^='add-to-cart']"))).thenReturn(addToCartButton);
        Mockito.when(addToCartButton.getText()).thenReturn("Add to cart");

        Mockito.doAnswer(invocation -> {
            Mockito.when(addToCartButton.getText()).thenReturn("Remove");
            Mockito.when(addToCartButton.getAttribute("data-test")).thenReturn("remove");
            return null;
        }).when(addToCartButton).click();

        Mockito.when(driver.findElement(By.cssSelector("button[data-test^='add-to-cart']"))).thenReturn(addToCartButton);
        return addToCartButton;

    }
}
