# Project Name

## Overview
This repository contains the source code for the **Project Name**. Below, you'll find a description of the project's structure, architecture, and instructions for setup and execution.

### Architectural Diagram
![Architectural Diagram](\ProjectPhotos\UMLclass.png)

---

## Building and Running the Application

### Prerequisites
Ensure you have the following dependencies installed:
- **Java** (version 21 or newer)
  - Download from: [Oracle JDK  21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) or an open-source option
- **Maven** (version 3.8 or newer)
  - Download from: [Apache Maven](https://maven.apache.org/download.cgi)
- **Node.js** (for Playwright tests)
  - Download from: [Node.js](https://nodejs.org/)
- **Junit-Jupiter (5.9.3 or newer)** (for Unit testing)
  - Download from: [JUnit](https://junit.org/)
- **IDE of your choice** (JetBrains IntelliJ IDEA recommended)
  - Download from: [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)

### Steps to Build and Run Application
1. (Terminal) Clone the repository:
   ```bash
   git clone https://github.com/FlyingOtta/SWE-3643-Project.git

2. (Terminal) Navigate to the project directory:
   ```bash
   cd SWE-3643-Project
   
3. (Terminal) Verify Java installation & access
   ```bash
   java -version

4. (Terminal) Verify Node.js installation & access
   ```bash
   node -v
   
5. (Terminal) Verify npm installation and access
   ```bash
   npm -v

6. (Terminal) Verify Maven installation & access
   ```bash
   mvn -version
   
7. (Terminal) Install Playwright dependencies using npm
   ```bash
   npm install @playwright/test
   
8. (Terminal) Verify Playwright installation and access
   ```
   npx playwright --version
   
9. (Terminal) Download Junit-Jupiter using MVN command
   ```bash
   mvn dependency:copy -Dartifact=org.junit.jupiter:junit-jupiter:5.9.3 -DoutputDirectory=.

10. (XML) Add the junit dependency to your xml file
    ```XML
    <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.9.3</version>
    <scope>test</scope>
    </dependency>

11. (Terminal) Build your project using maven. Maven will handle Spring Boot & Initializer & its dependencies
   ```bash
   mvn clean install
   ```
12. (Terminal) Run the Spring-Boot application using Maven
   ```bash
   mvn spring-boot:run
```
13. In the browser of your choice, open your browser and navigate to port 8080 to view the application
   ```text
   http://localhost:8080
```

### Steps to Run Testing

1. (Terminal) Once you've verified the application is running, unit tests can be run by inputting:
   ```bash
   mvn test
   
2. (Terminal) E2E Playwright tests can be run by inputting:
   ```bash
   npx playwright test

### Testing Proof / Screenshots

1. All Passing Unit Tests![UnitTestsPassing](C:\Users\gavin\IdeaProjects\Calculator\ProjectPhotos\Screenshot2024-12-01000602.png)
2. Unit Tests Achieving 100% Coverage![UnitTesting100%Coverage](C:\Users\gavin\IdeaProjects\Calculator\ProjectPhotos\Screenshot2024-12-01001549.png)
3. All Passing Playwright Tests ![PlaywrightTestsPassing](C:\Users\gavin\IdeaProjects\Calculator\ProjectPhotos\Screenshot2024-12-01002236.png)

### (YouTube) Link to Presentation Video

https://youtu.be/FDpYyI6cG9A
