package TestBase;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Clasă abstractă = nu poate fi instanțiată direct (nu poți face "new BasePage(driver)").
// Există doar ca să fie extinsă de alte Page Objects (LoginPage, ProductsPage etc.),
// astfel încât acestea să nu mai declare separat driver și wait.
public abstract class BasePage {

    // protected = vizibil pentru clasele copil (LoginPage, ProductsPage),
    // dar nu public, deci testele tot nu au acces direct la driver.
    protected WebDriver driver;
    protected WebDriverWait wait;

    // Constructorul primește driver-ul de la clasa copil (prin super(driver))
    // și inițializează wait-ul o singură dată, la un loc comun.
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Derulează pagina până când elementul ajunge în MIJLOCUL ecranului.
    // Util când un element e la marginea ecranului și e acoperit de altceva
    // (bară fixă, notificare), iar click-ul normal dă ElementClickInterceptedException.
    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    // Click "rezistent": dacă ceva acoperă temporar elementul (notificare, animație),
    // nu picăm din prima, ci reîncercăm click-ul la fiecare 0,5 secunde, maxim 10 secunde.
    protected void clickWhenNotCovered(By locator) {
        // 1. Așteptăm ca elementul să fie gata și îl aducem în ecran.
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollToElement(element);

        // 2. Un wait SEPARAT, care ignoră eroarea "click intercepted":
        //    dacă click-ul e blocat, nu oprește testul, ci încearcă din nou.
        //    (Nu folosim wait-ul comun, ca să nu schimbăm comportamentul celorlalte metode.)
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .ignoring(ElementClickInterceptedException.class)
                .until(d -> {
                    element.click(); // dacă reușește, mergem mai departe
                    return true;     // "true" îi spune wait-ului că am terminat
                });
    }
}