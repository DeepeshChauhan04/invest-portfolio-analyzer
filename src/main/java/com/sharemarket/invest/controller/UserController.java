package com.sharemarket.invest.controller;


import com.sharemarket.invest.dto.request.UserRequest;
import com.sharemarket.invest.dto.response.UserResponse;
import com.sharemarket.invest.service.abstraction.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse createdUser = userService.creatUser(userRequest);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/getUser")
    public ResponseEntity<List<UserResponse>> getAllDataUser() {

        List<UserResponse> allUserList = userService.getAllUser();
        return ResponseEntity.ok(allUserList);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<UserResponse> getById(@Valid @PathVariable Long id) {
        UserResponse userResp = userService.getByUserId(id);
        return ResponseEntity.ok(userResp);

    }

    @DeleteMapping("/deletUser/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteById(id);
        return "User Successfully Deleted";
    }


}

