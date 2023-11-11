package api2.service;

import api2.dto.request.UserRequest;
import api2.dto.response.UserResponse;
import api2.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUser();
    Optional<User> findById(Long id);
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);

//    Using Request for Save and Update User
    UserResponse saveUser(UserRequest userRequest);
    UserResponse updateUser(UserRequest userRequest, Long id);


}