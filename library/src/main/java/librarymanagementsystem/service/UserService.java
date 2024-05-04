package librarymanagementsystem.service;

import org.springframework.data.domain.Page;

import librarymanagementsystem.entity.User;
import librarymanagementsystem.entity.UserCredential;
import librarymanagementsystem.model.request.UserRequest;

public interface UserService{

    User register(UserRequest userRequest, UserCredential userCredential);
    User getUserById(String id);
    User updateUser(User user);
    void deleteUserById(String id);
    Page<User> getAll(Integer page, Integer size);

}
