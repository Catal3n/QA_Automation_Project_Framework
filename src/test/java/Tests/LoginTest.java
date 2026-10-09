package Tests;

import Pages.LoginPage;
import TestBase.DriverFactory;
import com.aventstack.chaintest.plugins.ChainTestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ChainTestListener.class)
public class LoginTest extends DriverFactory {

    @Test
    public void verifyLoginPage() {

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.clickSignIn();
        ChainTestListener.log("The user clicked on Sign in");
        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("/login"),"Login page was not opened.");
        ChainTestListener.log("The login page was opened: " + actualUrl);
    }
}
