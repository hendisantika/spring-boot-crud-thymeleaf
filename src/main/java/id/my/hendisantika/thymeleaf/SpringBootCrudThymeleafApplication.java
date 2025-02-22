package id.my.hendisantika.thymeleaf;

import id.my.hendisantika.thymeleaf.entity.Tutorial;
import id.my.hendisantika.thymeleaf.repository.TutorialRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

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
                tutorialRepository.save(new Tutorial("Tutorial " + i, "Tutorial 1", i, true));

            }

        };
    }

    private ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addDialect(new Java8TimeDialect());
        engine.setTemplateResolver(templateResolver);
        return engine;
    }

}
