package com.sharemarket.invest.service.abstraction;

import com.sharemarket.invest.dto.request.UserRequest;
import com.sharemarket.invest.dto.response.UserResponse;

import java.util.List;

public interface UserService {


    UserResponse creatUser(UserRequest userRequest);

    List<UserResponse> getAllUser();

    UserResponse getByUserId(long id);

    void deleteById(long id);

}
