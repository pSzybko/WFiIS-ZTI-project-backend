package zti.projekt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zti.projekt.aspect.AspectLogger;

@Configuration
public class AppConfig {
    @Bean
    public AspectLogger customAspectLogger() {
        return new AspectLogger();
    }
}