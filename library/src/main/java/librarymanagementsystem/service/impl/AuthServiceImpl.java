package librarymanagementsystem.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.annotation.PostConstruct;
import librarymanagementsystem.constant.ERole;
import librarymanagementsystem.entity.Role;
import librarymanagementsystem.entity.User;
import librarymanagementsystem.entity.UserCredential;
import librarymanagementsystem.model.request.AuthRequest;
import librarymanagementsystem.model.request.UserRequest;
import librarymanagementsystem.model.response.UserResponse;
import librarymanagementsystem.repo.UserCredentialRepo;
import librarymanagementsystem.security.JwtUtils;
import librarymanagementsystem.service.AuthService;
import librarymanagementsystem.service.RoleService;
import librarymanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService{

    private final RoleService roleService;
    private final UserCredentialRepo userCredentialRepo;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Value("${app.enigma-coop.username-admin}")
    private String usernameAdmin;

    @Value("${app.enigma-coop.password-admin}")
    private String passwordAdmin;

    @PostConstruct
    public void initSuperAdmin(){
        Optional<UserCredential> optionalUserCred = userCredentialRepo.findByUsername(usernameAdmin);
        if(optionalUserCred.isPresent()) return;

        Role adminRole = roleService.getOrSave(ERole.ROLE_ADMIN);
        Role employeeRole = roleService.getOrSave(ERole.ROLE_USER);

        String hashPassword = passwordEncoder.encode(passwordAdmin);

        UserCredential userCredential = UserCredential.builder()
        .username(usernameAdmin)
        .password(hashPassword)
        .roles(List.of(adminRole, employeeRole))
        .build();
        userCredentialRepo.saveAndFlush(userCredential);
    }
    
    @Override
    public UserResponse register(UserRequest userRequest) {
        
        Role roleUser = roleService.getOrSave(ERole.ROLE_USER);
        String hassPassword = passwordEncoder.encode(userRequest.getPassword());
        UserCredential userCredential = userCredentialRepo.saveAndFlush(UserCredential.builder()
        .username(userRequest.getUsername())
        .password(hassPassword)
        .roles(List.of(roleUser))
        .build());
        User user = userService.register(userRequest, userCredential);

        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
        return UserResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .username(userRequest.getUsername())
        .roles(roles)
        .build();
    }

    @Override
    public String login(AuthRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
        authRequest.getUsername(),
        authRequest.getPassword());

        Authentication authenticated = authenticationManager.authenticate(authentication);

        SecurityContextHolder.getContext().setAuthentication(authenticated);

        UserCredential userCredential = (UserCredential)authenticated.getPrincipal();
        return jwtUtils.generateToken(userCredential);

    }

}
