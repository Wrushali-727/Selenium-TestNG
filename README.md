# Selenium + TestNG — Page Object Model & Page Factory

A minimal, runnable UI test framework demonstrating the Page Object Model (POM) and
Page Factory patterns against the public practice site
[SauceDemo](https://www.saucedemo.com/).

## Stack
- Java 17
- Selenium 4.25 (Selenium Manager auto-downloads the browser driver — no manual setup)
- TestNG 7.10
- Maven

## Project layout
```
Selenium-Java-TestNG
├── pom.xml                     # dependencies + Surefire (runs testng.xml)
├── testng.xml                  # TestNG suite definition
└── src
    ├── main/java/com/example/qa
    │   ├── base
    │   │   ├── BasePage.java        # PageFactory.initElements + wait helpers
    │   │   └── DriverFactory.java   # WebDriver creation (chrome/firefox/edge)
    │   └── pages
    │       ├── LoginPage.java       # @FindBy elements + login actions
    │       └── ProductsPage.java    # @FindBy elements + product queries
    └── test/java/com/example/qa/tests
        ├── BaseTest.java            # @BeforeMethod/@AfterMethod driver lifecycle
        └── LoginTest.java           # @Test cases (valid, locked-out, invalid)
```

## How the patterns show up
- **Page Object Model** — each page is a class exposing behavior (`loginAs`, `getTitle`),
  never raw locators, to the tests.
- **Page Factory** — `BasePage` calls `PageFactory.initElements(driver, this)` in its
  constructor, and each page declares elements with `@FindBy` annotations.

## Run it
```bash
mvn clean test
```

Choose a browser or run headless:
```bash
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=chrome -Dheadless=true
```

Run a single test class:
```bash
mvn clean test -Dtest=LoginTest
```

## Prerequisites
- JDK 17+ (`java -version`)
- Maven 3.8+ (`mvn -version`)
- A local browser install (Chrome by default). Selenium Manager fetches the driver binary.

## Test coverage
| Test | What it checks |
|------|----------------|
| `validLoginShowsProductsPage` | Valid login reaches Products page with 6 items |
| `lockedOutUserSeesError`      | Locked-out user is blocked with an error |
| `invalidLoginIsRejected`      | Data-driven: bad password / missing username / missing password |
