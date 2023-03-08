<template>
    
    <div>
        <el-card class="box-card" style="width: 1200px;">
            <template #header>
                <div class="card-header">
                    <span>班级成绩管理</span>
                    <el-button type="primary" class="button" @click="selectByExamIdClassId"
                        style="margin-left: -320px;">返回成绩列表</el-button>
                    <el-row :gutter="20" style="margin-right: -360px;margin-left: -150px;">
                        <el-input v-model="stuName" class="w-50 m-2" placeholder="请输入学生姓名" :prefix-icon="Search" />
                    </el-row>
                    <el-button type="primary" class="button" @click="selectStuScore"
                        style="margin-left: 40px;">查询</el-button>
                    <el-button type="primary" class="button" @click="openT">选择具体考试和班级</el-button>
                </div>
            </template>
            <el-table :data="scoreList.list" style="width: 100%" @sort-change="sortTable">
                <!-- pror要跟传递过来的数据字段名相同 -->
                <el-table-column fixed prop="scoreId" label="成绩编号" width="100" />
                <el-table-column prop="stuId" label="学号" width="100" />
                <el-table-column prop="stuName" label="学生姓名" width="120" />
                <el-table-column prop="examName" label="考试名称" width="160" />
                <el-table-column prop="className" label="班级编号" width="120" />
                <el-table-column prop="languageScore" label="语文成绩" sortable=custom width="120" />
                <el-table-column prop="mathScore" label="数学成绩" sortable=custom width="120" />
                <el-table-column prop="englishScore" label="英语成绩" sortable=custom width="120" />
                <el-table-column prop="politicsScore" label="政治成绩" sortable=custom width="120" />
                <el-table-column prop="historyScore" label="历史成绩" sortable=custom width="120" />
                <el-table-column prop="geogScore" label="地理成绩" sortable=custom width="120" />
                <el-table-column prop="totalPoints" label="总分" sortable=custom width="120" />
                <el-table-column fixed="right" label="操作" width="120">
                    <template #default="scope">
                        <el-button-group>
                            <el-button size="small" type="warning" @click="doUpdate(scope.row)">修改</el-button>
                        </el-button-group>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination background style="padding-top: 15px" @size-change="changePageSize"
                @current-change="changePageSize" v-model:current-page.sync="pageObj.pageNow" :page-sizes="[2, 4, 6, 8, 10]"
                v-model:page-size="pageObj.size" layout="total, sizes, prev, pager, next, jumper" v-model:total="total">
            </el-pagination>
        </el-card>
    </div>

    <!-- 选择班级考试信息模态框 -->
    <el-dialog v-model="dialogData.open" :title="dialogData.title" width="500px" draggable>
        <el-select v-model="pageObj.examId" filterable placeholder="请选择考试信息" @change="handleChange">
            <el-option v-for="item in examInfo.list" :key="item.examId" :label="item.examName" :value="item.examId" />
        </el-select>
        <el-select v-model="pageObj.classId" filterable placeholder="请选择班级信息" @change="handleChange">
            <el-option v-for="item in classInfo.list" :key="item.classId" :label="item.className" :value="item.classId" />
        </el-select>
        <template #footer>
            <span class="dialog-footer">
                <el-button type="primary" @click="clear">清除以选择信息</el-button>
                <el-button @click="selectByExamIdClassId()">确定</el-button>
                <el-button type="primary" @click="openF">取消</el-button>
            </span>
        </template>
    </el-dialog>

    <!-- 修改模态框 -->
    <el-dialog v-model="upDialog.open" :title="upDialog.title + '对话框'" width="600px" draggable>
        <el-form label-position="right" label-width="180px" :model="upDialog.form">
            <el-form-item label="成绩编号">
                <el-input id="scoreId" readonly="false" v-model="upDialog.form.scoreId" />
            </el-form-item>
            <el-form-item label="学号">
                <el-input id="stuId" readonly="false" v-model="upDialog.form.stuId" />
            </el-form-item>
            <el-form-item label="学生姓名">
                <el-input id="stuName" readonly="false" v-model="upDialog.form.stuName" />
            </el-form-item>
            <el-form-item label="班级编号">
                <el-input id="className" readonly="false" v-model="upDialog.form.className" />
            </el-form-item>
            <el-form-item label="考试名称">
                <el-input id="examName" readonly="false" v-model="upDialog.form.examName" />
            </el-form-item>
            <el-form-item label="语文成绩">
                <el-input id="languageScore" readonly="false" v-model="upDialog.form.languageScore" />
            </el-form-item>
            <el-form-item label="数学成绩">
                <el-input id="mathScore" readonly="false" v-model="upDialog.form.mathScore" />
            </el-form-item>
            <el-form-item label="英语成绩">
                <el-input id="englishScore" readonly="false" v-model="upDialog.form.englishScore" />
            </el-form-item>
            <el-form-item label="政治成绩">
                <el-input id="politicsScore" readonly=true v-model="upDialog.form.politicsScore" />
            </el-form-item>
            <el-form-item label="历史成绩">
                <el-input id="historyScore" readonly="false" v-model="upDialog.form.historyScore" />
            </el-form-item>
            <el-form-item label="地理成绩">
                <el-input id="geogScore" readonly v-model="upDialog.form.geogScore" />
            </el-form-item>
            <el-form-item label="总分">
                <el-input id="totalPoints" readonly="false" v-model="upDialog.form.totalPoints" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="upDialog.open = false">关闭</el-button>
                <el-button type="primary" @click="doit()">修改</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref, onMounted, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import scoreApi from "../../api/score"
import examApi from "../../api/exam"
import { Search } from '@element-plus/icons-vue'
import classApi from '../../api/class'
import relationApi from '../../api/relation'
import $ from 'jquery'

//接收查询出来的成绩数据 并跟表格数据绑定
const scoreList = reactive({ list: [] })


//定义对象 储存条件分页查询属性
const pageObj = reactive({
    teaId: parseInt(sessionStorage.getItem("id")),
    examId: "",
    classId: "",
    pageNow: 1,
    size: 10,
    sortName: "",
    order: ""
})

// 总条目数
let total = 0
//定义查询框绑定的姓名
const stuName = ref("")

//获取当前老师编号
const teaId = parseInt(sessionStorage.getItem("id"))

//模态框基础值
const dialog = {
    open: true,
    title: "请选择具体考试信息和班级信息",
    readonly: false,
};

const dialogData = reactive(JSON.parse(JSON.stringify(dialog)));


const openT = () => {
    dialogData.open = true
    //   console.log(dialogData.open)
}
const openF = () => {
    dialogData.open = false
}

//接收查询出来的考试信息
const examInfo = reactive({ list: [] })

//查询考试信息
examApi.selectAllExam().then((r) => {
    examInfo.list = r.data.data
    // console.log(examInfo.list)
})


//接收查询出来的班级信息
const classInfo = reactive({ list: [] })

//查询班级信息
classApi.selectByTeaIdList(teaId).then((r) => {
    if (r.data.ok) {
        console.log(r.data.data)
        classInfo.list = r.data.data
    } else {
        ElMessage.error(r.data.message)
    }
})

//成绩分页查询
const scorePage = () => {
    scoreApi.selectExamIdClassId(pageObj)
        .then((r) => {
            // console.log(r);
            if (r.data.ok) {
                scoreList.list = r.data.data.list
                total = r.data.data.total
                // console.log(scoreList.list.classId)
                // console.log(scoreList.list)
                // console.log("page"+total)
            }
        })
}

//查询方法
const selectByExamIdClassId = () => {
    pageObj.pageNow = 1
    console.log(pageObj)
    if ((pageObj.examId == null || pageObj.examId == "") && (pageObj.classId == null || pageObj.classId == "")) {
        ElMessage.error("请先选择考试编号或班级编号")
    } else {
        scorePage()
    }
    openF()
}

//清除以选择数据
const clear = () => {
    if (pageObj.classId == "" && pageObj.examId == "") {
        ElMessage.info("当前没有选择任何信息")
    } else {
        pageObj.examId = ""
        pageObj.classId = ""
    }
}

//排序按钮改变触发
const sortTable = (custom) => {
    if ((pageObj.examId == null || pageObj.examId == "") && (pageObj.classId == null || pageObj.classId == "")) {
        ElMessage.error("请先选择考试编号或班级编号")
        return
    }
    pageObj.sortName = custom.prop
    if (custom.order == "ascending") {
        pageObj.order = "ASC"
        pageObj.pageNow = 1
        scorePage()

    } else if (custom.order == "descending") {
        pageObj.order = "DESC"
        pageObj.pageNow = 1
        scorePage()
    } else {
        pageObj.order = ""
        scorePage()
    }
}

//改变分页长度时调用
const changePageSize = () => {
    scorePage()
}

//根据学生姓名和考试id查询学生具体成绩方法
const selectStuScore = () => {
    //对输入框进行非空判断和空格判断
    // console.log(examId.value)
    if ((pageObj.examId == null || pageObj.examId == "") && (pageObj.classId == null || pageObj.classId == "")) {
        ElMessage.error("请先选择考试编号或成绩编号")
        return
    }
    if (stuName.value.indexOf(" ")!=-1) {
        ElMessage.error("输入的查询信息不能包含空格")
        return
    }
    if (stuName.value == '' || stuName.value.split(" ").join("").length == 0) {
        ElMessage.error("姓名不能为空！！！")
    } else {
        const selectStuScoreInFo = reactive({
            teaId: pageObj.teaId,
            examId: pageObj.examId,
            classId: pageObj.classId,
            stuName: stuName.value
        })
        // console.log(selectStuScoreInFo)
        scoreApi.byClassIdExamIdStuNameSelectScore(selectStuScoreInFo)
            .then((r) => {
                if (r.data.ok) {
                    scoreList.list = r.data.data.list
                    total = r.data.data.total
                    ElMessage.success("查询成功")
                } else {
                    ElMessage.error(r.data.message)
                }
            })
    }
}

// 修改学生成绩模态框初始值
const upDialog = reactive({
    open: false,
    title: "修改",
    form: {
        scoreId: "",
        stuId: "",
        stuName: "",
        examName: "",
        className: "",
        languageScore: "",
        mathScore: "",
        englishScore: "",
        politicsScore: "",
        historyScore: "",
        geogScore: "",
        totalPoints: ""
    }
})

//定义对象获取班级编号和字段名
const temp = reactive({
    teaCourse: "",
})

var tempScore

//表单修改方法
const doUpdate = (list) => {
    upDialog.open = true
    //对表单传进来的数据进行深拷贝  避免表单的值跟着改变
    upDialog.form = JSON.parse(JSON.stringify(list))
    relationApi.selectCourse(teaId, upDialog.form.scoreId)
        .then((r) => {
            if (r.data.ok) {
                temp.teaCourse = r.data.data
                $("#" + temp.teaCourse + "").removeAttr("readonly")
                tempScore = $("#" + temp.teaCourse + "").val()
            }
        })
    if ((pageObj.examId == null || pageObj.examId == "") && (pageObj.classId == null || pageObj.classId == "")) {
        scoreApi.selectClassIdByScoreId(upDialog.form.scoreId)
            .then((r) => {
                pageObj.classId = r.data.data
            })
        scoreApi.selectExamIdByScoreId(upDialog.form.scoreId)
            .then((r) => {
                pageObj.examId = r.data.data
            })
    }
}
//确定修改
const doit = () => {
    var score = $("#" + temp.teaCourse + "").val()
    const reg = new RegExp(/\s/)

    //对输入框成绩进行校验  不符合要求则不调用api接口
    //成绩没有改变不调用api接口
    if (score == tempScore) {
        ElMessage.error("成绩没有变动")
        return
    }
    //判断成绩是否为空
    if (score.split(" ").join("").length == 0 || score == "") {
        ElMessage.error("成绩不能为空，请重新输入")
        return
    }
    if (reg.test(score) || isNaN(Number(score)) || Number(score) > 100 || Number(score) < -1) {
        ElMessage.error("成绩格式错误,请重新输入")
        return
    }

    scoreApi.updateById(upDialog.form)
        .then((r) => {
            if (r.data.ok) {
                // console.log(pageObj)
                ElMessage.success("修改成功")
                selectByExamIdClassId()
                upDialog.open = false
            }
        })
}

</script>

<style scoped>
.box-card {
    width: 800px;
    margin: 1em auto;
}

.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
</style>