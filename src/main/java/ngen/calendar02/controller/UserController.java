package ngen.calendar02.controller;

import lombok.RequiredArgsConstructor;
import ngen.calendar02.entity.User;
import ngen.calendar02.repository.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/api/user")
    public String registerUser(@RequestBody User user) {
        if(userMapper.findPassword(user.getUsername()) != null) {
            return "すでに登録されています";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userMapper.insertUser(user);
        return "登録完了しました！";
    }
}
