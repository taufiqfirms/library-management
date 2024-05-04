package librarymanagementsystem.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import librarymanagementsystem.entity.User;
import librarymanagementsystem.model.request.UserRequest;
import librarymanagementsystem.model.response.UserResponse;
import librarymanagementsystem.repo.UserRepo;
import librarymanagementsystem.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    
    @Override
    public UserResponse register(UserRequest userRequest) {
        User newUser = User.builder()
        .name(userRequest.getName())
        .email(userRequest.getEmail())
        .build();

        User savedUser = userRepo.saveAndFlush(newUser);
        return UserResponse.builder()
        .id(savedUser.getId())
        .name(savedUser.getName())
        .email(savedUser.getEmail())
        .build();
    }

    @Override
    public User getUserById(String id) {
         Optional<User> optionalUser = userRepo.getUserById(id);
        if (optionalUser.isPresent()) return optionalUser.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id : " + id + " Not Found");
    }

    @Override
    public User updateUser(User user) {
        this.getUserById(user.getId());
        return userRepo.save(user);
    }

    @Override
    public void deleteUserById(String id) {
        this.getUserById(id);
        userRepo.deleteUser(id);
    }

    @Override
    public Page<User> getAll(Integer page, Integer size) {
        if (page <=0) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page-1, size);
        return userRepo.getUser(pageable);
    }

}
