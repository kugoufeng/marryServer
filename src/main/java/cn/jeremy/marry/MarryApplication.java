package cn.jeremy.marry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@MapperScan("cn.jeremy.marry.dao")

@SpringBootApplication
public class MarryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarryApplication.class, args);
	}


}
