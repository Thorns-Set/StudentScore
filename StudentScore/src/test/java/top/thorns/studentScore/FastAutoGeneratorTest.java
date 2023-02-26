package top.thorns.studentScore;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Thorns
 * @Data:2022/11/160:08
 * @PackageName:top.thorns.studentscore
 * @ClassName: FastAutoGeneratorTest
 * @Description: TODO
 **/
public class FastAutoGeneratorTest {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/student_score?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8";
        FastAutoGenerator.create(url, "root", "abcd1234")
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称？")).fileOverride())
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名？")))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok().build())
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
