package ngen.calendar02.service;

import lombok.RequiredArgsConstructor;
import ngen.calendar02.entity.User;
import ngen.calendar02.repository.UserMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LoginUserService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        ngen.calendar02.entity.User user = userMapper.findPassword(username);

        // Step 2: UserエンティティからUserDetailsに変換
        if (user == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりません: " + username);
        }

//         UserDetailsの構築
//        UserDetails userDetails = org.springframework.security.core.userdetails.User
//                .builder()
//                .username(user.getUsername()) // Userエンティティのusername
//                .password(user.getPassword()) // Userエンティティのpassword
//                .roles("USER") // 役割を設定（適宜変更）
//                .build();

        // Step 3: UserDetails型を返す
        return user;



//        entity.User を Spring Security 用の UserDetails に変換
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                user.getAuthorities()
//        );


//        if("user".equals(username)){
//            return User.withUsername(username)
//                    .password(new BCryptPasswordEncoder().encode("password"))
//                    .authorities("USER")
//                    .build();
//        }else {
//            throw new UsernameNotFoundException("ユーザーが見つかりません");
//        }

    }

    public String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            return authentication.getName();
        }
        return null;
    }

}
