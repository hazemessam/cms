package com.oie.cms.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnAuthorizedBusinessException extends BusinessException {
    public UnAuthorizedBusinessException(String message) {
        super(message);
    }
}
