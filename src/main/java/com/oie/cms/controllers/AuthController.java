package com.oie.cms.controllers;

import com.oie.cms.auth.AuthUser;
import com.oie.cms.auth.JwtService;
import com.oie.cms.dtos.auth.LoginReqDto;
import com.oie.cms.dtos.auth.LoginResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("login")
    public ResponseEntity<LoginResDto> login(@Valid @RequestBody LoginReqDto loginReqDto) {
        var authReq = new UsernamePasswordAuthenticationToken(
                loginReqDto.getEmail(), loginReqDto.getPassword());
        var authRes = authenticationManager.authenticate(authReq);
        var authToken = jwtService.generateToken((AuthUser) authRes.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK)
                .body(LoginResDto.builder().authToken(authToken).build());
    }
}
