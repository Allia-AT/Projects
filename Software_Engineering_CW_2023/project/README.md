# Films4You Database Analyser App

This project complements Film4You's production system by providing database analysis functionality.

## Project Layout
This Maven project has the following structure:
- `requirement-set.txt` contains the requirements from the client.
- `pom.xml` contains project configuration, such as dependencies and their configuration.
- `.gitlab-ci.yml` contains the CI/CD configuration.
- `checkstyle_supressions.xml` contains Checkstyle ignore rules for tests, as only docstrings are needed for source (not test) files.
- `src/com.films4you.[PACKAGE]` contains source code for `[PACKAGE]`.
- `test/com.films4you.[PACKAGE]` contains unit testing for `[PACKAGE]`.
- `com.films4you.main` contains shared code used across the project.
- `.gitignore` ignore files in git that should not be added to the repository.

Each requirement has its own package and a single file called `Requirement.java` which implements the `com.films4you.RequirementInterface`; other classes for the each requirement should be put in the same as its `Requirement.java` if appropriate.

Built files will be put in `target`.

## Project Dependencies
This project uses the Maven build system to manage its dependencies and build cycle.
If running without Maven, you need the following:
- `org.xerial.sqlite-jdbc` (3.41), which provides a JDBC connector for SQLite files;
- `junit` (4.13.2) for unit testing;
- `com.puppycrawl.tools.checkstyle` (10.8) to adhere to company's style guide; and,
- `org.checkerframework.checker-qual` (3.31) for nullness cheking and annotations.

`com.googlecode.maven-download-plugin` is used by Maven to download and set up the test environment (downloading the test database, `testing.db`). This testing database will need to be placed in the project's root directory if not using this tool when setting up tests.

The project targets **Java 11**.

## Project Timeline
The initial database and project configuration has been completed.

However, no client requirements have been implemented yet. Still to do are:
1. Requirement 1;
2. Requirement 2;
3. Requirement 3;
4. Requirement 4; and,
5. their tests.

Release v1 is due 15 May at 16:00.
**TODO: publish on this repository for CTO review by this date.**

