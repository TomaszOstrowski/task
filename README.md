Plan3 - Assignment: Java / Backend
==================================

## About the assignment
This assignment consist of two tasks and a set of belonging questions. The first part is a pure development task while the second part is to deploy your code to Heroku.

## Intention
The assignment aims primarily to provide us with a freshly developed code base that you've authored. We'll look at your solution in combination with your answers to the questions but primarily it serves as a foundation for a discussion between us. We can discuss the solution with you in order to get a picture of how you work, how you've reasoned.
But the assignment also serves as an example of code that you might write, release and deploy as part of our team. This way we hope to convey a image of how you and we work in order for us to figure out if we might be a good match for each other. As much as this assignment is tailored to give us a view of who you are as programmer we also try to see it as a way for us to show you who we are and how we work.

An remember: **there are no hard rights or wrongs.** The rules outlined below are mainly to narrow the scope of the assignment so that it's easier to compare different solutions.

## Execution
* You're allowed to:
    * Email us at dev@plan3.se and ask questions!
    * Use, add and remove any libraries and frameworks you'd like as long as you manage them with Maven
    * Change the indention and/or reformat the code, as long as all of the code is consistently formatted. All or nothing.
    * Take notes of and/or version the code during your work.
    * Question and criticize the assignment! (too hard? too easy? too weird? not consistent?)
    * Break all or any of the rules if you get stuck. We'd rather receive a partial solution or a solution that doesn't conform to the rules than no solution. But if you choose to deviate from the rules we require that you document how and why you deviated from the instructions.
* You are not required to:
    * Document your solution other than answering the questions in this document
    * Save data persistently, it's sufficient if data is stored in memory while the solution is running

And remember, this is meant to be fun :)

### Preparations
* Install [Maven](http://maven.apache.org), see also:
    * [installation](http://maven.apache.org/download.html#Installation)
    * [getting started](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html).
* Install [Git](http://git-scm.com), see also:
    * [Try Git by Github](http://try.github.com/)
* If you're using Eclipse run `mvn eclipse:eclipse` in the root directory to create a project setup for [Eclipse](http://www.eclipse.org/)
* Optional:
    * Install [Heroku Toolbelt](https://toolbelt.heroku.com/)

### Testing
* To run the unit tests: `mvn test`
* To start the server locally: `mvn exec:java`, it'll be available at http://127.0.0.1:8080/
* To package and run the packaged solution the solution:
    * `mvn clean package`
    * `java -jar target/java-backend-recruitment-1.jar server`

----

## The assignment

### Task 1
* Implement the code that's missing so that the solution compiles and all tests passes and adjust the code according to a set of rules:
    * Implement the code that's missing so that the solution compiles
    * Adjust the existing code so that the tests pass
    * Write unit tests for your implementation of `PersonStorage`
       * Whether you write the tests before or after the code is up to you
    * Adjust the code to these rules: https://dl.dropboxusercontent.com/u/697196/plan3-rekrytering-regler-del-2-en.txt
* Questions:
    1. What effect did applying the rules have on your code?
    2. Anything that became better?
    3. Anything that became worse?
    4. What's your impression and understanding of the frameworks and libraries we've used here?

### Task 2
* Register an Heroku account, setup a new application and deploy your solution:
    * It must run as a process of type `web`
    * It must use Java 7 
>> TOOS: use jdk7 features
* Questions:
    1. How do you feel about shipping and deploying code yourself?
    2. Who do you think should be responsible for code running in production?

## Expectations
We expect that you supply us with
* ...a zip file of your final solution
* ...the URL to your Heroku application
* ...a plain text document with your answers to the questions
    * Preferably placed in the zip file together with your code
