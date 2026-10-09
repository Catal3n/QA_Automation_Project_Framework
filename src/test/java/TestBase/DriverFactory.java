package TestBase;

import com.aventstack.chaintest.plugins.ChainTestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DriverFactory {

    // Logger-ul acestei clase: prin el scriem mesaje în consolă și în fișierele de log.
    // static final = un singur logger pentru toată clasa, creat o singură dată.
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    private WebDriver driver;

    @BeforeMethod
    // Se execută înainte de fiecare metodă marcată cu @Test.
    // TestNG ne dă automat parametrul "method" = testul care urmează să ruleze.
    public void initialiseBrowser(Method method) {
        // Numele testului, ex. "CartTest.verifyRemoveProductFromCart".
        String testName = getClass().getSimpleName() + "." + method.getName();

        // Îi spunem lui Log4j2 cum se numește testul curent.
        // log4j2.xml folosește valoarea în ${ctx:testName} ca să creeze câte un fișier pe test.
        ThreadContext.put("testName", testName);

        logger.info("========== START TEST: {} ==========", testName);

        ChromeOptions options = new ChromeOptions(); // Cream un obiect ChromeOptions.

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://qademo.com/");
        logger.info("Browser Chrome deschis pe https://qademo.com/");
    }

    @AfterMethod
    // Se execută după fiecare test, chiar dacă a picat.
    // TestNG ne dă automat "result" = rezultatul testului care tocmai s-a terminat.
    public void clearBrowser(ITestResult result) {
        String testName = getClass().getSimpleName() + "." + result.getMethod().getMethodName();

        // Scriem în log rezultatul testului.
        if (result.getStatus() == ITestResult.SUCCESS) {
            logger.info("TEST TRECUT: {}", testName);
        } else if (result.getStatus() == ITestResult.FAILURE) {
            // ERROR + motivul exact (mesajul din Assert sau excepția).
            logger.error("TEST PICAT: {}", testName);
            logger.error("Motiv: {}", result.getThrowable().getMessage());
            // Poza se face ÎNAINTE de driver.quit(), cât timp browserul e încă deschis.
            takeScreenshot(testName);
        } else {
            logger.warn("TEST SĂRIT: {}", testName);
        }

        // Închidem browserul. Dacă vrei să vezi ecranul după un test picat,
        // comentează DOAR aceste 3 linii, nu toată metoda (altfel pierzi logul cu rezultatul).
        if (driver != null) {
            driver.quit();
        }

        logger.info("========== END TEST: {} ==========", testName);

        // Ștergem numele testului din context, ca să nu "rămână agățat" la testul următor.
        ThreadContext.clearAll();
    }

    public WebDriver getDriver() {
        return driver;
    }

    // Face o captură de ecran a browserului și o salvează în target/screenshots/.
    // Numele fișierului = numele testului + ora, ca să nu se suprascrie
    private void takeScreenshot(String testName) {
        try {
            // TakesScreenshot = "capacitatea" driverului de a face poze.
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            Path destination = Paths.get("target", "screenshots",
                    testName + "_" + System.currentTimeMillis() + ".png");

            Files.createDirectories(destination.getParent()); // creează folderul dacă nu există
            Files.copy(screenshot.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            logger.error("Screenshot salvat: {}", destination.toAbsolutePath());
            // Atașăm poza și în raportul ChainTest, sub testul care a picat.
            // Text în raport, deasupra pozei, ca să se înțeleagă ce reprezintă.
            ChainTestListener.log("Poza la eroare: " + testName);
            ChainTestListener.embed(Files.readAllBytes(destination), "image/png");
        } catch (Exception e) {
            // Dacă poza nu reușește, NU vrem să stricăm restul (închiderea browserului etc.).
            logger.warn("Nu am putut face screenshot: {}", e.getMessage());
        }
    }

}