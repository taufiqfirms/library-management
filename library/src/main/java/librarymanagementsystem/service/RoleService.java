package librarymanagementsystem.service;

import librarymanagementsystem.constant.ERole;
import librarymanagementsystem.entity.Role;

public interface RoleService {
    Role getOrSave(ERole role);
}
