package com.oie.cms.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundBusinessException extends BusinessException {
    public NotFoundBusinessException(String message) {
        super(message);
    }
}
