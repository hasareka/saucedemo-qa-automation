# QA Automation Framework for SauceDemo

A Selenium-based automation testing framework developed using Java, TestNG, Maven, and the Page Object Model (POM) design pattern.

This framework automates the core functionality of the SauceDemo web application, including Login, Inventory, Cart, and Checkout workflows.

---

## Features

- Selenium WebDriver
- Java
- TestNG
- Maven
- Page Object Model (POM)
- Explicit Waits
- Extent Reports
- Automatic Screenshot Capture on Test Failure
- Reusable Page Objects
- Cross-page navigation
- End-to-End Purchase Flow

---

## Technologies Used

- Java 18
- Selenium WebDriver 4.12.1
- TestNG 7.8.0
- Maven
- WebDriverManager
- ExtentReports 5

---

## Project Structure

```
QA_Automation
│
├── src
│   ├── main
│   └── test
│       └── java
│           ├── pages
│           │     ├── LoginPage.java
│           │     ├── InventoryPage.java
│           │     ├── CartPage.java
│           │     ├── CheckoutInfoPage.java
│           │     ├── CheckoutOverviewPage.java
│           │     └── CheckoutCompletePage.java
│           │
│           ├── tests
│           │     ├── BaseTest.java
│           │     ├── LoginTests.java
│           │     ├── InventoryTests.java
│           │     ├── CartTests.java
│           │     └── CheckoutTests.java
│           │
│           └── utils
│                 ├── ExtentManager.java
│                 ├── ScreenshotUtils.java
│                 └── TestListener.java
│
├── test-output
│     ├── ExtentReport.html
│     └── screenshots
│
├── pom.xml
└── README.md
```

---

## Test Coverage

### Login

- Valid Login
- Invalid Username
- Invalid Password
- Locked Out User
- Empty Username
- Empty Password
- Empty Credentials
- Problem User
- Performance Glitch User

### Inventory

- Verify Inventory Page
- Verify Product Count
- Add Product to Cart
- Remove Product from Cart
- Verify Cart Badge
- Sort Products
- Verify Product Details

### Cart

- Verify Added Item
- Remove Item
- Continue Shopping
- Checkout Navigation

### Checkout

- Successful Checkout
- Missing First Name Validation
- Missing Last Name Validation
- Missing Postal Code Validation
- Cancel Checkout
- Verify Total Price
- Complete Purchase

---

## Reports

The framework generates an HTML Extent Report after execution.

Features include:

- Pass/Fail Status
- Execution Time
- Exception Stack Trace
- Automatic Screenshot for Failed Tests

Report Location

```
test-output/ExtentReport.html
```

---

## Running the Tests

Clone the repository

```
git clone <repository-url>
```

Open the project using IntelliJ IDEA.

Run the TestNG suite or execute any test class individually.

The Extent Report will be generated automatically under:

```
test-output/
```

---

## Current Status

- 31 Automated Test Cases
- Page Object Model
- Extent Reporting
- Screenshot Capture
- Modular Test Framework

---

## Future Enhancements

- Config.properties
- DriverFactory
- Cross Browser Execution
- Data Driven Testing
- Jenkins CI/CD
- GitHub Actions
- Log4j Logging

---

## Author

**Thathsarani Hasareka**

University of Moratuwa

BSc (Hons) in Information Technology & Management