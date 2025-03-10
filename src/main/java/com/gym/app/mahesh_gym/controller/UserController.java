package com.gym.app.mahesh_gym.controller;


import com.gym.app.mahesh_gym.dto.UserDTO;
import com.gym.app.mahesh_gym.dto.UserRegistrationDto;
import com.gym.app.mahesh_gym.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test() {
        return "done";
    }

    @PostMapping("/create_user")
    public String createUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        String resp = userService.createUser(userRegistrationDto);
        return resp;

    }

    @PostMapping("/update_user")
    public String updateUser(@RequestBody UserDTO userDTO) {
        String resp = userService.updateUser(userDTO);
        return resp;

    }

    @GetMapping("/get_user")
    public UserDTO getUser(@RequestParam String id) {
        UserDTO resp = userService.getUser(id);
        return resp;
    }

    @GetMapping("/get_all_user")
    public List<UserDTO> getAllUser() {
        List<UserDTO> resp = userService.getAllUsers();
        return resp;
    }

    @GetMapping("/active_users")
    public List<UserDTO> getActiveUsers() {
        List<UserDTO> resp = userService.getActiveUsers();
        return resp;
    }
}
