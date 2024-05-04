package librarymanagementsystem.service;



import org.springframework.data.domain.Page;

import librarymanagementsystem.entity.User;
import librarymanagementsystem.model.request.UserRequest;
import librarymanagementsystem.model.response.UserResponse;

public interface UserService {

    UserResponse register(UserRequest userRequest);
    Page<User> getAllUser(Integer page, Integer size);
    User getUserById(String id);
    User updateUser(User user);
    void deleteUserById(String id);

}
