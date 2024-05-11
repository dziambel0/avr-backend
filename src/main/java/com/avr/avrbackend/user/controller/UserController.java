package com.avr.avrbackend.user.controller;

import com.avr.avrbackend.user.domain.User;
import com.avr.avrbackend.user.domain.UserDto;
import com.avr.avrbackend.user.mapper.UserMapper;
import com.avr.avrbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getListOfUsers() {
        List<User> userList = userService.getAllUsers();
        List<UserDto> dtos = userList.stream()
                .map(userMapper::mapToUserDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDto productGroupsDTO) {
        User user = userMapper.mapToUser(productGroupsDTO);
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(()-> new RuntimeException("User not found by id:" + userId));
        UserDto dto = userMapper.mapToUserDto(user);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User existingUser = userService.getUserById(userId)
                .orElseThrow(()-> new RuntimeException("User not found by id:" + userId));

        User updatedUser = userMapper.mapToUser(userDto);
        updatedUser.setUserId(existingUser.getUserId());

        User savedUser = userService.updateUser(updatedUser);
        UserDto savedDto = userMapper.mapToUserDto(savedUser);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping(value = "{userId}")
    public ResponseEntity<Void> deleteUser (@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
