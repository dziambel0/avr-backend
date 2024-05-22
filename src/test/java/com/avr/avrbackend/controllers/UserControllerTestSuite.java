package com.avr.avrbackend.controllers;

import com.avr.avrbackend.user.controller.UserController;
import com.avr.avrbackend.user.domain.User;
import com.avr.avrbackend.user.domain.UserDto;
import com.avr.avrbackend.user.mapper.UserMapper;
import com.avr.avrbackend.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTestSuite {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetListOfUsers() {
        User user = new User();
        UserDto userDto = new UserDto();
        List<User> users = Arrays.asList(user);
        List<UserDto> userDtos = Arrays.asList(userDto);

        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        ResponseEntity<List<UserDto>> response = userController.getListOfUsers();

        assertEquals(ResponseEntity.ok(userDtos), response);
        verify(userService, times(1)).getAllUsers();
        verify(userMapper, times(1)).mapToUserDto(user);
    }

    @Test
    void testCreateUser() {
        UserDto userDto = new UserDto();
        User user = new User();

        when(userMapper.mapToUser(userDto)).thenReturn(user);

        ResponseEntity<Void> response = userController.createUser(userDto);

        assertEquals(ResponseEntity.ok().build(), response);
        verify(userMapper, times(1)).mapToUser(userDto);
        verify(userService, times(1)).createUser(user);
    }

    @Test
    void testGetUser() {
        Long userId = 1L;
        User user = new User();
        UserDto userDto = new UserDto();

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.getUser(userId);

        assertEquals(ResponseEntity.ok(userDto), response);
        verify(userService, times(1)).getUserById(userId);
        verify(userMapper, times(1)).mapToUserDto(user);
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        User existingUser = new User();
        User updatedUser = new User();
        User savedUser = new User();
        UserDto savedDto = new UserDto();

        when(userService.getUserById(userId)).thenReturn(Optional.of(existingUser));
        when(userMapper.mapToUser(userDto)).thenReturn(updatedUser);
        when(userService.updateUser(updatedUser)).thenReturn(savedUser);
        when(userMapper.mapToUserDto(savedUser)).thenReturn(savedDto);

        ResponseEntity<UserDto> response = userController.updateUser(userId, userDto);

        assertEquals(ResponseEntity.ok(savedDto), response);
        verify(userService, times(1)).getUserById(userId);
        verify(userMapper, times(1)).mapToUser(userDto);
        verify(userService, times(1)).updateUser(updatedUser);
        verify(userMapper, times(1)).mapToUserDto(savedUser);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(userService, times(1)).deleteUser(userId);
    }
}
