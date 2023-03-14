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
                    <el-button type="primary" class="button" @click="openT">选择具体考试</el-button>
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
        <template #footer>
            <span class="dialog-footer">
                <el-button type="primary" @click="clear">清除以选择信息</el-button>
                <el-button @click="selectByExamIdClassId()">确定</el-button>
                <el-button type="primary" @click="openF">取消</el-button>
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
import stuApi from "../../api/student"


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

const getclassID = () => {
    stuApi.selectById(stuId)
        .then((r) => {
            pageObj.classId = r.data.data.classId
            console.log(pageObj.classId);
        })
}

setTimeout(() => {
    getclassID()
}, 1);



// 总条目数
let total = 0
//定义查询框绑定的姓名
const stuName = ref("")

const stuId = parseInt(sessionStorage.getItem("id"))

//模态框基础值
const dialog = {
    open: true,
    title: "请选择具体考试信息",
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


//成绩分页查询
const scorePage = () => {
    scoreApi.selectExamIdClassId(pageObj)
        .then((r) => {
            // console.log(r);
            if (r.data.ok) {
                scoreList.list = r.data.data.list
                total = r.data.data.total
            }
        })
}

//查询方法
const selectByExamIdClassId = () => {
    pageObj.pageNow = 1
    if (pageObj.examId == null || pageObj.examId == "") {
        ElMessage.error("请先选择考试编号")
    } else {
        scorePage()
    }
    openF()
}

const changePageSize = () => {
    if (pageObj.examId == null || pageObj.examId == "") {
        ElMessage.error("请先选择考试编号")
        return
    }
    scorePage()
}

//清除以选择数据
const clear = () => {
    if (pageObj.examId == "") {
        ElMessage.info("当前没有选择任何信息")
    } else {
        pageObj.examId = ""
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


//根据学生姓名和考试id查询学生具体成绩方法
const selectStuScore = () => {
    //对输入框进行非空判断和空格判断
    // console.log(examId.value)
    if ((pageObj.examId == null || pageObj.examId == "") && (pageObj.classId == null || pageObj.classId == "")) {
        ElMessage.error("请先选择考试编号或成绩编号")
        return
    }
    if (stuName.value.indexOf(" ") != -1) {
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