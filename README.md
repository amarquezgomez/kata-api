# Kata-api testing in Java

- [Java 24](https://www.oracle.com/java/)
- [Maven](https://maven.apache.org/)
- [JUnit 5](https://junit.org/junit5/)
- [Cucumber](https://cucumber.io/)
- [Rest-Assured](https://rest-assured.io/)
- [Jackson](https://github.com/FasterXML/jackson)

Target API: [automationintesting.online](https://automationintesting.online/)

---

##  📁 Project Structure
```
kata-api/
├── pom.xml # Maven build + dependencies
├── .gitignore # Ignore build artifacts & IDE files
├── README.md # Project documentation
├── src/
│ ├── main/java/com/amgqa/kata/ # Source code
│ │ ├── api/ # API client classes
│ │ ├── model/ # DTOs for request/response payloads
│ │ └── util/ # Utilities 
│ └── test/java/com/amgqa/kata/ # Tests
│ │  ├── config/ # Rest-Assured config
│ │  ├── steps/ # Cucumber step definitions
│ └── resources/
│    ├── features/ # Cucumber feature files
│    └── schemas/ # JSON Schemas
```

---

## ⚡ Prerequisites

- **Java 24** → `java -version`
- **Maven 3.9.11** → `mvn -version`
- **Git**

---

## ▶️ Running Tests

Run all tests with Maven:

```
mvn -Dapi.base=https://automationintesting.online test
mvn -X test
mvn test -Dcucumber.filter.tags="@smoke"