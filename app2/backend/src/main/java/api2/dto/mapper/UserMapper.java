package api2.dto.mapper;

import api2.dto.request.UserRequest;
import api2.dto.response.UserResponse;
import api2.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    User fromRequestToEntity(UserRequest userRequest);
    UserResponse fromEntityToResponse(User user);
}