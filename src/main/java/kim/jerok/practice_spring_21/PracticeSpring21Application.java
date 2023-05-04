package kim.jerok.practice_spring_21;

import kim.jerok.practice_spring_21.model.user.User;
import kim.jerok.practice_spring_21.model.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PracticeSpring21Application {

    // 서버 실행시에 실행됨!!
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return (args) -> {
            userRepository.save(User.builder().username("jerok").password("1234").email("jerok.kim@gmail.com").role("USER").build());
            userRepository.save(User.builder().username("admin").password("1234").email("admin@gmail.com").role("ADMIN").build());
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(PracticeSpring21Application.class, args);
    }

}
