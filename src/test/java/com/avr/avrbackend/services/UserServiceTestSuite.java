package com.avr.avrbackend.services;

import com.avr.avrbackend.user.domain.User;
import com.avr.avrbackend.user.domain.UserDto;
import com.avr.avrbackend.user.mapper.UserMapper;
import com.avr.avrbackend.user.repository.UserRepository;
import com.avr.avrbackend.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTestSuite {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUo(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        User user = new User();
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        assertEquals(Collections.singletonList(user), userService.getAllUsers());
        verify(userRepository, times(1)).findAll();
    }
    @Test
    void testGetUserById() {
        Long userId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserByIdNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(userId);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testCreateUser() {
        User user = new User();

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser() {
        User user = new User();

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.updateUser(user);

        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    private final UserMapper userMapper = new UserMapper();

    @Test
    void testMapToUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("JohnDoe");
        userDto.setEmail("john.doe@example.com");

        User user = userMapper.mapToUser(userDto);

        assertEquals("JohnDoe", user.getUsername());
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    void testMapToUserDto() {
        User user = User.builder()
                .userId(1L)
                .username("JohnDoe")
                .email("john.doe@example.com")
                .build();

        UserDto userDto = userMapper.mapToUserDto(user);

        assertEquals(1L, userDto.getUserId());
        assertEquals("JohnDoe", userDto.getUsername());
        assertEquals("john.doe@example.com", userDto.getEmail());
    }

}
