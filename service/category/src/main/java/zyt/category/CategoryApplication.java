package zyt.category;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@SpringBootApplication
//允许服务注册发现
@EnableDiscoveryClient
@MapperScan("zyt.category.mapper")
@EnableFeignClients(basePackages = {"zyt.apis.blog"})
public class CategoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(CategoryApplication.class,args);
        System.out.println("category run successfully");
    }
}
