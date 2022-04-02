package dev.shph.digapply.configuration;

import dev.shph.commandeur.container.AnnotationScanningContainer;
import dev.shph.commandeur.container.CommandContainer;
import dev.shph.commandeur.container.CommandeurException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "dev.shph.digapply")
public class ApplicationConfiguration {
    @Bean
    public CommandContainer commandContainer() throws CommandeurException {
        return new AnnotationScanningContainer("dev.sph.digapply.controller");
    }
}
