package Tests;

import Data.CheckoutData;
import Data.CheckoutDataReader;
import Data.Users;
import Pages.*;
import TestBase.DriverFactory;
import com.aventstack.chaintest.plugins.ChainTestListener;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.util.List;

@Listeners(ChainTestListener.class)
public class CheckoutTest extends DriverFactory {

    // Furnizorul de date: citește clienții din src/test/resources/checkoutData.xml.
    // Fiecare <dataSet_N> din XML devine o rulare separată a testului.
    // indices = {0}  -> rulează doar primul client din XML (Catalin)
    // indices = {1}  -> doar al doilea (Vlad)
    // fără indices   -> toți
    @DataProvider(name = "validCheckoutData", indices = {0,1})
    public Object[][] validCheckoutData() {
        List<CheckoutData> dataSets = CheckoutDataReader.readFromXml("checkoutData.xml");

        // TestNG vrea un tabel: un rând pentru fiecare client din XML.
        Object[][] rows = new Object[dataSets.size()][1];
        for (int i = 0; i < dataSets.size(); i++) {
            rows[i][0] = dataSets.get(i);
        }
        return rows;
    }

    @Test(dataProvider = "validCheckoutData")
    public void verifyPlaceOrder(CheckoutData data) {

        ChainTestListener.log("Checkout data: " + data.firstName() + " " + data.lastName() + ", " + data.address());
        // 1. Login (checkout-ul cere cont).
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(Users.STANDARD.getUsername(), Users.STANDARD.getPassword());
        ChainTestListener.log("The user logged in with: " + Users.STANDARD.getUsername());

        // 2. Adăugăm un produs și mergem în coș.
        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.addProductToCart(ProductsPage.BLUETOOTH_SPEAKER_ID);
        ChainTestListener.log("The user added Bluetooth Speaker to cart");

        CartPage cartPage = new CartPage(getDriver());
        cartPage.open();
        cartPage.proceedToCheckout();
        ChainTestListener.log("The user went from cart to checkout");

        // 3. Completăm formularul de checkout.
        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        checkoutPage.fillCheckoutForm(data);
        ChainTestListener.log("The user filled in the checkout form");

        // 4. Plasăm comanda.
        checkoutPage.clickPlaceOrder();
        ChainTestListener.log("The user clicked on Place Order");

        // 5. Verificăm pagina de confirmare.
        OrderConfirmationPage confirmationPage = new OrderConfirmationPage(getDriver());

        // Titlul trebuie să fie exact acesta.
        Assert.assertEquals(confirmationPage.getConfirmationHeading(), "Order Confirmed!");

        // Numărul se schimbă la fiecare comandă, deci nu-l comparăm exact.
        // Verificăm doar FORMATUL: "Order #" urmat de una sau mai multe cifre.
        String orderNumber = confirmationPage.getOrderNumber();
        Assert.assertTrue(orderNumber.matches("Order #\\d+"),
                "Numărul comenzii nu are formatul așteptat: " + orderNumber);
        ChainTestListener.log("The order was confirmed: " + orderNumber);
    }
}
