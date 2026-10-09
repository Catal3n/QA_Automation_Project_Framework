package Pages;

import TestBase.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage extends BasePage {

    // ID-uri cunoscute de produse, confirmate din data-testid pe site.
    // Le folosim ca ancore în teste, în loc de cifre fără sens (magic numbers).
    public static final int BLUETOOTH_SPEAKER_ID = 4;
    public static final int FITNESS_TRACKER_ID = 5;

    private By cartBadge = By.xpath("//span[@data-testid='navbar-cart-badge']");

    // Locator generic: prinde ORICE h3 al cărui data-testid începe cu
    // "product-name-", indiferent de ID. Confirmat pe site (Fitness Tracker
    // respectă același tipar ca Bluetooth Speaker).
    private By allProductNames = By.xpath("//h3[starts-with(@data-testid,'product-name-')]");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    private By productNameLocator(int productId) {
        return By.xpath("//h3[@data-testid='product-name-" + productId + "']");
    }

    private By addToCartButtonLocator(int productId) {
        return By.xpath("//button[@data-testid='product-add-to-cart-" + productId + "']");
    }

    public void clickProduct(int productId) {
        wait.until(ExpectedConditions.elementToBeClickable(productNameLocator(productId))).click();
    }

    public void addProductToCart(int productId) {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonLocator(productId))).click();
    }

    public String getCartBadge() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
    }

    // Returnează numele tuturor produselor afișate pe pagină.
    // ATENȚIE: lăsat intenționat așa, pentru demonstrație (test instabil / flaky).
    // visibilityOfAll... așteaptă ca TOATE cele ~129 de produse să fie vizibile.
    // Cardurile apar cu animație (opacity 0 -> 1), iar la rulare în paralel ultimele nu apar
    // în 10 secunde -> TimeoutException.
    // Reparație: presenceOfAllElementsLocatedBy + getDomProperty("textContent").
    public List<String> getAllProductNames() {
        List<WebElement> elements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(allProductNames));
        return elements.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    // Câte produse sunt afișate în total.
    public int getProductCount() {
        return getAllProductNames().size();
    }
}