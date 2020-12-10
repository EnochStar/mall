package zust.bjx.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author EnochStar
 * @title: MallApplication
 * @projectName mall
 * @description: TODO
 * @date 2020/12/319:01
 */
@SpringBootApplication
@MapperScan(basePackages = "zust.bjx.mall.dao")
public class MallApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }

}
