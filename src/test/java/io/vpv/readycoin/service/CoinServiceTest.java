package io.vpv.readycoin.service;

import io.vpv.readycoin.ReadyCoinApplication;
import io.vpv.readycoin.exception.ServiceException;
import io.vpv.readycoin.exception.UserInputError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class CoinServiceTest extends ReadyCoinApplication {

    @Autowired
    CoinService coinService;

    @BeforeEach
    void setUp() {
        coinService.reset(); // Reset the coin balance
    }

    @Test
    void emptyCoinBalanceAllHappyPath() {
        Map<Double, Integer> step1 = coinService.findDenominationsForGivenBill(10);
        assertEquals(40, step1.get(0.25), "Expect 40 $0.25 returned for $10");
        Map<Double, Integer> step2 = coinService.findDenominationsForGivenBill(20);
        assertEquals(60, step2.get(0.25), "Expect 60 $0.25 returned for $15");
        assertEquals(50, step2.get(0.10), "Expect 50 $0.10 returned for $5");

        Map<Double, Integer> step3 = coinService.findDenominationsForGivenBill(10);
        assertEquals(null, step3.get(0.25), "Expect 0 $0.25 returned for $0");
        assertEquals(50, step3.get(0.10), "Expect 50 $0.10 returned for $5");
        assertEquals(100, step3.get(0.05), "Expect 50 $0.10 returned for $5");

    }

    @Test
    void checkForServiceExceptionForInsufficientCoins() {
        emptyCoinBalanceAllHappyPath();
        assertThrows(ServiceException.class, () -> coinService.findDenominationsForGivenBill(10));
    }

    @Test
    void checkForUserInputErrorWithInvalidInput() {
        assertThrows(UserInputError.class, () -> coinService.findDenominationsForGivenBill(11));
    }
}