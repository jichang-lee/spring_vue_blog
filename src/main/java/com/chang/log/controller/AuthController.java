package com.chang.log.controller;

import com.chang.log.request.user.LoginRequest;
import com.chang.log.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest req){
        String token = this.authService.login(req);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
