package zyt.blog;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
//允许服务注册发现
@EnableDiscoveryClient
@MapperScan("zyt.blog.mapper")
//@ComponentScan("zyt.blog.config")
@EnableFeignClients(basePackages = {"zyt.apis.category","zyt.apis.user"})
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class,args);
        System.out.println("blog run successfully");
    }
}
