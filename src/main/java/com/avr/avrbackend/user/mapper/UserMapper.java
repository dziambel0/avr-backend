package com.avr.avrbackend.user.mapper;

import com.avr.avrbackend.user.domain.User;
import com.avr.avrbackend.user.domain.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .build();
    }

    public UserDto mapToUserDto(User user){
        return new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
