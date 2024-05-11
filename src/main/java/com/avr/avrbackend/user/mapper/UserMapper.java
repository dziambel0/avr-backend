package com.avr.avrbackend.user.mapper;

import com.avr.avrbackend.user.domain.User;
import com.avr.avrbackend.user.domain.UserDto;

public class UserMapper {

    public static User mapToUser(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .build();
    }

    public static UserDto mapToUserDto (User user){
        return new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
