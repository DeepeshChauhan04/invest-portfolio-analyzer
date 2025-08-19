package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dao.UserDao;
import com.sharemarket.invest.dto.request.UserRequest;
import com.sharemarket.invest.dto.response.UserResponse;
import com.sharemarket.invest.entity.User;
import com.sharemarket.invest.exception.CustomInvestException;
import com.sharemarket.invest.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ModelMapper modelMapper;


    @Override
    public UserResponse creatUser(UserRequest userRequest) {
        log.info("Creating new User:  {} with email: {}", userRequest.getEmail(), userRequest.getUsername());

        if (userDao.existsByEmail(userRequest.getEmail())) {
            throw new CustomInvestException(0, "User already exist with that email id", null);
        }
        User models = modelMapper.map(userRequest, User.class);
        models.setCreatedAt(LocalDateTime.now());
        models.setStatus(1);
        models.setUpdatedAt(LocalDateTime.now());
        User saveuser = userDao.save(models);
        return modelMapper.map(saveuser, UserResponse.class);
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<User> userData = userDao.findAll();
        List<UserResponse> responses = userData.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());

        log.info("user list : {}", responses.size());
        return responses;
    }

    @Override
    public UserResponse getByUserId(long id) {
        if (!userDao.existsByUserIdAndStatus(id, 1)) {
            throw new CustomInvestException(0, "User not found with ID: " + id, null);
        }
        User userById = userDao.getById(id);
        return modelMapper.map(userById, UserResponse.class);
    }

    @Override
    public void deleteById(long id) {
        if (!userDao.existsByUserIdAndStatus(id, 1)) {
            throw new CustomInvestException(0, "User not found with ID: " + id, null);
        }
        userDao.deleteById(id);
    }
}



