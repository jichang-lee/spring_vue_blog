package com.chang.log.controller;

import com.chang.log.request.user.SignUp;
import com.chang.log.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup") //@valid
    public void signUp(@RequestBody SignUp signUp){
        userService.signUp(signUp);
    }

    @PostMapping("/edit")
    public void userEdit(){

    }

}
