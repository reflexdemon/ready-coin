package io.vpv.readycoin.api;

import io.vpv.readycoin.domain.APIError;
import io.vpv.readycoin.exception.ServiceException;
import io.vpv.readycoin.exception.UserInputError;
import io.vpv.readycoin.service.CoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/coin")
public class RestCoinService {
    private CoinService coinService;

    public RestCoinService(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping(value = "/change/{amount}", produces = "application/json")
    public Map<Double, Integer> findDenominationsForGivenBill(@PathVariable(name = "amount") Integer amount) {
        return coinService.findDenominationsForGivenBill(amount);
    }

    @ExceptionHandler(UserInputError.class)
    public ResponseEntity<APIError> handleUserInputError(UserInputError error) {
        return new ResponseEntity<>(APIError.builder()
                .message(error.getMessage())
                .code(400)
                .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<APIError> handleServiceException(ServiceException error) {
        return new ResponseEntity<>(APIError.builder()
                .message(error.getMessage())
                .code(500)
                .build(), HttpStatus.SERVICE_UNAVAILABLE);
    }

}
