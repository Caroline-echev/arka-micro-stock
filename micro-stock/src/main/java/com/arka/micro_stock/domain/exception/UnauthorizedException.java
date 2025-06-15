package com.arka.micro_stock.domain.exception;

import com.arka.micro_stock.domain.exception.error.CommonErrorCode;
import com.arka.micro_stock.domain.exception.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends BusinessException {

    private static final ErrorCode DEFAULT_ERROR_CODE = CommonErrorCode.UNAUTHORIZED;

    public UnauthorizedException(String message) {
        super(DEFAULT_ERROR_CODE, message);
    }

    public UnauthorizedException() {
        super(DEFAULT_ERROR_CODE, "Unauthorized access");
    }
}
