package com.example.project.service;

import com.example.project.dto.UserDto;
import com.example.project.entity.Profile;
import com.example.project.entity.Role;
import com.example.project.entity.User;
import com.example.project.repository.ProfileRepository;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProfileRepository profileRepository;

    public UserServiceImpl (UserRepository userRepository,
                            RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder,
                            ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        Profile profile = new Profile();

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhone(userDto.getPhone());
        user.setRegistrationDate(new Date());

        profile.setFirstName(userDto.getFirstName());
        profile.setLastName(userDto.getLastName());
        profile.setEmail(userDto.getEmail());
        profile.setPhone(userDto.getPhone());
        profile.setRegistrationDate(new Date());
        profile.setRating(0);

        Role role = roleRepository.findByName("ROLE_USER");

        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
        profileRepository.save(profile);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return userDto;
    }
}
