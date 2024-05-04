package librarymanagementsystem.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import librarymanagementsystem.constant.ERole;
import librarymanagementsystem.entity.Role;
import librarymanagementsystem.repo.RoleRepo;
import librarymanagementsystem.service.RoleService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;

    @Override
    public Role getOrSave(ERole role) {
        Optional<Role> optionalRole = roleRepo.findByRole(role);
        if(optionalRole.isPresent()) return optionalRole.get();

        return roleRepo.saveAndFlush(
            Role.builder()
            .role(role)
            .build()
        );
    }

}
