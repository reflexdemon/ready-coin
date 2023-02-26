package io.vpv.readycoin.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class APIError {
    private Integer code;
    private String message;
}
