package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;
import pages.LoginPage;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * BaseTest class for browser setup and teardown
 * Implements WebDriver initialization and common test configurations
 */
public class BaseTest {

    protected static WebDriver driver;
    protected LoginPage loginPage;

    // Configuration properties
    private static final String BASE_URL = "https://dev-dash.janitri.in";
    private static final int IMPLICIT_WAIT_TIMEOUT = 10;
    private static final int PAGE_LOAD_TIMEOUT = 30;

    /**
     * Setup method to initialize WebDriver before each test class
     */
    @BeforeClass
    public void setUp() {
        initializeDriver("chrome");
        configureDriver();
        initializePages();
        navigateToApplication();
    }

    /**
     * Initialize WebDriver based on browser type
     * @param browserType - chrome, firefox, or edge
     */
    private void initializeDriver(String browserType) {
        switch (browserType.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();

                // Handle notification permissions
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("profile.default_content_setting_values.notifications", 1);
                chromeOptions.setExperimentalOption("prefs", prefs);

                // Additional Chrome options for stability
                chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-plugins");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");

                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            default:
                throw new RuntimeException("Browser not supported: " + browserType);
        }
    }

    /**
     * Configure WebDriver with timeouts and window settings
     */
    private void configureDriver() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIMEOUT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        driver.manage().deleteAllCookies();
    }

    /**
     * Initialize Page Object instances
     */
    private void initializePages() {
        loginPage = new LoginPage(driver);
    }

    /**
     * Navigate to the application URL
     */
    private void navigateToApplication() {
        driver.get(BASE_URL);

        // Handle notification permission dialog if present
        try {
            Thread.sleep(2000); // Wait for page to load and notification prompt to appear
            // Note: Notification handling will be done in LoginPage class
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Get WebDriver instance
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        return driver;
    }

    /**
     * Get base URL
     * @return application base URL
     */
    public String getBaseUrl() {
        return BASE_URL;
    }

    /**
     * Teardown method to close WebDriver after each test class
     */
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Method to refresh page - useful for notification handling
     */
    protected void refreshPage() {
        driver.navigate().refresh();
    }

    /**
     * Method to get current page title
     * @return current page title
     */
    protected String getCurrentPageTitle() {
        return driver.getTitle();
    }

    /**
     * Method to get current URL
     * @return current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}