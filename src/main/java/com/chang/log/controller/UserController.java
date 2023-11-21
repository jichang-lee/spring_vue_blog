package com.chang.log.controller;

import com.chang.log.request.user.SignUp;
import com.chang.log.request.user.UserEditor;
import com.chang.log.request.user.UserResponse;
import com.chang.log.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup") //@valid
    public void signUp(@RequestBody SignUp signUp){
        userService.signUp(signUp);
    }

    @PatchMapping("/edit/{userId}")
    public void userEdit(@PathVariable Long userId , @RequestBody UserEditor userEditor){
        userService.userEdit(userId,userEditor);
    }

    @GetMapping("/myPage/{userId}")
    public UserResponse myPage(@PathVariable Long userId){
        return userService.findUser(userId);
    }

    @DeleteMapping("/delete/{userId}")
    public void userDelete(@PathVariable Long userId){
        userService.userDelete(userId);
    }



}
