package librarymanagementsystem.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import librarymanagementsystem.constant.ERole;
import librarymanagementsystem.entity.User;
import librarymanagementsystem.entity.UserCredential;
import librarymanagementsystem.model.request.UserRequest;

import librarymanagementsystem.repo.UserRepo;
import librarymanagementsystem.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    
    @Override
    public User register(UserRequest userRequest, UserCredential userCredential) {
        User newUser = User.builder()
        .name(userRequest.getName())
        .email(userRequest.getEmail())
        .roles(ERole.ROLE_USER)
        .userCredential(userCredential)
        .build();

        User savedUser = userRepo.saveAndFlush(newUser);
        return savedUser;
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
