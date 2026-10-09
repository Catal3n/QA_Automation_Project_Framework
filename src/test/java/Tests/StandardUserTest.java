package Tests;

import Data.Users;              // Datele de test (username + parolă) vin din clasa Users.
import Pages.LoginPage;
import TestBase.DriverFactory;
import com.aventstack.chaintest.plugins.ChainTestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ChainTestListener.class)
public class StandardUserTest extends DriverFactory {

    @Test
    public void verifyStandardUserLogin() {

        LoginPage loginPage = new LoginPage(getDriver());

        // login() apasă singur pe "Sign in", deci NU mai apelăm clickSignIn() separat
        // (înainte butonul era apăsat de două ori).
        loginPage.login(Users.STANDARD.getUsername(), Users.STANDARD.getPassword());
        ChainTestListener.log("The user logged in with: " + Users.STANDARD.getUsername());

        // Rezultatul așteptat vine tot din Users: dacă se schimbă username-ul,
        // îl modificăm doar într-un loc.
        Assert.assertEquals(loginPage.getUsername(), Users.STANDARD.getUsername(),
                "Standard user was not logged in");
        ChainTestListener.log("The username is displayed in the navbar");
    }
}