package application.config;

import application.service.MoexService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration
public class AppStart {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(AppStart.class).headless(false).run(args);
        MoexService moexService = context.getBean(MoexService.class);
        moexService.run();
    }

}
