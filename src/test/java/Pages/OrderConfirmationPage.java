package Pages;

import TestBase.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

// Pagina afișată după plasarea comenzii: "Order Confirmed!" + numărul comenzii.
public class OrderConfirmationPage extends BasePage {

    private By confirmationHeading = By.xpath("//h1[@data-testid='order-confirmation-heading']");
    private By orderNumber = By.xpath("//p[@data-testid='order-confirmation-number']");

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    // Titlul paginii, ex. "Order Confirmed!".
    // Wait-ul e important: după click pe Place Order, pagina are nevoie de puțin timp să se încarce.
    public String getConfirmationHeading() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationHeading)).getText();
    }

    // Textul cu numărul comenzii, ex. "Order #2234". Numărul diferă la fiecare comandă.
    public String getOrderNumber() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderNumber)).getText();
    }
}
