package io.vpv.readycoin.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Coin Service API", description = "This API endpoint has the collection on Services that is used to get the change for bill.")
public class RestCoinService {
    private CoinService coinService;

    public RestCoinService(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping(value = "/change/{amount}", produces = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponse(responseCode = "400", description = "When there is invalid Bill amount sent")
    @ApiResponse(responseCode = "503", description = "When there is not enough coins to return")
    public Map<Double, Integer> findDenominationsForGivenBill(@PathVariable(name = "amount") Integer amount) {
        return coinService.findDenominationsForGivenBill(amount);
    }

    @ExceptionHandler(UserInputError.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<APIError> handleUserInputError(UserInputError error) {
        return new ResponseEntity<>(APIError.builder()
                .message(error.getMessage())
                .code(400)
                .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<APIError> handleServiceException(ServiceException error) {
        return new ResponseEntity<>(APIError.builder()
                .message(error.getMessage())
                .code(500)
                .build(), HttpStatus.SERVICE_UNAVAILABLE);
    }

}
