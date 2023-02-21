# REST Spring Template

This repository provides a starting point for building RESTful Spring Boot applications.

## How to start

1. Install Java 11 (or higher) and Maven 3.8 (or higher).
2. Set it as the default Java version.
3. Clone the repository and navigate to the project directory.

   ```bash
   git clone https://github.com/scastd/rest-spring-template.git
   cd rest-spring-template
   ```

4. Run the application.

   ```bash
   mvn spring-boot:run
   ```
   #### Note
   The application will be available at http://localhost:8080 (by default).
   At first, it will not connect to any database until you configure the connection
   properties in the `application.properties` file.

## Configuration

The application is configured using the `application.properties` file, which is located in the `src/main/resources` directory.

Also, some environment variables must be provided in order for the application to work properly and securely (see the table below).

|      Name      |         Description         |
|:--------------:|:---------------------------:|
|   `DB_HOST`    |  The host of the database.  |
|   `DB_NAME`    |  The name of the database.  |
|   `DB_USER`    |  Username of the database.  |
| `DB_PASSWORD`  |  Password of the database.  |
| `TOKEN_SECRET` | Secret to build JWT tokens. |

To be able to use these environment variables, you need to set them in a `.env` file, inside a
new directory called `env` in the root directory of the project and following the format specified below,
without comments.
Then, you are able to run the application using the [run_app.sh](scripts/run_app.sh) script.

```dotenv
DB_HOST=value
DB_NAME=value
DB_USER=value
DB_PASSWORD=value
TOKEN_SECRET=value
```

#### Important note

You should not commit these files to the repository, because they contain sensitive information.

## Project Structure

It contains several packages that are useful for building this type of applications:

* `config` - Contains the configuration classes for the application.
	* `logging` - Contains the configuration classes for the logging system.
	* `security` - Security configuration classes that are used to configure the authentication and authorization.
		* `filter` - Contains the classes that are used to filter the requests.
* `controllers` - Contains the controllers for the application.
* `domain` - Contains the domain classes for the application.
	* `model` - Contains the classes that represent the model that maps the database tables.
	* `repository` - Contains the classes that are used to access the database.
	* `service` - Contains the classes that are used to implement the business logic.
* `exceptions` - Exception classes used throughout the application.
* `json` - Contains the classes that are used to serialize and deserialize the JSON objects.
	* `modules` - Contains the classes that configure all the serializers and deserializers for each module.
	* `serializers` - Contains the classes that are used to serialize the JSON objects.
* `net` - Contains the classes that are used to improve the communication between the client and the server.
	* `http` - Provides classes that improve the handling of HTTP requests and responses.
* `router` - Contains a class that establishes all the routes of the application as constants.
* `utils` - Utility classes.

## Important note
Several classes have been created to help you build your application, but they should be modified to suit your needs
because they are too simple to be used in a real application. In addition, some of them are not used in the application,
but they are provided as examples of how to implement them.

## Technologies used

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Security](https://spring.io/projects/spring-security)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [Spring Web](https://spring.io/projects/spring-framework)
* [Lombok](https://projectlombok.org/)
* [MapStruct](https://mapstruct.org/)
* [Logback](https://logback.qos.ch/)
* [Slf4j](https://www.slf4j.org/)
* [jjwt](https://github.com/jwtk/jjwt) - JSON Web Token for Java and Android.
* [Yavi](https://yavi.ik.am/) - Java validation library.
* [Gson](https://github.com/google/gson) - Json serialization/deserialization library for Java.
* [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/)
* [Apache Commons IO](https://commons.apache.org/proper/commons-io/)

## Contributing

If you want to contribute to this project, you can do it by forking the repository and creating a pull request.
All contributions are welcome.
