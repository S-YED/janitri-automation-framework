package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import java.time.Duration;

/**
 * Page Object Model class for Janitri Login Page
 * Contains all locators and methods for login page interactions
 */
public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Locators using @FindBy annotations
    @FindBy(id = "userID")
    private WebElement userIdInput;

    @FindBy(id = "password")  
    private WebElement passwordInput;

    @FindBy(id = "loginBtn")
    private WebElement loginButton;

    @FindBy(xpath = "//span[contains(@class, 'eye-icon') or contains(@class, 'password-toggle')]")
    private WebElement passwordVisibilityToggle;

    @FindBy(xpath = "//div[contains(@class, 'error-message') or contains(@class, 'alert')]")
    private WebElement errorMessage;

    @FindBy(xpath = "//button[contains(text(), 'Allow') or contains(text(), 'Reload')]")
    private WebElement allowNotificationButton;

    @FindBy(xpath = "//h1 | //h2 | //title")
    private WebElement pageTitle;

    // Alternative locators (fallback options)
    private final String USER_ID_LOCATORS[] = {
        "input[type='text']", 
        "input[placeholder*='User']",
        "input[placeholder*='Email']",
        "input[name='username']",
        "input[name='email']"
    };

    private final String PASSWORD_LOCATORS[] = {
        "input[type='password']",
        "input[placeholder*='Password']",
        "input[name='password']"
    };

    private final String LOGIN_BUTTON_LOCATORS[] = {
        "button[type='submit']",
        "input[type='submit']",
        "button:contains('Login')",
        "button:contains('Sign In')"
    };

    private final String EYE_ICON_LOCATORS[] = {
        "[class*='eye']",
        "[class*='toggle']", 
        "[class*='visibility']",
        "i[class*='fa-eye']"
    };

    /**
     * Handle notification permission dialog
     * This method attempts to handle the notification permission requirement
     */
    public void handleNotificationPermission() {
        try {
            Thread.sleep(2000); // Wait for notification dialog to appear

            // Try to click allow/reload button if present
            try {
                if (allowNotificationButton.isDisplayed()) {
                    allowNotificationButton.click();
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                // If specific button not found, try generic approaches
                handleNotificationWithJS();
            }

            // Refresh the page after handling notification
            driver.navigate().refresh();
            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("Notification handling: " + e.getMessage());
            // Continue with test execution even if notification handling fails
        }
    }

    /**
     * Handle notification permission using JavaScript
     */
    private void handleNotificationWithJS() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Grant notification permission via JavaScript
            js.executeScript("Notification.requestPermission().then(function (permission) { console.log(permission); });");

            // Try to find and click reload/allow buttons
            String[] buttonTexts = {"Reload", "Allow", "Continue", "Proceed"};
            for (String text : buttonTexts) {
                try {
                    WebElement button = driver.findElement(By.xpath("//button[contains(text(), '" + text + "')]"));
                    if (button.isDisplayed()) {
                        button.click();
                        Thread.sleep(1000);
                        break;
                    }
                } catch (Exception ignore) {
                    // Continue to next button text
                }
            }
        } catch (Exception e) {
            System.out.println("JavaScript notification handling failed: " + e.getMessage());
        }
    }

    /**
     * Find User ID input field using multiple locator strategies
     */
    private WebElement findUserIdInput() {
        // Try primary locator first
        try {
            return userIdInput;
        } catch (Exception e) {
            // Try alternative locators
            for (String locator : USER_ID_LOCATORS) {
                try {
                    return driver.findElement(By.cssSelector(locator));
                } catch (Exception ignore) {
                    // Continue to next locator
                }
            }
            throw new RuntimeException("User ID input field not found with any locator strategy");
        }
    }

    /**
     * Find Password input field using multiple locator strategies
     */
    private WebElement findPasswordInput() {
        try {
            return passwordInput;
        } catch (Exception e) {
            for (String locator : PASSWORD_LOCATORS) {
                try {
                    return driver.findElement(By.cssSelector(locator));
                } catch (Exception ignore) {
                    // Continue to next locator
                }
            }
            throw new RuntimeException("Password input field not found with any locator strategy");
        }
    }

    /**
     * Find Login button using multiple locator strategies
     */
    private WebElement findLoginButton() {
        try {
            return loginButton;
        } catch (Exception e) {
            for (String locator : LOGIN_BUTTON_LOCATORS) {
                try {
                    return driver.findElement(By.cssSelector(locator));
                } catch (Exception ignore) {
                    // Continue to next locator
                }
            }
            throw new RuntimeException("Login button not found with any locator strategy");
        }
    }

    /**
     * Find Password visibility toggle using multiple locator strategies
     */
    private WebElement findPasswordToggle() {
        try {
            return passwordVisibilityToggle;
        } catch (Exception e) {
            for (String locator : EYE_ICON_LOCATORS) {
                try {
                    return driver.findElement(By.cssSelector(locator));
                } catch (Exception ignore) {
                    // Continue to next locator
                }
            }
            throw new RuntimeException("Password visibility toggle not found with any locator strategy");
        }
    }

    /**
     * Enter text in User ID field
     * @param userId - user ID to enter
     */
    public void enterUserId(String userId) {
        try {
            WebElement userIdField = findUserIdInput();
            wait.until(ExpectedConditions.elementToBeClickable(userIdField));
            userIdField.clear();
            userIdField.sendKeys(userId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter User ID: " + e.getMessage());
        }
    }

    /**
     * Enter text in Password field
     * @param password - password to enter
     */
    public void enterPassword(String password) {
        try {
            WebElement passwordField = findPasswordInput();
            wait.until(ExpectedConditions.elementToBeClickable(passwordField));
            passwordField.clear();
            passwordField.sendKeys(password);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter Password: " + e.getMessage());
        }
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        try {
            WebElement loginBtn = findLoginButton();
            wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
            loginBtn.click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Login button: " + e.getMessage());
        }
    }

    /**
     * Click password visibility toggle (eye icon)
     */
    public void clickPasswordVisibilityToggle() {
        try {
            WebElement toggle = findPasswordToggle();
            wait.until(ExpectedConditions.elementToBeClickable(toggle));
            toggle.click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click password visibility toggle: " + e.getMessage());
        }
    }

    /**
     * Check if login button is enabled
     * @return true if login button is enabled, false otherwise
     */
    public boolean isLoginButtonEnabled() {
        try {
            WebElement loginBtn = findLoginButton();
            return loginBtn.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if password is masked (input type = "password")
     * @return true if password is masked, false if visible
     */
    public boolean isPasswordMasked() {
        try {
            WebElement passwordField = findPasswordInput();
            String inputType = passwordField.getAttribute("type");
            return "password".equals(inputType);
        } catch (Exception e) {
            return true; // Default to masked if unable to determine
        }
    }

    /**
     * Get error message text
     * @return error message text or empty string if no error
     */
    public String getErrorMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.getText();
        } catch (Exception e) {
            // Try alternative selectors for error messages
            String[] errorSelectors = {
                ".error", ".alert", ".message", ".notification", 
                "[class*='error']", "[class*='alert']", "[class*='invalid']"
            };

            for (String selector : errorSelectors) {
                try {
                    WebElement errorElement = driver.findElement(By.cssSelector(selector));
                    if (errorElement.isDisplayed()) {
                        return errorElement.getText();
                    }
                } catch (Exception ignore) {
                    // Continue to next selector
                }
            }
            return "";
        }
    }

    /**
     * Check if error message is displayed
     * @return true if error message is visible, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        return !getErrorMessage().isEmpty();
    }

    /**
     * Clear all input fields
     */
    public void clearAllFields() {
        try {
            findUserIdInput().clear();
            findPasswordInput().clear();
        } catch (Exception e) {
            System.out.println("Failed to clear fields: " + e.getMessage());
        }
    }

    /**
     * Get page title
     * @return current page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Check if login page is loaded
     * @return true if page contains login elements, false otherwise
     */
    public boolean isLoginPageLoaded() {
        try {
            findUserIdInput();
            findPasswordInput(); 
            findLoginButton();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Perform complete login action
     * @param userId - user ID to enter
     * @param password - password to enter
     */
    public void performLogin(String userId, String password) {
        enterUserId(userId);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Wait for page to load completely
     */
    public void waitForPageLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver)
            .executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Check if User ID field is empty
     * @return true if User ID field is empty, false otherwise
     */
    public boolean isUserIdFieldEmpty() {
        try {
            WebElement userIdField = findUserIdInput();
            String value = userIdField.getAttribute("value");
            return value == null || value.trim().isEmpty();
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Check if Password field is empty
     * @return true if Password field is empty, false otherwise
     */
    public boolean isPasswordFieldEmpty() {
        try {
            WebElement passwordField = findPasswordInput();
            String value = passwordField.getAttribute("value");
            return value == null || value.trim().isEmpty();
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Check if both fields are empty
     * @return true if both User ID and Password fields are empty, false otherwise
     */
    public boolean areBothFieldsEmpty() {
        return isUserIdFieldEmpty() && isPasswordFieldEmpty();
    }
}