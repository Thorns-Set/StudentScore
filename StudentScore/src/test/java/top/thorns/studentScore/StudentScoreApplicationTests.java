package top.thorns.studentScore;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.thorns.studentScore.dto.ScoreMaxDto;
import top.thorns.studentScore.dto.scoreMax.language;
import top.thorns.studentScore.entity.TClass;
import top.thorns.studentScore.entity.TScore;
import top.thorns.studentScore.entity.TStudent;
import top.thorns.studentScore.entity.TTeacher;
import top.thorns.studentScore.mapper.TClassMapper;
import top.thorns.studentScore.mapper.TExamMapper;
import top.thorns.studentScore.mapper.TScoreMapper;
import top.thorns.studentScore.mapper.TTeacherMapper;
import top.thorns.studentScore.service.ITStudentService;

import java.util.HashMap;
import java.util.List;


@SpringBootTest
class StudentScoreApplicationTests {
    @Autowired
    private ITStudentService itStudentService;

    @Autowired
    private TTeacherMapper tTeacherMapper;

    @Autowired
    private TScoreMapper tScoreMapper;

    @Autowired
    private TClassMapper tClassMapper;

    @Autowired
    private TExamMapper tExamMapper;

    //测试mapper接口根据id查询功能
    @Test
    void contextLoads() {
        TStudent student = itStudentService.getById(1);
        System.out.println(student.toString());
    }

    //测试mapper接口查询全部功能
    @Test
    void selectListTest() {
        List<TTeacher> list = tTeacherMapper.selectList(null);
        for (TTeacher tTeacher : list) {
            System.out.println(tTeacher);
        }
    }

    //测试mapper接口添加功能
    @Test
    void insertTest() {
        TTeacher tTeacher = new TTeacher();
        tTeacher.setTeaName("主席");
        tTeacher.setTeaIdentity("430411111111111111");
        tTeacher.setTeaTel("15011111111");
        tTeacher.setTeaEmial("eml");
        tTeacher.setTeaPassword("123456");
        int flag = tTeacherMapper.insert(tTeacher);
        System.out.println(tTeacher.getTeaId());
    }

    //测试mapper接口根据id删除功能
    @Test
    void deleteTest() {
        int flag=tExamMapper.deleteById(11);
        System.out.println(flag);
    }

    //测试mybatisplus 分页插件
    @Test
    void pageTest() {
        //创建page类设置分页信息
        Page<TTeacher> page = new Page<>(2, 5);
        tTeacherMapper.selectPage(page, null);
        System.out.println(page);
    }

    //测试mapper接口根据id修改功能
    @Test
    void updateTest() {
        TTeacher tTeacher = new TTeacher();
        tTeacher.setTeaId(10);
        tTeacher.setTeaName("测试修改");
        tTeacher.setTeaEmial("测试修改");

        int flag = tTeacherMapper.updateById(tTeacher);
        System.out.println(flag);
    }

    //测试自定义方法
    @Test
    void selectByIdPasswordTest() {
        String password = tTeacherMapper.selectByIdPassword(10);
        System.out.println(password);
    }

    @Test
    void test() {
        TScore tScore = tScoreMapper.selectById(1);
        System.out.println(tScore);
    }

    @Test
    void classMapperTest() {
        List<TClass> list = tClassMapper.selectByTeaIdList(1);
        System.out.println(list);
    }

    @Test
    void selectScoreMax() {
//        ScoreMaxDto dto=new ScoreMaxDto();
        List<language> list = tScoreMapper.selectLanguageScoreMax(1, 1);
        list.forEach(language -> {
            System.out.println(language.toString());
        });
    }
    @Test
    void qw(){
        HashMap<String,Integer> map =new HashMap<>();
        map.put("stu_id",1);
        map.put("exam_id",31);
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(map);

        TScore tScore=tScoreMapper.selectOne(queryWrapper);
        System.out.println(tScore==null);
    }
    @Test
    void e(){
//        ScoreMaxDto maxDto=new ScoreMaxDto();
//        maxDto.setGeogMaxList(tScoreMapper.selectGeogScoreMax(1, 4));
//        System.out.println(maxDto);
        TScore tScore=new TScore();
        for (int i = 31,k=20171401; i <= 60; i++,k++) {
            tScore.setScoreId(i);
            tScore.setStuId(k);
            tScoreMapper.updateById(tScore);
        }
    }
}
