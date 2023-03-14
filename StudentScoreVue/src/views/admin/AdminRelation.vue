<template>
    <div>
        <el-card class="box-card" style="width: 1200px;">
            <template #header>
                <div class="card-header">
                    <span>班级课程授课教师信息</span>
                    <el-button type="primary" class="button" @click="pageSelect()"
                        style="margin-left: -135px;">返回全部列表</el-button>
                    <el-button type="primary" class="button" @click="addDialog.open = true"
                        style="margin-left: -440px;">添加</el-button>
                    <el-select v-model="flagId" filterable placeholder="请选择查询条件" @change="handleChange" style="margin-left:100px">
                        <el-option v-for="item in selectCondition.list" :key="item.id" :label="item.name"
                            :value="item.id" />
                    </el-select>
                    <el-row :gutter="20" style="margin-right:-260px ;margin-left:-260px ;">
                        <el-input v-model="selectId" class="w-50 m-2" placeholder="请输入编号" :prefix-icon="Search" />
                    </el-row>
                    <el-button type="primary" class="button" @click="selectByid()">查询</el-button>
                </div>
            </template>
            <el-table :data="rellist.list" style="width: 100%" @sort-change="sortTable" align="center">
                <!-- pror要跟传递过来的数据字段名相同 -->
                <el-table-column prop="relId" label="id" width="110" />
                <el-table-column prop="classId" label="班级编号" width="110" />
                <el-table-column prop="className" label="班级名称" width="110" />
                <el-table-column prop="courseId" label="课程编号" width="110" />
                <el-table-column prop="courseName" label="课程名称" width="110" />
                <el-table-column prop="teaId" label="教师编号" width="110" />
                <el-table-column prop="teaName" label="教师名称" width="110" />
                <el-table-column fixed="right" label="操作" width="160">
                    <template #default="scope">
                        <el-button-group>
                            <el-button size="small" type="warning" @click="doUpdate(scope.row)">修改</el-button>
                            <el-button size="small" type="danger" @click="deleteByid(scope.row)">删除</el-button>
                        </el-button-group>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination background style="padding-top: 15px" @size-change="pageSelect" @current-change="pageSelect"
                v-model:current-page.sync="pageNow" :page-sizes="[2, 4, 6, 8, 10]" v-model:page-size="size"
                layout="total, sizes, prev, pager, next, jumper" v-model:total="total">
            </el-pagination>
        </el-card>
    </div>
    <!-- 添加模态框 -->
    <el-dialog v-model="addDialog.open" title="考试信息添加" width="600px" draggable>
        <el-form label-position="right" label-width="180px" :model="addDialog.form">
            <el-form-item label="关联id">
                <el-input v-model="addDialog.form.relId" />
            </el-form-item>
            <el-form-item label="班级编号">
                <el-input v-model="addDialog.form.classId" />
            </el-form-item>
            <el-form-item label="课程编号">
                <el-input v-model="addDialog.form.courseId" />
            </el-form-item>
            <el-form-item label="教师编号">
                <el-input v-model="addDialog.form.teaId" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="addDialog.open = false">关闭</el-button>
                <el-button type="primary" @click="add()">添加</el-button>
            </span>
        </template>
    </el-dialog>
    <!-- 修改模态框 -->
    <el-dialog v-model="upDialog.open" title="考试信息修改" width="600px" draggable>
        <el-form label-position="right" label-width="180px" :model="upDialog.form">
            <el-form-item label="关联id">
                <el-input v-model="upDialog.form.relId" readonly="false" />
            </el-form-item>
            <el-form-item label="班级编号">
                <el-input v-model="upDialog.form.classId" readonly="false" />
            </el-form-item>
            <el-form-item label="课程编号">
                <el-input v-model="upDialog.form.courseId" readonly="false" />
            </el-form-item>
            <el-form-item label="教师编号">
                <el-input v-model="upDialog.form.teaId" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="upDialog.open = false">关闭</el-button>
                <el-button type="primary" @click="updata">修改</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>

import { ref, onMounted, reactive } from "vue"
import { ElMessage, ElMessageBox } from "element-plus"
import scoreApi from "../../api/score"
import examApi from "../../api/exam"
import { Search } from '@element-plus/icons-vue'
import classApi from '../../api/class'
import { useRouter } from "vue-router"
import exam from "../../api/exam"
import relApi from "../../api/relation"

//绑定查询条件选择框
const flagId = ref("")

//绑定查询框
const selectId=ref("")

//接收查询出来的学生信息 并跟表格数据绑定
const rellist = reactive({ list: [] })

//设置初始分页信息
// 每页显示条目个数
let size = 10
// 总条目数
let total = 0
//当前页数
let pageNow = 1

const router = useRouter();
const adminId = sessionStorage.getItem("id")

const pageSelect = () => {
    relApi.selectPageAll(pageNow, size)
        .then((r) => {
            if (r.data.ok) {
                rellist.list = r.data.data.list
                total = r.data.data.total
            }
        })
}

pageSelect()

//添加模态框
const addDialog = reactive({
    open: false,
    form: {
        relId: "",
        classId: "",
        teaId: "",
        courseId: "",
    }
})

//添加
const add = () => {
    if (addDialog.form.relId.split(" ").join("").length == 0 || addDialog.form.classId.split(" ").join("").length == 0 || addDialog.form.teaId.split(" ").join("").length == 0 || addDialog.form.courseId.split(" ").join("").length == 0) {
        ElMessage.error("添加信息不能为空")
        return
    }
    if (isNaN(addDialog.form.relId) || isNaN(addDialog.form.classId) || isNaN(addDialog.form.teaId) || isNaN(addDialog.form.courseId)) {
        ElMessage.error("所填添加信息不符合规范")
        return
    }
    if (addDialog.form.relId.indexOf(" ") != -1 || addDialog.form.classId.indexOf(" ") != -1 || addDialog.form.teaId.indexOf(" ") != -1 || addDialog.form.courseId.indexOf(" ") != -1) {
        ElMessage.error("所填添加信息不能含有空格")
        return
    }
    relApi.addRel(addDialog.form)
        .then((r) => {
            if (r.data.ok) {
                ElMessage.success("添加成功")
                addDialog.open = false
                addDialog.form.relId = ""
                addDialog.form.classId = ""
                addDialog.form.teaId = ""
                addDialog.form.courseId = ""
                pageSelect()
            } else {
                ElMessage.error(r.data.message)
            }
        })
}


//删除
const deleteByid = (row) => {
    relApi.deleteById(row.relId)
        .then((r) => {
            if (r.data.ok) {
                ElMessage.success("删除成功")
                pageSelect()
            } else {
                ElMessage.error(r.data.message)
            }
        })
}

const upDialog = reactive(
    {
        open: false,
        form: {
            relId: "",
            classId: "",
            teaId: "",
            courseId: "",
        }
    }
)

var tempTeaId
const doUpdate = (flag) => {
    upDialog.open = true
    upDialog.form = JSON.parse(JSON.stringify(flag))
    tempTeaId = JSON.parse(JSON.stringify(flag.teaId))
    console.log(upDialog.form);
}

//修改 添加async执行异步操作阻塞消息弹出框
const updata = async () => {
    if (tempTeaId == upDialog.form.teaId) {
        ElMessage.info("信息没有变动")
        return
    }
    if (upDialog.form.teaId.toString(10).indexOf(" ") != -1) {
        ElMessage.error("教师编号不能包含空格")
        return
    }
    if (upDialog.form.teaId + "".split(" ").join("").length == 0) {
        ElMessage.error("教师编号不能为空")
        return
    }

    if (isNaN(upDialog.form.teaId)) {
        ElMessage.error("教师编号不符合规范")
        return
    }
    relApi.updateById(upDialog.form).then((r) => {
        if (r.data.ok) {
            ElMessage.success("修改成功")
            upDialog.open = false
            pageSelect()
        } else {
            ElMessage.error(r.data.message)
        }
    })
}

const selectCondition = reactive({
    list: [
        {
            id: 1,
            name: "按班级编号查询"
        },
        {
            id: 2,
            name: "按教师编号查询"
        },
        {
            id: 3,
            name: "按课程编号查询"
        }]
})


const selectByid = () => {
    const temp=reactive({
        data:{
            teaId:"",
            classId:"",
            courseId:""
        }
    })

    if(flagId.value.toString(10).split(" ").join("").length == 0){
        ElMessage.error("请先选择查询条件")
        return
    }
    if (selectId.value.indexOf(" ") != -1) {
        ElMessage.error("输入的查询信息不能包含空格")
        return
    }
    if (selectId.value.split(" ").join("").length == 0) {
        ElMessage.error("输入的查询信息不能为空")
        return
    }
    if (isNaN(Number(selectId.value))) {
        ElMessage.error("输入的查询信息不符合格式")
        return
    }

    if(flagId.value==1){
        temp.data.classId=selectId.value
    }else if(flagId.value==2){
        temp.data.teaId=selectId.value
    }else{
        temp.data.courseId=selectId.value
    }

    relApi.selectById(temp.data)
    .then((r)=>{
        if(r.data.ok){
            rellist.list=r.data.data.list
            total=r.data.data.total
            ElMessage.success("查询成功")
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