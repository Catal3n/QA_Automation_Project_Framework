package Pages;

import Data.CheckoutData;
import TestBase.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

// Pagina de checkout (/checkout): datele de livrare, datele de plată și plasarea comenzii.
// Se ajunge aici din coș, cu CartPage.proceedToCheckout(), și doar dacă utilizatorul e logat.
public class CheckoutPage extends BasePage {

    // ----- Locatori: Shipping Information -----
    private By firstNameInput = By.xpath("//input[@data-testid='checkout-first-name']");
    private By lastNameInput = By.xpath("//input[@data-testid='checkout-last-name']");
    private By addressInput = By.xpath("//textarea[@data-testid='checkout-address']"); // textarea, nu input!

    // ----- Locatori: Payment Information -----
    private By cardNumberInput = By.xpath("//input[@data-testid='checkout-card-number']");
    private By expiryInput = By.xpath("//input[@data-testid='checkout-expiry']");
    private By cvvInput = By.xpath("//input[@data-testid='checkout-cvv']");
    private By nameOnCardInput = By.xpath("//input[@data-testid='checkout-cardholder-name']");

    private By placeOrderButton = By.xpath("//button[@data-testid='place-order-button']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // ----- Shipping Information -----

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput)).sendKeys(lastName);
    }

    public void enterAddress(String address) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addressInput)).sendKeys(address);
    }

    // ----- Payment Information -----

    public void enterCardNumber(String cardNumber) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardNumberInput)).sendKeys(cardNumber);
    }

    public void enterExpiry(String expiry) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(expiryInput)).sendKeys(expiry);
    }

    public void enterCvv(String cvv) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cvvInput)).sendKeys(cvv);
    }

    public void enterNameOnCard(String nameOnCard) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameOnCardInput)).sendKeys(nameOnCard);
    }

    // ----- Finalizare -----

    public void clickPlaceOrder() {
        // Click cu reîncercare: butonul e jos pe pagină și uneori e acoperit temporar.
        clickWhenNotCovered(placeOrderButton);
    }

    // Completează TOT formularul dintr-un obiect CheckoutData.
    // Testul nu mai apelează 7 metode, ci una singură.
    public void fillCheckoutForm(CheckoutData data) {
        enterFirstName(data.firstName());
        enterLastName(data.lastName());
        enterAddress(data.address());
        enterCardNumber(data.cardNumber());
        enterExpiry(data.expiry());
        enterCvv(data.cvv());
        enterNameOnCard(data.nameOnCard());
    }
}