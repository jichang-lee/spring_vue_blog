package com.chang.log.service;

import com.chang.log.config.CustomUserDetails;
import com.chang.log.domain.User;
import com.chang.log.domain.UserBlackList;
import com.chang.log.exception.AlreadyExistsEmail;
import com.chang.log.exception.UserNotFound;
import com.chang.log.repository.UserBlackListRepository;
import com.chang.log.repository.UserRepository;
import com.chang.log.request.user.SignUp;
import com.chang.log.request.user.UserEditor;
import com.chang.log.response.UserResponse;
import com.chang.log.response.UserTokenInfo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserBlackListRepository userBlackListRepository;

    public void signUp(SignUp signUp) {

        //1. byEmail -> 중복 이메일 검증
        Optional<User> byEmail = userRepository.findByEmail(signUp.getEmail());
        if(byEmail.isPresent()){
            throw new AlreadyExistsEmail();
        }

        //2. passwordEncoder
        String encrypt = passwordEncoder.encode(signUp.getPassword());

        User user = User.builder()
                .email(signUp.getEmail())
                .password(encrypt)
                .name(signUp.getName())
                .build();

        userRepository.save(user);

    }

    @Transactional
    public void addUserBlackList() {
        List<User> blackListUser= userRepository.findByBlackUserYn("Y");

        List<UserBlackList> blackListEntries = blackListUser.stream()
            .map(user -> UserBlackList.builder().user(user).build())
            .toList();

        userBlackListRepository.saveAll(blackListEntries);
    }


    //할지 말지 마지막에 생각
//    public void signUpToImage(SignUp signUp) throws Exception {
//
//        //1. byEmail -> 중복 이메일 검증
//        Optional<User> byEmail = userRepository.findByEmail(signUp.getEmail());
//        if(byEmail.isPresent()){
//            throw new AlreadyExistsEmail();
//        }
//
//        //2. passwordEncoder
//        String encrypt = passwordEncoder.encode(signUp.getPassword());
//
//        //프로필 이미지 지정을 안했을 때
//        if(signUp.getImage() == null) {
//        User user = User.builder()
//                .email(signUp.getEmail())
//                .password(encrypt)
//                .name(signUp.getName())
//                .build();
//
//        userRepository.save(user);
//        }else{
//            MultipartFile file = signUp.getFile();
//            User user = User.builder()
//                    .email(signUp.getEmail())
//                    .password(encrypt)
//                    .name(signUp.getName())
//                    .image(signUp.getImage())
//                    .build();
//            userRepository.save(user);
//
//            imageService.uploadImage(file,user);
//        }
//
//    }

    @Transactional
    public void userEdit(Long userId , UserEditor userEditor){

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        String encrypt = passwordEncoder.encode(userEditor.getPassword());

           user.edit(userEditor.getName(),
                   userEditor.getEmail(),
                   encrypt);
    }
    public void userDelete(Long userId){
        User findUser = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        userRepository.delete(findUser);
    }


   public UserResponse findUser(Long userId){
       User user = userRepository.findById(userId)
               .orElseThrow(UserNotFound::new);

       return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
   }

}
