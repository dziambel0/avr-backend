package com.avr.avrbackend.user.controller;

import com.avr.avrbackend.user.domain.UserDto;
import com.avr.avrbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

//    @GetMapping
//    public List<UserDto> getAllUsers() {
//        return userService.getAllUsers();
//    }

//    @GetMapping("/{userId}")
//    public UserDto getUserById(@PathVariable Long userId) {
//        for (UserDto user : users) {
//            if (user.getUserId().equals(userId)) {
//                return user;
//            }
//        }
//        throw new RuntimeException("User not found for id: " + userId);
//    }
//
//    @PostMapping
//    public void addUser(@RequestBody UserDto user) {
//        users.add(user);
//    }
//
//    @PutMapping("/{userId}")
//    public void updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
//        for (int i = 0; i < users.size(); i++) {
//            if (users.get(i).getUserId().equals(userId)) {
//                users.set(i, userDto);
//                return;
//            }
//        }
//        throw new RuntimeException("User not found for id: " + userId);
//    }
//
//    @DeleteMapping("/{userId}")
//    public void deleteUser(@PathVariable Long userId) {
//        users.removeIf(user -> user.getUserId().equals(userId));
//    }
//}
