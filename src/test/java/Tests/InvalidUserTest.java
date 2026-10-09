package Tests;

import Data.Users;              // Datele de test vin din clasa Users.
import Pages.LoginPage;
import TestBase.DriverFactory;
import com.aventstack.chaintest.plugins.ChainTestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ChainTestListener.class)
public class InvalidUserTest extends DriverFactory {

    @Test
    public void verifyInvalidUserLogin() {

        LoginPage loginPage = new LoginPage(getDriver());

        // Test NEGATIV: încercăm login cu credențiale greșite.
        loginPage.login(Users.INVALID.getUsername(), Users.INVALID.getPassword());
        ChainTestListener.log("The user tried to log in with invalid credentials: " + Users.INVALID.getUsername());

        // Ne așteptăm să apară mesajul de eroare, nu să intrăm în cont.
        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid username or password");
        ChainTestListener.log("The error message 'Invalid username or password' is displayed");
    }
}