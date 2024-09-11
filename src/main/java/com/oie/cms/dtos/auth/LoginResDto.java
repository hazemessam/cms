package com.oie.cms.dtos.auth;

import lombok.*;

@Data
@Builder
public class LoginResDto {
    private String authToken;
}
