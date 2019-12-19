package me.superning.nettychat;

import me.superning.nettychat.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author superning
 */
@SpringBootApplication
@MapperScan(basePackages = "me.superning.nettychat.mapper")
public class NettyChatApplication {

    @Bean
    public SpringUtil getspringutil()
    {
        return new SpringUtil();

    }



    public static void main(String[] args) {
        SpringApplication.run(NettyChatApplication.class, args);


    }
}

