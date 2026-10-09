package Pages;

import TestBase.BasePage;
import org.openqa.selenium.By; // Permite identificarea elementelor web folosind locatori precum ID, XPath etc.
import org.openqa.selenium.WebDriver; // Tipul driverului primit în constructor.
import org.openqa.selenium.support.ui.ExpectedConditions;

// Clasa care reprezintă pagina de Login a aplicației.
// Știe CUM se face login-ul (ce câmpuri, ce butoane), dar NU știe CU CE DATE:
// datele le primește din test (care le ia din enum-ul Users).
// extends BasePage = moștenește driver și wait, nu le mai declară singură.
public class LoginPage extends BasePage {

    // ----- Locatori -----
    private By signInButton = By.xpath("//button[@aria-label='Sign in']");
    private By usernameInput = By.id("username");
    private By passwordInput = By.id("password");
    private By loginButton = By.xpath("//button[@data-testid='login-submit-button']");
    // Mesajul de eroare de la login (credențiale greșite, cont blocat etc.).
    private By errorMessage = By.xpath("//p[@data-testid='login-error-message']");
    private By navbarUsername = By.xpath("//span[@data-testid='navbar-username']");

    // Constructorul dă driverul mai departe către BasePage (super),
    // care îl salvează și creează wait-ul.
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ----- Acțiuni de bază (câte un pas) -----

    public void clickSignIn() {
        // Acum cu wait, ca toate celelalte acțiuni (înainte: driver.findElement, fără așteptare).
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    // ----- Flux complet -----

    // Login cu ORICE utilizator: datele vin ca parametri (din enum-ul Users).
    public void login(String username, String password) {
        clickSignIn();
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // ----- Verificări (citesc ce afișează pagina) -----

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    public String getUsername() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(navbarUsername)).getText();
    }
}