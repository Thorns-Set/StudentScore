<template>
    <div>
        <el-card class="box-card" style="width: 1200px;">
            <template #header>
                <div class="card-header">
                    <span>班级成绩管理</span>
                    <el-button type="primary" class="button" @click="selectByExamIdClassId"
                        style="margin-left: -290px;">返回成绩列表</el-button>
                    <el-button type="primary" class="button" @click="addDialog.open = true"
                        style="margin-left: -290px;">添加</el-button>
                    <el-row :gutter="20" style="margin-right: -360px;margin-left: -150px;">
                        <el-input v-model="stuName" class="w-50 m-2" placeholder="请输入学生姓名" :prefix-icon="Search" />
                    </el-row>
                    <el-button type="primary" class="button" @click="selectStuScore"
                        style="margin-left: 70px;">查询</el-button>
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
                            <el-button size="small" type="danger" @click="deleteByid(scope.row)">删除</el-button>
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
    <el-dialog v-model="upDialog.open" title="修改成绩信息" width="600px" draggable>
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
            <el-form-item label="班级名称">
                <el-input id="className" readonly="false" v-model="upDialog.form.className" />
            </el-form-item>
            <el-form-item label="考试名称">
                <el-input id="examName" readonly="false" v-model="upDialog.form.examName" />
            </el-form-item>
            <el-form-item label="语文成绩">
                <el-input id="languageScore" v-model="upDialog.form.languageScore" />
            </el-form-item>
            <el-form-item label="数学成绩">
                <el-input id="mathScore" v-model="upDialog.form.mathScore" />
            </el-form-item>
            <el-form-item label="英语成绩">
                <el-input id="englishScore" v-model="upDialog.form.englishScore" />
            </el-form-item>
            <el-form-item label="政治成绩">
                <el-input id="politicsScore" v-model="upDialog.form.politicsScore" />
            </el-form-item>
            <el-form-item label="历史成绩">
                <el-input id="historyScore" v-model="upDialog.form.historyScore" />
            </el-form-item>
            <el-form-item label="地理成绩">
                <el-input id="geogScore" v-model="upDialog.form.geogScore" />
            </el-form-item>
            <el-form-item label="总分">
                <el-input id="totalPoints" readonly="false" v-model="upDialog.form.totalPoints" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="upDialog.open = false">关闭</el-button>
                <el-button type="primary" @click="doit()">确定</el-button>
            </span>
        </template>
    </el-dialog>
    <!-- 添加模态框 -->
    <el-dialog v-model="addDialog.open" title="添加成绩信息" width="600px" draggable>
        <el-form label-position="right" label-width="180px" :model="addDialog.form">
            <el-form-item label="成绩编号">
                <el-input v-model="addDialog.form.scoreId" />
            </el-form-item>
            <el-form-item label="学号">
                <el-input v-model="addDialog.form.stuId" />
            </el-form-item>
            <el-form-item label="班级名称">
                <el-select v-model="addDialog.form.classId" filterable placeholder="请选择班级名称" @change="handleChange">
                    <el-option v-for="item in classInfo.list" :key="item.classId" :label="item.className"
                        :value="item.classId" />
                </el-select>
            </el-form-item>
            <el-form-item label="考试名称">
                <el-select v-model="addDialog.form.examId" filterable placeholder="请选择考试名称" @change="handleChange">
                    <el-option v-for="item in examInfo.list" :key="item.examId" :label="item.examName"
                        :value="item.examId" />
                </el-select>
            </el-form-item>
            <el-form-item label="语文成绩">
                <el-input v-model="addDialog.form.languageScore" />
            </el-form-item>
            <el-form-item label="数学成绩">
                <el-input v-model="addDialog.form.mathScore" />
            </el-form-item>
            <el-form-item label="英语成绩">
                <el-input v-model="addDialog.form.englishScore" />
            </el-form-item>
            <el-form-item label="政治成绩">
                <el-input v-model="addDialog.form.politicsScore" />
            </el-form-item>
            <el-form-item label="历史成绩">
                <el-input v-model="addDialog.form.historyScore" />
            </el-form-item>
            <el-form-item label="地理成绩">
                <el-input v-model="addDialog.form.geogScore" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="addDialog.open = false">关闭</el-button>
                <el-button type="primary" @click="add()">确定</el-button>
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

const regName = /^[\u4E00-\u9FA5]+$/;

//接收查询出来的成绩数据 并跟表格数据绑定
const scoreList = reactive({ list: [] })

//接收查询出来的考试信息
const examInfo = reactive({ list: [] })

//接收查询出来的班级信息
const classInfo = reactive({ list: [] })

//定义对象 储存条件分页查询属性
const pageObj = reactive({
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
const adminId = parseInt(sessionStorage.getItem("id"))

//选择考试编号和班级编号模态框基础值
const dialog = {
    open: false,
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

/**
 * 查询模块
 */

//查询考试信息
examApi.selectAllExam().then((r) => {
    examInfo.list = r.data.data
    // console.log(examInfo.list)
})

//查询班级信息
classApi.selectAll().then((r) => {
    if (r.data.ok) {
        classInfo.list = r.data.data
    } else {
        ElMessage.error(r.data.message)
    }
})

//成绩分页查询
const scorePage = () => {
    scoreApi.adminSelectScore(pageObj)
        .then((r) => {
            // console.log(r);
            if (r.data.ok) {
                scoreList.list = r.data.data.list
                total = r.data.data.total
            } else {
                ElMessage.error(r.data.message)
                scoreList.list= []
            }
        })
}

//查询成绩方法
const selectByExamIdClassId = () => {
    pageObj.pageNow = 1
    console.log(pageObj)
    if ((pageObj.examId == null || pageObj.examId == "") && (pageObj.classId == null || pageObj.classId == "")) {
        ElMessage.error("请先选择考试名称或班级名称")
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
    if ((pageObj.examId == null || pageObj.examId == "") && (pageObj.classId == null || pageObj.classId == "")) {
        ElMessage.error("请先选择考试编号或班级编号")
        return
    }
    scorePage()
}

//根据学生姓名和考试id查询学生具体成绩方法
const selectStuScore = () => {

    if ((pageObj.examId == null || pageObj.examId == "") && (pageObj.classId == null || pageObj.classId == "")) {
        ElMessage.error("请先选择考试编号或成绩编号")
        return
    }
    if (stuName.value.indexOf(" ") != -1) {
        ElMessage.error("输入的查询信息不能包含空格")
        return
    }
    if (stuName.value == '' || stuName.value.split(" ").join("").length == 0) {
        ElMessage.error("要查询的姓名不能为空！！！")
        return
    }
    if (!regName.test(stuName.value)) {
        ElMessage.error("要查询的姓名只能包含汉字")
        return
    }

    const selectStuScoreInFo = reactive({
        examId: pageObj.examId,
        classId: pageObj.classId,
        stuName: stuName.value
    })
    // console.log(selectStuScoreInFo)
    scoreApi.adminSelectByStuName(selectStuScoreInFo)
        .then((r) => {
            if (r.data.ok) {
                scoreList.list = r.data.data.list
                total = r.data.data.total
                console.log(r.data.data);
                ElMessage.success("查询成功")
            } else {
                ElMessage.error(r.data.message)
            }
        })

}

/**
 * 修改模块
 */
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


//表单修改方法
const doUpdate = (list) => {
    upDialog.open = true
    //对表单传进来的数据进行深拷贝  避免表单的值跟着改变
    upDialog.form = JSON.parse(JSON.stringify(list))

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
    const reg = new RegExp(/\s/)

    //对输入框成绩进行校验  不符合要求则不调用api接口

    //判断成绩是否为空
    if (upDialog.form.languageScore.toString(10).split(" ").join("").length == 0 || upDialog.form.mathScore.toString(10).split(" ").join("").length == 0 || upDialog.form.englishScore.toString(10).split(" ").join("").length == 0 || upDialog.form.politicsScore.toString(10).split(" ").join("").length == 0 || upDialog.form.historyScore.toString(10).split(" ").join("").length == 0 || upDialog.form.geogScore.toString(10).split(" ").join("").length == 0) {
        ElMessage.error("成绩不能为空，请重新输入")
        return
    }
    if (reg.test(upDialog.form.languageScore) || isNaN(Number(upDialog.form.languageScore)) || Number(upDialog.form.languageScore) > 100 || Number(upDialog.form.languageScore) < 0 || upDialog.form.languageScore.toString(10).indexOf(" ") != -1) {
        ElMessage.error("语文成绩格式错误,请重新输入")
        return
    }
    if (reg.test(upDialog.form.mathScore) || isNaN(Number(upDialog.form.mathScore)) || Number(upDialog.form.mathScore) > 100 || Number(upDialog.form.mathScore) < 0 || upDialog.form.mathScore.toString(10).indexOf(" ") != -1) {
        ElMessage.error("语文成绩格式错误,请重新输入")
        return
    }
    if (reg.test(upDialog.form.englishScore) || isNaN(Number(upDialog.form.englishScore)) || Number(upDialog.form.englishScore) > 100 || Number(upDialog.form.englishScore) < 0 || upDialog.form.englishScore.toString(10).indexOf(" ") != -1) {
        ElMessage.error("语文成绩格式错误,请重新输入")
        return
    }
    if (reg.test(upDialog.form.politicsScore) || isNaN(Number(upDialog.form.politicsScore)) || Number(upDialog.form.politicsScore) > 100 || Number(upDialog.form.politicsScore) < 0 || upDialog.form.politicsScore.toString(10).indexOf(" ") != -1) {
        ElMessage.error("语文成绩格式错误,请重新输入")
        return
    }
    if (reg.test(upDialog.form.historyScore) || isNaN(Number(upDialog.form.historyScore)) || Number(upDialog.form.historyScore) > 100 || Number(upDialog.form.historyScore) < 0 || upDialog.form.historyScore.toString(10).indexOf(" ") != -1) {
        ElMessage.error("语文成绩格式错误,请重新输入")
        return
    }
    if (reg.test(upDialog.form.geogScore) || isNaN(Number(upDialog.form.geogScore)) || Number(upDialog.form.geogScore) > 100 || Number(upDialog.form.geogScore) < 0 || upDialog.form.geogScore.toString(10).indexOf(" ") != -1) {
        ElMessage.error("语文成绩格式错误,请重新输入")
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

/**
 * 添加模块
 */

const addDialog = reactive({
    open: false,
    form: {
        scoreId: "",
        stuId: "",
        examId: "",
        classId: "",
        languageScore: "",
        mathScore: "",
        englishScore: "",
        politicsScore: "",
        historyScore: "",
        geogScore: "",
    }
})

//确定修改
const add = () => {
    const reg = new RegExp(/\s/)

    //对输入框成绩进行校验  不符合要求则不调用api接口

    //判断成绩是否为空
    if (addDialog.form.languageScore.toString(10).split(" ").join("").length == 0 || addDialog.form.mathScore.toString(10).split(" ").join("").length == 0 || addDialog.form.englishScore.toString(10).split(" ").join("").length == 0 || addDialog.form.politicsScore.toString(10).split(" ").join("").length == 0 || addDialog.form.historyScore.toString(10).split(" ").join("").length == 0 || addDialog.form.geogScore.toString(10).split(" ").join("").length == 0) {
        ElMessage.error("添加信息不能为空，请重新输入")
        return
    }
    if (reg.test(addDialog.form.languageScore) || isNaN(Number(addDialog.form.languageScore)) || Number(addDialog.form.languageScore) > 100 || Number(addDialog.form.languageScore) < 0 || addDialog.form.languageScore.toString(10).indexOf(" ") != -1) {
        ElMessage.error("语文成绩格式错误,请重新输入")
        return
    }
    if (reg.test(addDialog.form.mathScore) || isNaN(Number(addDialog.form.mathScore)) || Number(addDialog.form.mathScore) > 100 || Number(addDialog.form.mathScore) < 0 || addDialog.form.mathScore.toString(10).indexOf(" ") != -1) {
        ElMessage.error("数学成绩格式错误,请重新输入")
        return
    }
    if (reg.test(addDialog.form.englishScore) || isNaN(Number(addDialog.form.englishScore)) || Number(addDialog.form.englishScore) > 100 || Number(addDialog.form.englishScore) < 0 || addDialog.form.englishScore.toString(10).indexOf(" ") != -1) {
        ElMessage.error("英语成绩格式错误,请重新输入")
        return
    }
    if (reg.test(addDialog.form.politicsScore) || isNaN(Number(addDialog.form.politicsScore)) || Number(addDialog.form.politicsScore) > 100 || Number(addDialog.form.politicsScore) < 0 || addDialog.form.politicsScore.toString(10).indexOf(" ") != -1) {
        ElMessage.error("政治成绩格式错误,请重新输入")
        return
    }
    if (reg.test(addDialog.form.historyScore) || isNaN(Number(addDialog.form.historyScore)) || Number(addDialog.form.historyScore) > 100 || Number(addDialog.form.historyScore) < 0 || addDialog.form.historyScore.toString(10).indexOf(" ") != -1) {
        ElMessage.error("历史成绩格式错误,请重新输入")
        return
    }
    if (reg.test(addDialog.form.geogScore) || isNaN(Number(addDialog.form.geogScore)) || Number(addDialog.form.geogScore) > 100 || Number(addDialog.form.geogScore) < 0 || addDialog.form.geogScore.toString(10).indexOf(" ") != -1) {
        ElMessage.error("地理成绩格式错误,请重新输入")
        return
    }

    if (isNaN(Number(addDialog.form.scoreId)) || addDialog.form.scoreId.split(" ").join("").length == 0 || addDialog.form.scoreId.indexOf(" ") != -1) {
        ElMessage.error("成绩编号不符合格式")
        return
    }

    if (isNaN(Number(addDialog.form.stuId)) || addDialog.form.stuId.split(" ").join("").length == 0 || addDialog.form.stuId.indexOf(" ") != -1) {
        ElMessage.error("学号不符合格式")
        return
    }
    if (addDialog.form.classId + "".split(" ").join("").length == 0) {
        ElMessage.error("班级名称不能为空")
        return
    }
    if (addDialog.form.examId + "".split(" ").join("").length == 0) {
        ElMessage.error("考试名称不能为空")
        return
    }
    scoreApi.addScore(addDialog.form)
        .then((r) => {
            if (r.data.ok) {
                // console.log(pageObj)
                ElMessage.success("添加成功")
                if ((pageObj.examId == null || pageObj.examId == "") && (pageObj.classId == null || pageObj.classId == "")) {
                    pageObj.classId = addDialog.form.classId
                    pageObj.examId = addDialog.form.examId
                }
                selectByExamIdClassId()
                addDialog.open = false
            } else {
                ElMessage.error(r.data.message)
            }
        })
}

/**
 * 删除模块
 */
const deleteByid = async (row) => {
    //await关键字触发异步操作进行阻塞
    if (await ElMessageBox.confirm('确认删除此条成绩信息吗?', '提示', {
        distinguishCancelAndClose: true,
        confirmButtonText: '删除',
        cancelButtonText: '取消'
    }).catch(() => {
        ElMessage.info("已取消删除")
    }) !== 'confirm') {
        return
    }
    scoreApi.deleteById(row.scoreId)
        .then((r) => {
            if (r.data.ok) {
                ElMessage.success("删除成功")
                selectByExamIdClassId()
            } else {
                ElMessage.error(r.data.message)
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