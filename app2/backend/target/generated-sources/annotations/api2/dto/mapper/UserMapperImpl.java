package api2.dto.mapper;

import api2.dto.request.UserRequest;
import api2.dto.response.UserResponse;
import api2.entity.User;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-11T20:15:10+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.20.1 (Ubuntu)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromRequestToEntity(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User user = new User();

        user.setName( userRequest.getName() );
        user.setUsername( userRequest.getUsername() );
        user.setAge( userRequest.getAge() );
        user.setEmail( userRequest.getEmail() );

        return user;
    }

    @Override
    public UserResponse fromEntityToResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setId( user.getId() );
        userResponse.setName( user.getName() );
        userResponse.setUsername( user.getUsername() );
        userResponse.setAge( user.getAge() );
        userResponse.setEmail( user.getEmail() );

        return userResponse;
    }
}
