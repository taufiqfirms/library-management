package librarymanagementsystem.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import librarymanagementsystem.entity.User;

import librarymanagementsystem.model.response.PagingResponse;

import librarymanagementsystem.model.response.WebResponse;
import librarymanagementsystem.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasAnyRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id){
        User findUser = userService.getUserById(id);
        WebResponse<User> response = WebResponse.<User>builder()
        .status(HttpStatus.OK.getReasonPhrase())
        .message("Success get User by id ")
        .data(findUser)
        .build();
    return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id){
        userService.deleteUserById(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success delete User by id ")
                .data("OK")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> updateUserById(@RequestBody User user){
        User updateUser = userService.updateUser(user);
        WebResponse<User> response = WebResponse.<User>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success update User by id ")
                .data(updateUser)
                .build();
        return ResponseEntity.ok(response);
    }

     @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size){
            
            Page<User> bookList = userService.getAll(page, size);

            PagingResponse pagingResponse = PagingResponse.builder()
            .page(page)
            .size(size)
            .totalPages(bookList.getTotalPages())
            .totalElement(bookList.getTotalElements())
            .build();

            WebResponse<List<User>> response = WebResponse.<List<User>>builder()
            .status(HttpStatus.OK.getReasonPhrase())
            .message("Success get list User")
            .paging(pagingResponse)
            .data(bookList.getContent())
            .build();
            return ResponseEntity.ok(response);
    }
}