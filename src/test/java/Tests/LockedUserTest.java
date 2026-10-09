package Tests;

import Data.Users;              // Datele de test vin din clasa Users.
import Pages.LoginPage;
import TestBase.DriverFactory;
import com.aventstack.chaintest.plugins.ChainTestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ChainTestListener.class)
public class LockedUserTest extends DriverFactory {

    @Test
    public void verifyLockedUserLogin() {

        LoginPage loginPage = new LoginPage(getDriver());

        // Test NEGATIV: contul există, dar este blocat.
        loginPage.login(Users.LOCKED.getUsername(), Users.LOCKED.getPassword());
        ChainTestListener.log("The user tried to log in with a locked account: " + Users.LOCKED.getUsername());

        // getErrorMessage() AȘTEAPTĂ să apară mesajul (wait), apoi îi citește textul.
        Assert.assertEquals(loginPage.getErrorMessage(), "Account is locked","Locked user error message was not displayed");
        ChainTestListener.log("The error message 'Account is locked' is displayed");
    }
}