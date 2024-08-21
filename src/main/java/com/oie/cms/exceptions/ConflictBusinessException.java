package com.oie.cms.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConflictBusinessException extends BusinessException {
    public ConflictBusinessException(String message) {
        super(message);
    }
}
