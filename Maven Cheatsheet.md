# 🚀 Maven Commands Cheat Sheet

## ⚡ Most Used Commands
| Command | Description |
|----------|--------------|
| `mvn clean` | Deletes the `target/` directory to ensure a fresh build. |
| `mvn clean install -U` | Cleans and installs the project locally, forcing dependency updates. |
| `mvn clean package -DskipTests` | Packages the project into a JAR/WAR while skipping tests. |
| `mvn spring-boot:run` | Runs the Spring Boot application directly. |
| `mvn test` | Runs all unit tests. |
| `mvn dependency:tree` | Displays the dependency hierarchy. |
| `mvn dependency:purge-local-repository` | Clears and re-downloads dependencies. |
| `mvn help:effective-pom` | Displays the final POM after inheritance and management. |
| `mvn package` | Compiles, tests, and packages your app into a distributable JAR/WAR. |
| `mvn -X clean install` | Cleans, installs, and enables debug logging. |

---

## 🧹 Build & Cleanup
| Command | Description |
|----------|--------------|
| `mvn clean` | Removes compiled files and `target/` folder. |
| `mvn clean install` | Cleans and installs the project into the local repository. |
| `mvn clean install -U` | Same as above but forces dependency updates. |
| `mvn clean package` | Cleans and builds the JAR/WAR. |
| `mvn clean package -DskipTests` | Packages the application without running tests. |
| `mvn clean verify` | Cleans, builds, tests, and verifies the project integrity. |

---

## ⚙️ Compile & Run
| Command | Description |
|----------|--------------|
| `mvn compile` | Compiles Java code under `src/main/java`. |
| `mvn test-compile` | Compiles tests under `src/test/java`. |
| `mvn spring-boot:run` | Runs the Spring Boot app without building a JAR. |
| `mvn exec:java -Dexec.mainClass=com.example.Main` | Runs a specific Java main class. |

---

## 📦 Dependency Management
| Command | Description |
|----------|--------------|
| `mvn dependency:tree` | Shows the dependency tree (helps find conflicts). |
| `mvn dependency:list` | Lists all resolved dependencies. |
| `mvn dependency:analyze` | Detects unused or undeclared dependencies. |
| `mvn dependency:purge-local-repository` | Deletes and re-downloads dependencies. |
| `mvn dependency:resolve` | Resolves and downloads dependencies. |
| `mvn dependency:copy-dependencies` | Copies dependencies to a specified directory. |

---

## 🧪 Testing
| Command | Description |
|----------|--------------|
| `mvn test` | Runs all unit tests. |
| `mvn test -Dtest=ClassName` | Runs tests from a specific test class. |
| `mvn test -Dtest=ClassName#methodName` | Runs a single test method. |
| `mvn verify -DskipTests` | Verifies build lifecycle while skipping tests. |
| `mvn clean test -DskipITs` | Skips integration tests (if configured separately). |

---

## 🚀 Packaging & Running JARs
| Command | Description |
|----------|--------------|
| `mvn package` | Compiles, tests, and packages into a JAR/WAR. |
| `mvn install` | Installs the packaged artifact into your local `.m2` repository. |
| `mvn deploy` | Deploys the artifact to a remote repository (e.g., Nexus). |
| `java -jar target/<filename>.jar` | Runs the packaged JAR. |
| `mvn spring-boot:build-image` | Builds a Docker image for your Spring Boot app. |

---

## 🧭 Project Info & Debugging
| Command | Description |
|----------|--------------|
| `mvn help:effective-pom` | Displays final effective POM. |
| `mvn help:active-profiles` | Lists active profiles. |
| `mvn -X clean install` | Runs Maven with detailed debug output. |
| `mvn versions:display-dependency-updates` | Shows available newer dependency versions. |
| `mvn dependency:tree -Dverbose` | Displays dependencies with versions and scopes. |

---

## 🔄 Profile-Specific Builds
| Command | Description |
|----------|--------------|
| `mvn clean package -Pdev` | Builds using the `dev` Maven profile. |
| `mvn spring-boot:run -Pprod` | Runs using the `prod` profile. |
| `mvn install -Ptest` | Installs with the `test` profile active. |

---

## 🧰 Miscellaneous
| Command | Description |
|----------|--------------|
| `mvn validate` | Validates project structure and configuration. |
| `mvn site` | Generates project documentation site. |
| `mvn clean compile exec:java` | Cleans, compiles, and runs the project. |
| `mvn dependency:sources` | Downloads all dependency source JARs. |
| `mvn dependency:go-offline` | Pre-downloads dependencies for offline builds. |
| `mvn license:format` | Formats or updates license headers (if plugin configured). |
