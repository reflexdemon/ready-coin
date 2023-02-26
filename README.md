# Ready Coin
## Problem
Create a Spring Boot service that exposes a REST endpoint that allows a user to request change for a given bill. The service should assume there is a finite number of coins, and it should update that number on each request

### Requirements

* Accepted bills are `(1, 2, 5, 10, 20, 50, 100)`, nothing else
* Available coins are `(0.01, 0.05, 0.10, 0.25)`, nothing else
* Start with 100 coins of each type
* Change should be made by utilizing the least amount of coins
* API should validate bad input and respond accordingly
* Service should respond with an appropriate message if it does not have enough coins to make change
* The service should maintain the state of the coins throughout the transactions
* Have at least 1 test
* Upload your code to Github and come to interview prepared to walk through code

### Notes:
Example of a possible response of the API for a $2 bill
```json
{
    "0.25": 2,
    "0.10": 5,
    "0.05": 20
}

```

The design, the URL, and everything else is not defined. Use your creativity.

## Testing Instructions

Prerequisites:
* JDK 17
* Terminal

Step 1: Clone the repository to your local using below command.
```shell
$ git clone git@github.com:reflexdemon/ready-coin.git
$ cd ready-coin
```
Step 2: Build, Compile and Test the application
```shell
$ ./gradlew clean test assemble
```
Sample Output
```text
$ ./gradlew clean test assemble

> Task :test

  io.vpv.readycoin.ReadyCoinApplicationTests

    ✔ contextLoads()

  io.vpv.readycoin.api.RestCoinServiceTest

    ✔ emptyCoinBalanceAllHappyPath()
    ✔ handleUserInputError()
    ✔ handleServiceException()

  io.vpv.readycoin.service.CoinServiceTest

    ✔ checkForUserInputErrorWithInvalidInput()
    ✔ emptyCoinBalanceAllHappyPath()
    ✔ checkForServiceExceptionForInsufficientCoins()

  7 passing (3.6s)


BUILD SUCCESSFUL in 5s
9 actionable tasks: 9 executed
```
Step 3: Start the Spring Boot Server
```shell
$ java -jar build/libs/ready-coin-0.0.1-SNAPSHOT.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.0.3)

2023-02-25T22:22:04.541-05:00  INFO 38463 --- [           main] io.vpv.readycoin.ReadyCoinApplication    : Starting ReadyCoinApplication v0.0.1-SNAPSHOT using Java 17.0.6 with PID 38463 (/Users/reflex/dev/github/reflexdemon/ready-coin/build/libs/ready-coin-0.0.1-SNAPSHOT.jar started by reflex in /Users/reflex/dev/github/reflexdemon/ready-coin)
2023-02-25T22:22:04.543-05:00  INFO 38463 --- [           main] io.vpv.readycoin.ReadyCoinApplication    : No active profile set, falling back to 1 default profile: "default"
2023-02-25T22:22:05.034-05:00  INFO 38463 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2023-02-25T22:22:05.040-05:00  INFO 38463 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2023-02-25T22:22:05.040-05:00  INFO 38463 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.5]
2023-02-25T22:22:05.088-05:00  INFO 38463 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2023-02-25T22:22:05.089-05:00  INFO 38463 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 521 ms
2023-02-25T22:22:05.546-05:00  INFO 38463 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2023-02-25T22:22:05.556-05:00  INFO 38463 --- [           main] io.vpv.readycoin.ReadyCoinApplication    : Started ReadyCoinApplication in 1.196 seconds (process running for 1.475)

```
Note: You should see `Started ReadyCoinApplication` on your screen to confirm that the server has started successfully

Step 4: Access Swagger UI on your browser  to start Manual Testing. URL: `http://localhost:8080/swagger-ui/index.html`



