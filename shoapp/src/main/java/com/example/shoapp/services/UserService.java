package com.example.shoapp.services;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.shoapp.component.JwtTokenUtil;
import com.example.shoapp.dtos.UserDTO;
import com.example.shoapp.exceptions.DataNotFoundException;
import com.example.shoapp.exceptions.PermissionDenyException;
import com.example.shoapp.models.Role;
import com.example.shoapp.models.User;
import com.example.shoapp.repositories.RoleRepository;
import com.example.shoapp.repositories.UserRepository;
import com.example.shoapp.utils.LocalizationUtils;
import com.example.shoapp.utils.MessageKeys;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final LocalizationUtils localizationUtils;

    @Override
    @Transactional
    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));
        if (role.getName().toUpperCase().equals(Role.ADMIN)) {
            throw new PermissionDenyException("You cannot register an admin account");
        }
        // convert userdto => user
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();

        newUser.setRole(role);
        // nếu sử dụng google hoặc facebook thì không yêu cầu mật khẩu
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodePassword = passwordEncoder.encode(password);
            newUser.setPassword(encodePassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password, Long roleId) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid phonenumber or password");
        }
        User existingUser = optionalUser.get();
        if (existingUser.getFacebookAccountId() == 0 && existingUser.getGoogleAccountId() == 0) {
            if (!passwordEncoder.matches(password, existingUser.getPassword())) {
                throw new BadCredentialsException("Wrong phone number or password");
            }
        }
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isEmpty() || !roleId.equals(existingUser.getRole().getId())) {
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.ROLE_DOES_NOT_EXISTS));
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber,
                password, existingUser.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

}
