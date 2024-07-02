package id.my.hendisantika.thymeleaf;

import id.my.hendisantika.thymeleaf.entity.Tutorial;
import id.my.hendisantika.thymeleaf.repository.TutorialRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootCrudThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCrudThymeleafApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(TutorialRepository tutorialRepository) {
        return args -> {
            tutorialRepository.deleteAll();
            for (int i = 0; i < 25; i++) {
                tutorialRepository.save(new Tutorial(i, "Tutorial " + i, "Tutorial 1", i, true));

            }

        };
    }

}
