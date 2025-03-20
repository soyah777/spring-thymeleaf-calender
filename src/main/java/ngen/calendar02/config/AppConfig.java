package ngen.calendar02.config;

import lombok.RequiredArgsConstructor;
import ngen.calendar02.repository.UserMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final UserMapper userMapper;

    @Bean
    public CommandLineRunner dataLoader(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        return args -> {
            String username = "chiikawa";

            if(userMapper.findPassword(username) == null) {
                ngen.calendar02.entity.User user = new ngen.calendar02.entity.User();
                user.setUsername(username);
                user.setName("ちいかわ");
                user.setPassword(passwordEncoder.encode("passpass"));
                userMapper.insertUser(user);
            }else {
                System.out.println(username+"はすでに登録されています");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
