package org.example.tutorial4_2.service;

import jakarta.transaction.Transactional;
import org.example.tutorial4_2.entity.Role;
import org.example.tutorial4_2.exceptions.RoleNotFoundException;
import org.example.tutorial4_2.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }

    public void createNewRole(Role role){
        roleRepository.save(role);
    }

    public Role findRole(String roleName) throws RoleNotFoundException {
        return roleRepository.findByName(roleName).orElseThrow(RoleNotFoundException::new);
    }

    @Transactional
    public void deleteRole(String name){
        try {
            roleRepository.delete(findRole(name));
        } catch(RoleNotFoundException ignored){

        }
    }

    public Optional<Role> findRoleById(long id){
        return roleRepository.findById(id);
    }
}
