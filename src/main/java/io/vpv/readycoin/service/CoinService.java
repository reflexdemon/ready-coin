package io.vpv.readycoin.service;

import io.vpv.readycoin.exception.ServiceException;
import io.vpv.readycoin.exception.UserInputError;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toConcurrentMap;
import static java.util.stream.Collectors.toList;

@Service
public class CoinService {
    private static final Double[] DENOMINATIONS = {0.01, 0.05, 0.10, 0.25};
    private static final Integer[] BILLS = {1, 2, 5, 10, 20, 50, 100};

    private static final Map<Double, Integer> KIOSK_CHANGE = Stream.of(DENOMINATIONS)
                .collect(toConcurrentMap(i -> i, i -> 100)); // Got Concurrent Map for Thread safety

    public synchronized void reset() { // synchronized for thread safety
        KIOSK_CHANGE.keySet()
                .stream()
                .forEach(k -> KIOSK_CHANGE.put(k, 100));
    }
    public Map<Double, Integer> findDenominations(Integer amount) {

        if (!Stream.of(BILLS).anyMatch( b -> b == amount)) {
            throw new UserInputError("Enter valid Bill amount.");
        }

        // Does the store have enough balance?
        Double balance = getBalance();
        if (balance < amount) {
            throw new ServiceException("Insufficient Fund for amount = " + amount);
        }
        List<Integer> sortedDenominations = getSortedDenominations();
        Map<Double, Integer> result = new HashMap<>();
        Integer amountInCents = amount * 100; // Convert to cents for easy Integer calculations
        for (Integer denomination : sortedDenominations) {
            Integer num = amountInCents / denomination;
            Double denominationInDollar = denomination.doubleValue() / 100.0;
            Integer remainingCoins = KIOSK_CHANGE.get(denominationInDollar);
            if (remainingCoins > num) {
                amountInCents -= num * denomination;
                result.put(denominationInDollar, num );
                KIOSK_CHANGE.put(denominationInDollar, remainingCoins - num);
            } else {
                amountInCents -= remainingCoins * denomination;
                result.put(denominationInDollar, remainingCoins );
                KIOSK_CHANGE.put(denominationInDollar, 0);
            }

            if (amountInCents <= 0) break; // Check if we have completed the denominations
        }


        return result;
    }

    private List<Integer> getSortedDenominations() {
        return Stream.of(DENOMINATIONS)
                .sorted(reverseOrder()) // Making sure Highest denomination is coming first
                .filter(d -> KIOSK_CHANGE.get(d) > 0) // Only have denominations that has the coins
                .map(d -> d * 100) // Convert to Cent
                .map(d -> d.intValue()) // Convert to int
                .collect(toList());
    }

    private double getBalance() {
        return KIOSK_CHANGE.entrySet()
                .stream()
                .mapToDouble(d -> d.getKey() * d.getValue())
                .sum();
    }
}
