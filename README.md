# Janitri Login Page Automation Framework

This project contains automated tests for the Janitri Dashboard login page using Selenium WebDriver, Java, TestNG, and the Page Object Model design pattern.

## Project Structure

```
janitri-automation-framework/
├── src/
│   ├── main/java/
│   │   └── pages/
│   │       └── LoginPage.java          # Page Object Model for login page
│   │                        
│   └── test/
│       ├── java/tests/
│       │   ├── BaseTest.java           # Base test class with setup/teardown
│       │   └── LoginPageTests.java     # Test cases for login functionality
│       └── resources/
│           └── testng.xml              # TestNG configuration
├── pom.xml                             # Maven dependencies and configuration
├── .gitignore                          # Git ignore file
└── README.md                           # This file
```

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Chrome, Firefox, or Edge browser
- Internet connection

## Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/S-YED/janitri-qa-automation.git
   cd janitri-automation-framework
   ```

2. **Install dependencies:**
   ```bash
   mvn clean install
   ```

3. **Run tests:**
   ```bash
   mvn test
   ```

## Test Cases Implemented

### Required Test Methods (As per assignment):

1. **testLoginButtonDisabledWhenFieldAreEmpty()**
   - Verifies that the login button is disabled when both User ID and Password fields are empty

2. **testPasswordMaskedbutton()**
   - Verifies password masking/unmasking functionality with eye icon toggle

3. **testInvalidLoginShowErrorMsg()**
   - Verifies that error messages are displayed when invalid credentials are used

### Additional Test Methods:

4. **testLoginPageElementsPresence()**
   - Verifies that all required login page elements are present and accessible

5. **testLoginWithBlankFields()**
   - Verifies proper handling when attempting to login with blank fields

## Key Features

- **Page Object Model (POM):** Clean separation of test logic and page elements
- **WebDriverManager:** Automatic driver management for different browsers
- **Multiple Locator Strategies:** Fallback locators for robust element identification
- **Notification Permission Handling:** Automatic handling of browser notification permissions
- **Cross-browser Support:** Chrome, Firefox, and Edge browser support
- **Detailed Logging:** Comprehensive console output for test debugging

## Configuration

### Browser Selection
By default, tests run on Chrome. To change browser, modify the `initializeDriver()` method in `BaseTest.java`:

```java
initializeDriver("brave"); 
```

### Timeouts
Adjust timeouts in `BaseTest.java`:
```java
private static final int IMPLICIT_WAIT_TIMEOUT = 10;
private static final int PAGE_LOAD_TIMEOUT = 30;
```

## Handling Notification Permissions

The framework automatically handles the Janitri website's notification permission requirement through:
- Chrome browser options pre-configuration
- JavaScript-based permission granting
- Automatic page refresh after permission handling

## Running Tests

### Command Line Options

**Run all tests:**
```bash
mvn test
```

**Run specific test class:**
```bash
mvn test -Dtest=LoginPageTests
```

**Run specific test method:**
```bash
mvn test -Dtest=LoginPageTests#testLoginButtonDisabledWhenFieldAreEmpty
```

**Run with different browser (if configured):**
```bash
mvn test -Dbrowser=firefox
```

## Test Reports

After running tests, reports are available in:
- `target/surefire-reports/` - TestNG HTML reports
- Console output with detailed test execution logs

## Troubleshooting

### Common Issues:

1. **Notification Permission Error:**
   - The framework automatically handles this, but if issues persist, ensure you're using Chrome with the provided configuration

2. **Element Not Found:**
   - The framework uses multiple locator strategies as fallbacks
   - Check if the website structure has changed

3. **Browser Driver Issues:**
   - WebDriverManager automatically downloads drivers, but ensure you have a stable internet connection

4. **Test Timeouts:**
   - Increase timeout values in `BaseTest.java` if tests are failing due to slow page loads

## Assignment Requirements Compliance

✅ **Framework Setup:**
- Java + Selenium WebDriver ✓
- Maven for dependency management ✓
- TestNG as test runner ✓
- Page Object Model implementation ✓
- BaseTest class for setup/teardown ✓

✅ **Required Locators:**
- User ID input ✓
- Password input ✓
- Login button ✓
- Password visibility toggle ✓

✅ **Required Test Methods:**
- testLoginButtonDisabledWhenFieldAreEmpty() ✓
- testPasswordMaskedbutton() ✓
- testInvalidLoginShowErrorMsg() ✓

✅ **Additional Features:**
- Notification permission handling ✓
- Multiple locator strategies for robustness ✓
- Comprehensive error handling ✓
- Detailed logging and reporting ✓
