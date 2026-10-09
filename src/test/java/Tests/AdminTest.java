package Tests;

import Data.Users; // Datele de test vin din clasa Users.
import Pages.LoginPage;
import TestBase.DriverFactory;
import com.aventstack.chaintest.plugins.ChainTestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ChainTestListener.class)
public class AdminTest extends DriverFactory {

    @Test
    public void verifyAdminLogin() {

        LoginPage loginPage = new LoginPage(getDriver());

        // Login cu utilizatorul ADMIN.
        loginPage.login(Users.ADMIN.getUsername(), Users.ADMIN.getPassword());
        ChainTestListener.log("The user logged in with: " + Users.ADMIN.getUsername());

        // Verificăm că în bara de navigare apare numele adminului.
        Assert.assertEquals(loginPage.getUsername(), Users.ADMIN.getUsername(),"Admin user was not logged in");
        ChainTestListener.log("The admin username is displayed in the navbar");
    }
}