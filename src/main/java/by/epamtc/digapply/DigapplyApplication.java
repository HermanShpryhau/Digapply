package by.epamtc.digapply;

import dev.shph.commandeur.container.AnnotationScanningContainer;
import dev.shph.commandeur.container.CommandContainer;
import dev.shph.commandeur.container.CommandeurException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DigapplyApplication {
    public static void main(String[] args) {
        SpringApplication.run(DigapplyApplication.class);
    }

    @Bean
    CommandContainer commandContainer() throws CommandeurException {
        return new AnnotationScanningContainer();
    }
}
