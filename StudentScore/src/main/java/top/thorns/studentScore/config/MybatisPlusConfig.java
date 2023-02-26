package top.thorns.studentScore.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Thorns
 * @Data:2023/2/1421:06
 * @PackageName:top.thorns.studentscore.config
 * @ClassName: MybatisPlusConfig
 * @Description: 配置mybatis plus分页插件
 **/
@Configuration
@MapperScan("top.thorns.studentScore.mapper")
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        //用来配置mybatis plus的插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //调用方法添加内部插件  此处添加分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        return interceptor;
    }

}
