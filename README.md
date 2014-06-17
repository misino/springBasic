springBasic
===========

Basic application created in Spring Framework.
It can be used as starting point for new applications. There are solved some issues which needs to be solved while developing web app from the scratch. Developers can grad this code and customize it to their needs.

##Features
- Templates (Thymeleaf) - for web content and email messages
- Authentication, registering users, resetting forgotten password
- Email sending
- Exception Handling (No ugly errors displayed on the page)
- Internationalization
- Database migrations (Flyway), database configuration

##Requirements
==============
- Java 7
- application server (i.e. Apache Tomcat 8)

##Configuring
Before building and deploying .war file, you need to set some values in .properties files which are located in resources. Configure database connection and SMTP connection.
Configure database connection for Flyway in build.gradle

After connections are set, open command line, go to root directory and 

##Building
In command line, go to root directory of this project.
Run `./gradlew build`

WAR file can be found in `build/libs` directory.

##Database migrations
Database can be easily updated with updates scripts which are located in `src/main/resources/db/migration/`.
For automatic database update, run command `./gradlew flywayMigrate -i` from root directory.
If you dont know how to write or use these sql update scripts, learn more on http://flywaydb.org/
