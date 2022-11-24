# Oasys Africa.
The primary goal of this project is to come up with the system for churches where members can you get interact with each other share bible verses as well
as providing a repository of their sermons and songs. Members can update their location, personal infomation and the current church they attend. Besides,
the church admins should create events futher listed to membersin the church/have access the system.

### Project Tech Stack
- [Spring Framework](https://spring.io/projects/spring-framework). Provides a comprehensive programming and configuration model for modern Java-based enterprise applications - on any kind of deployment platform.
  has support for both Java and Kotlin.
- [Springboot](https://spring.io/projects/spring-boot). A java based framework for building production grade backend APIs. It eliminates the boilerplate configurations required for setting up a Spring application
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa). Fastens the implementation of the data access layer of the project through interfaces written
  with which spring generated the implementation automatically.
- [Spring Security](https://spring.io/projects/spring-security). An interesting-to-use access control and authentication framework for java application. It provides authorization filters in a proper
  and more understandable manner. You can also integrated this using [OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/).
- [Postgresql](https://www.postgresql.org/). A powerful open source relational database management system. [They](https://www.postgresql.org/0), claim is has a good perfomence reputation and reliability.
- [AWS S3](https://aws.amazon.com/s3/). Amazon Simple Storage Service (Amazon S3) is an object storage service offering industry-leading scalability, data availability, security, and performance.
  We will be using this to store the audio files, profile images and other images in the project. Spring has an sdk for this, so minimal configurations are required.
- [Lombok](https://projectlombok.org/). Lombok offers various annotations aimed at replacing Java code that is well known for being boilerplate, repetitive, or tedious to write.
- [Maven](https://maven.apache.org/). Maven is a project management and comprehension tool that provides developers a complete build lifecycle framework. Through this we package our executable
  project into a `jar` or `war` file.
- [Model Mapper](http://modelmapper.org/). Automatically determinES how one object model maps to another, based on conventions, in the same way that a
  human would - while providing a simple, refactoring-safe API for handling specific use cases. We are using this to prevent exposing our data models to the
  users.
- [JWT](https://www.baeldung.com/spring-security-oauth-jwt). We are using this to implment token based authentiction. A user gets a token pair after loging in,
  the `access token` and `refresh token`.

### Contributing to the project
- Clone the project from the link `https://github.com/jerimkaura/oasis.git`.
- Create and checkout your branch from development not main (this is meant for production).
- Treat everything you are working on as a feature that will be merged to the development branch. So everytime you are working on something,
  create a branch with the feature name i.e login-endpoint or profile-picture-update
- Once done with the feature, review it before creating a pull request Note: Always pull before pushing.

### Support
If you find this project useful, please leave a star ⭐️  on the top right, create an issue in the [issue section](https://github.com/jerimkaura/oasis/issues)
or launch a [pull request here](https://github.com/jerimkaura/oasis/pulls). Feel free to contribute to this project in any way but if you have time, review my code,
and let us work together. You feedback will be highly appreciated.
