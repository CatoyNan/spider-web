package top.catoy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("top.catoy.dao")
@SpringBootApplication
public class SpiderWeb {

    public static void main(String[] args) {
        SpringApplication.run(SpiderWeb.class, args);

    }

}
