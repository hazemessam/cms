package com.oie.cms.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnProcessableBusinessException extends BusinessException{
    public UnProcessableBusinessException(String message) {
        super(message);
    }
}
