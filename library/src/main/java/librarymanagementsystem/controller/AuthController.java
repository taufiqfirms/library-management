package librarymanagementsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import librarymanagementsystem.model.request.AuthRequest;
import librarymanagementsystem.model.request.UserRequest;
import librarymanagementsystem.model.response.LoginResponse;
import librarymanagementsystem.model.response.UserResponse;
import librarymanagementsystem.model.response.WebResponse;
import librarymanagementsystem.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        String token = authService.login(authRequest);
        LoginResponse<String> response = LoginResponse.<String>builder()
        .message("Success login")
        .token(token)
        .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequest userRequest){
        UserResponse userResponse = authService.register(userRequest);
        WebResponse<UserResponse> response = WebResponse.<UserResponse>builder()
        .status(HttpStatus.CREATED.getReasonPhrase())
        .message("Success register new User")
        .data(userResponse)
        .build();
        return ResponseEntity.ok(response);
    }

}
