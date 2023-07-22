package com.mustafa.exporttutorial.controller;

import com.mustafa.exporttutorial.dto.UserDTO;
import com.mustafa.exporttutorial.entity.UserEntity;
import com.mustafa.exporttutorial.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/", produces = "application/json")
    public Integer addUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Optional<UserEntity> getUser(@PathVariable Integer id) {
        return userService.readUser(id);
    }

    @GetMapping(value = "/", produces = "application/json")
    public List<UserDTO> getAllUser() {
        return userService.readUserList();
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public Boolean updateUser(@PathVariable Integer id, UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping(value = "/", produces = "application/json")
    public Boolean deleteUser(Integer id) {
        return userService.deleteUser(id);
    }
}
