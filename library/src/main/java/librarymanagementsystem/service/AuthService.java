package librarymanagementsystem.service;

import librarymanagementsystem.model.request.AuthRequest;
import librarymanagementsystem.model.request.UserRequest;
import librarymanagementsystem.model.response.UserResponse;

public interface AuthService {

    UserResponse register(UserRequest userRequest);
    String login(AuthRequest authRequest);
}
