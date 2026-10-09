package Pages;

import TestBase.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

// Pagina de coș (/cart): produsele ALESE de utilizator, totalul și trecerea la checkout.
// Elementele unui produs din coș au în data-testid același ID ca în ProductsPage
// (ex. cart-item-name-4 pentru Bluetooth Speaker), deci folosim aceleași constante.
public class CartPage extends BasePage {

    // ----- Locatori ficși (există o singură dată pe pagină) -----
    private By cartLink = By.xpath("//a[@data-testid='navbar-cart-link']"); // iconița din bara de sus
    private By orderTotal = By.xpath("//span[@data-testid='order-total']");
    private By checkoutButton = By.xpath("//button[@data-testid='proceed-to-checkout-button']");
    private By emptyCartHeading = By.xpath("//h1[@data-testid='cart-empty-heading']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ----- Locatori generați din ID-ul produsului (ca în ProductsPage) -----

    private By itemNameLocator(int productId) {
        return By.xpath("//a[@data-testid='cart-item-name-" + productId + "']");
    }

    private By itemQuantityLocator(int productId) {
        return By.xpath("//span[@data-testid='cart-item-quantity-" + productId + "']");
    }

    private By removeButtonLocator(int productId) {
        return By.xpath("//button[@data-testid='cart-item-remove-" + productId + "']");
    }

    // ----- Acțiuni -----

    // Deschide coșul din iconița de sus (funcționează de pe orice pagină).
    public void open() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }

    public void removeProduct(int productId) {
        wait.until(ExpectedConditions.elementToBeClickable(removeButtonLocator(productId))).click();
    }

    public void proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }

    // ----- Verificări -----

    public String getProductName(int productId) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(itemNameLocator(productId))).getText();
    }

    public String getProductQuantity(int productId) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(itemQuantityLocator(productId))).getText();
    }

    public String getOrderTotal() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderTotal)).getText();
    }

    // Așteaptă mesajul "Your cart is empty" (apare după ce scoți ultimul produs).
    public String getEmptyCartMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartHeading)).getText();
    }
}