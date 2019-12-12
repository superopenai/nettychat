package me.superning.nettychat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author superning
 */
@SpringBootApplication
@MapperScan(basePackages = "me.superning.nettychat.mapper")
public class NettyChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyChatApplication.class, args);


    }
    }

