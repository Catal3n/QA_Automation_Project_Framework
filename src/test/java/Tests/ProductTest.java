package Tests;

import Pages.LoginPage;
import Pages.ProductsPage;
import TestBase.DriverFactory;
import Data.Users;
import com.aventstack.chaintest.plugins.ChainTestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

@Listeners(ChainTestListener.class)
public class ProductTest extends DriverFactory {

    @Test
    public void verifyAddProductToCart() {

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(Users.STANDARD.getUsername(), Users.STANDARD.getPassword());
        ChainTestListener.log("The user logged in with: " + Users.STANDARD.getUsername());

        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.addProductToCart(ProductsPage.BLUETOOTH_SPEAKER_ID);
        ChainTestListener.log("The user added Bluetooth Speaker to cart");
        Assert.assertEquals(productsPage.getCartBadge(), "1");
        ChainTestListener.log("The cart badge shows 1 product");
    }

    @Test
    public void verifyAddMultipleProductsToCart() {

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(Users.STANDARD.getUsername(), Users.STANDARD.getPassword());
        ChainTestListener.log("The user logged in with: " + Users.STANDARD.getUsername());

        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.addProductToCart(ProductsPage.BLUETOOTH_SPEAKER_ID);
        productsPage.addProductToCart(ProductsPage.FITNESS_TRACKER_ID);
        ChainTestListener.log("The user added Bluetooth Speaker and Fitness Tracker to cart");

        Assert.assertEquals(productsPage.getCartBadge(), "2");
        ChainTestListener.log("The cart badge shows 2 products");
    }

    @Test
    public void verifyProductsAreDisplayed() {

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(Users.STANDARD.getUsername(), Users.STANDARD.getPassword());
        ChainTestListener.log("The user logged in with: " + Users.STANDARD.getUsername());

        ProductsPage productsPage = new ProductsPage(getDriver());
        List<String> products = productsPage.getAllProductNames();
        ChainTestListener.log("Products found on the page: " + products.size());

        Assert.assertTrue(products.contains("Fitness Tracker"), "Fitness Tracker nu apare în listă");
        Assert.assertTrue(productsPage.getProductCount() > 0, "Nu apare niciun produs pe pagină");
        ChainTestListener.log("Fitness Tracker is displayed in the product list");
    }

    // Test care PICĂ INTENȚIONAT, pentru demonstrație.
    // Adăugăm 1 produs, dar ne așteptăm (greșit) ca badge-ul coșului să arate "5".
    // Rezultat așteptat la rulare: AssertionError -> expected [5] but found [1].
    @Test(description = "Eroare introdusă intenționat: badge-ul coșului vs. o valoare greșită")
    public void verifyCartBadgeIntentionalFail() {

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(Users.STANDARD.getUsername(), Users.STANDARD.getPassword());
        ChainTestListener.log("The user logged in with: " + Users.STANDARD.getUsername());

        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.addProductToCart(ProductsPage.BLUETOOTH_SPEAKER_ID);
        ChainTestListener.log("The user added Bluetooth Speaker to cart");


        // GREȘIT INTENȚIONAT: în coș e 1 produs, nu 5.
        Assert.assertEquals(productsPage.getCartBadge(), "5",
                "Badge-ul coșului nu arată numărul așteptat de produse");
        ChainTestListener.log("The cart badge shows the expected number of products");
    }

}