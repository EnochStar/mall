package zust.bjx.mall;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author EnochStar
 * @title: InterceptorConfig
 * @projectName mall
 * @description: 拦截器的配置
 * @date 2020/12/14 21:43
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/error","/carts","/user/login","/user/register","/categories","/products","/products/*");
    }
}
