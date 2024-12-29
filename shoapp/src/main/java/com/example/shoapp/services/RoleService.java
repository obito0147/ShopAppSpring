package com.example.shoapp.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.shoapp.models.Category;
import com.example.shoapp.models.Role;
import com.example.shoapp.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}
