<template>
    <div>
        <el-card class="box-card" style="width: 1200px;">
            <template #header>
                <div class="card-header">
                    <span>班级课程授课教师信息</span>
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
            </el-table>
            <el-pagination background style="padding-top: 15px" @size-change="pageSelect" @current-change="pageSelect"
                v-model:current-page.sync="pageNow" :page-sizes="[2, 4, 6, 8, 10]" v-model:page-size="size"
                layout="total, sizes, prev, pager, next, jumper" v-model:total="total">
            </el-pagination>
        </el-card>
    </div>
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
import stuApi from '../../api/student'


//接收查询出来的学生信息 并跟表格数据绑定
const rellist = reactive({ list: [] })

//设置初始分页信息
// 每页显示条目个数
let size = 10
// 总条目数
let total = 0
//当前页数
let pageNow = 1

const stuId = sessionStorage.getItem("id")
var classId


const getclassID = () => {
    stuApi.selectById(stuId)
        .then((r) => {
            classId = r.data.data.classId
            selectByid()
        })

}
getclassID()

const selectByid = () => {
    const temp = reactive({
        data: {
            teaId: "",
            classId: classId,
            courseId: ""
        }
    })
    relApi.selectById(temp.data)
        .then((r) => {
            if (r.data.ok) {
                rellist.list = r.data.data.list
                total = r.data.data.total
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