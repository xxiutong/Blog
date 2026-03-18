package zyt.files;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@SpringBootApplication
//允许服务注册发现
@EnableDiscoveryClient
@MapperScan("zyt.files.mapper")
public class FilesApplication {
    public static void main(String[] args) {
        SpringApplication.run(FilesApplication.class,args);
        System.out.println("fileService run successfully");
    }
}
