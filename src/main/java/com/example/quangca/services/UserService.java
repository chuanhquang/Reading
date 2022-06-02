package com.example.quangca.services;

import com.example.quangca.dto.UserDto;
import com.example.quangca.models.Role;
import com.example.quangca.models.User;
import com.example.quangca.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private NovelService novelService;

    @Autowired
    private NovelFavoriteService novelFavoriteService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDto> getUsers(Integer limit){
        List<User> users = Optional.ofNullable(limit)
                .map(value -> userRepository.findAll(PageRequest.of(0,limit)).getContent())
                .orElseGet(() -> userRepository.findAll());
        return users
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto signupMember(UserDto userDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(usernameExist(userDto.getUsername())){
            return null;
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        Set<Role> roles = new HashSet<>();
//        roles.add(roleRepository.getById(3));
        User newUser = userRepository.save(user);
        newUser.getRoles().addAll(userDto
                .getRoles()
                .stream()
                .map(role ->{
                    Role r = roleService.findRoleByName(role.getName());
                    r.getUsers().add(newUser);
                    return r;
                }).collect(Collectors.toSet()));
        userRepository.flush();
        return modelMapper.map(newUser, UserDto.class);
    }

    /**
     *
     * @param novelId
     * @return
     */
    public boolean favoriteUnfavoriteNovel(int novelId){
        UserDto currentUser = this.getCurrentLoggedInUserDto();
        //TODO
        return novelFavoriteService.addRemoveFavoriteNovel(currentUser.getId(), novelId);
    }

    private boolean usernameExist(String username){
        return userRepository.findByUsername(username) != null;
    }

    public UserDto findUserByUsername(String username){
        return modelMapper.map(userRepository.findByUsername(username), UserDto.class);
    }

    public UserDto getCurrentLoggedInUserDto(){
        return this.findUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

    public User getCurrentLoggedInUser(){
        return userRepository.findByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

    public boolean isUserLoggedIn(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param newPassword
     * @param oldPassword
     * @return  TRUE if oldpassword is matched, otherwise FALSE
     */
    public boolean changePassword(String newPassword, String oldPassword) {
        User currentUser = getCurrentLoggedInUser();
        if (passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            currentUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.saveAndFlush(currentUser);
            return true;
        }
        return false;
    }
}
