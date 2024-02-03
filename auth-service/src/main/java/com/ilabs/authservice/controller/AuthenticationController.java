package com.ilabs.authservice.controller;

import com.ilabs.authservice.dto.AuthenticateRequest;
import com.ilabs.authservice.dto.AuthenticationResponse;
import com.ilabs.authservice.dto.RegisterRequest;
import com.ilabs.authservice.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate user")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Order created"),
//            @ApiResponse(responseCode = "400", description = "Invalid input data"),
//            @ApiResponse(responseCode = "500", description = "Internal server error")
//    })
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticateRequest authRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }
}
