package com.appsdevblog.app.ws.service;

import com.appsdevblog.app.ws.shared_dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto user);

    UserDto getUser(String email);

    UserDto getUserByUserId(String userId);

    UserDto updateUser(String id, UserDto user);

    void deleteUser(String userId);

    List<UserDto> getUsers(int page, int limit);
}
