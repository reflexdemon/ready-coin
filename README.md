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
