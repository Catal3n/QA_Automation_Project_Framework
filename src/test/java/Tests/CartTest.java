package Tests;

import Data.Users;
import Pages.CartPage;
import Pages.LoginPage;
import Pages.ProductsPage;
import TestBase.DriverFactory;
import com.aventstack.chaintest.plugins.ChainTestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ChainTestListener.class)
public class CartTest extends DriverFactory {

    @Test
    public void verifyProductAppearsInCart() {

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(Users.STANDARD.getUsername(), Users.STANDARD.getPassword());
        ChainTestListener.log("The user logged in with: " + Users.STANDARD.getUsername());

        // Pe pagina de produse: punem Bluetooth Speaker în coș.
        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.addProductToCart(ProductsPage.BLUETOOTH_SPEAKER_ID);
        ChainTestListener.log("The user added Bluetooth Speaker to cart");

        // Deschidem coșul și verificăm produsul, cantitatea și totalul.
        CartPage cartPage = new CartPage(getDriver());
        cartPage.open();
        ChainTestListener.log("The user opened the cart");

        Assert.assertEquals(cartPage.getProductName(ProductsPage.BLUETOOTH_SPEAKER_ID), "Bluetooth Speaker");
        Assert.assertEquals(cartPage.getProductQuantity(ProductsPage.BLUETOOTH_SPEAKER_ID), "1");
        Assert.assertEquals(cartPage.getOrderTotal(), "$159.99");
        ChainTestListener.log("The cart contains Bluetooth Speaker, quantity 1, total $159.99");
    }

    @Test
    public void verifyRemoveProductFromCart() {

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(Users.STANDARD.getUsername(), Users.STANDARD.getPassword());
        ChainTestListener.log("The user logged in with: " + Users.STANDARD.getUsername());

        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.addProductToCart(ProductsPage.BLUETOOTH_SPEAKER_ID);
        ChainTestListener.log("The user added Bluetooth Speaker to cart");

        CartPage cartPage = new CartPage(getDriver());
        cartPage.open();
        ChainTestListener.log("The user opened the cart");

        // Scoatem singurul produs: coșul trebuie să devină gol.
        cartPage.removeProduct(ProductsPage.BLUETOOTH_SPEAKER_ID);
        ChainTestListener.log("The user removed Bluetooth Speaker from cart");

        Assert.assertEquals(cartPage.getEmptyCartMessage(), "Your cart is empty");
        ChainTestListener.log("The message 'Your cart is empty' is displayed");
    }
}