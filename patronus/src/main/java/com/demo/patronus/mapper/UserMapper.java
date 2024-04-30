package com.demo.patronus.mapper;

import com.demo.patronus.dto.request.RegisterRequest;
import com.demo.patronus.dto.request.UpdateUserRequest;
import com.demo.patronus.dto.response.UserResponse;
import com.demo.patronus.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toUser(RegisterRequest request);
    UserResponse toUserResponse(User user);
    @Mapping(target = "id", ignore = true)
    void updateUserFromRequest(UpdateUserRequest updateUserRequest, @MappingTarget User user);
}