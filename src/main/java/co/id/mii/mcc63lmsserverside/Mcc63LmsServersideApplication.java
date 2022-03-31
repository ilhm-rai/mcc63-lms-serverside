package co.id.mii.mcc63lmsserverside;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Mcc63LmsServersideApplication {

    public static void main(String[] args) {
        SpringApplication.run(Mcc63LmsServersideApplication.class, args);
        System.out.println("Serverside is Running");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}