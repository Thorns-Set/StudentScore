<template>
    <div>
        <el-card class="box-card" style="width: 1000px;">
            <template #header>
                <div class="card-header">
                    <span>班级信息</span>
                    <el-button type="primary" class="button" @click="pageSelect()" style="margin-left: -430px;">返回班级列表</el-button>
                    <el-button type="primary" class="button" @click="addDialog.open = true"
                        style="margin-left: -430px;">添加</el-button>
                    <el-row :gutter="20" style="margin-right:-430px ;margin-left: ;">
                        <el-input v-model="classid" class="w-50 m-2" placeholder="请输入班级编号" :prefix-icon="Search" />
                    </el-row>
                    <el-button type="primary" class="button" @click="selectByid()">查询</el-button>
                </div>
            </template>
            <el-table :data="classlist.list" style="width: 100%" @sort-change="sortTable" align="center">
                <!-- pror要跟传递过来的数据字段名相同 -->
                <el-table-column prop="classId" label="班级编号" width="110" />
                <el-table-column prop="className" label="班级名称" width="120" />
                <el-table-column fixed="right" label="操作" width="160">
                    <template #default="scope">
                        <el-button-group>
                            <el-button size="small" type="warning" @click="doUpdate(scope.row)">修改</el-button>
                            <el-button size="small" type="danger" @click="deleteByid(scope.row)">删除</el-button>
                        </el-button-group>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination background style="padding-top: 15px" @size-change="selectStuPage" @current-change="selectStuPage"
                v-model:current-page.sync="pageNow" :page-sizes="[2, 4, 6, 8, 10]" v-model:page-size="size"
                layout="total, sizes, prev, pager, next, jumper" v-model:total="total">
            </el-pagination>
        </el-card>
    </div>
    <!-- 添加模态框 -->
    <el-dialog v-model="addDialog.open" title="班级信息添加" width="600px" draggable>
        <el-form label-position="right" label-width="180px" :model="addDialog.form">
            <el-form-item label="班级编号">
                <el-input v-model="addDialog.form.classId" />
            </el-form-item>
            <el-form-item label="班级名称">
                <el-input v-model="addDialog.form.className" />
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
    <el-dialog v-model="upDialog.open" title="班级信息修改" width="600px" draggable>
        <el-form label-position="right" label-width="180px" :model="upDialog.form">
            <el-form-item label="班级编号">
                <el-input v-model="upDialog.form.classId" readonly="false" />
            </el-form-item>
            <el-form-item label="班级名称">
                <el-input v-model="upDialog.form.className" />
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

import { ref, onMounted, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import scoreApi from "../../api/score"
import examApi from "../../api/exam"
import { Search } from '@element-plus/icons-vue'
import classApi from '../../api/class'
import { useRouter } from "vue-router"

const classid=ref("")

//接收查询出来的学生信息 并跟表格数据绑定
const classlist = reactive({ list: [] })

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
    classApi.selectClassPage(size, pageNow)
        .then((r) => {
            if (r.data.ok) {
                // console.log(r.data.data);
                classlist.list = r.data.data.records
                total = r.data.data.total
            }
        })
}

pageSelect()

//添加模态框
const addDialog = reactive({
    open: false,
    form: {
        classId: "",
        className: ""
    }
})

//添加
const add = async () => {
    if (addDialog.form.classId.split(" ").join("").length == 0 || addDialog.form.className.split(" ").join("").length == 0) {
        ElMessage.error("添加信息不能为空")
        return
    }
    if (isNaN(addDialog.form.classId) || isNaN(addDialog.form.className) || addDialog.form.classId.indexOf(" ") != -1 || addDialog.form.className.indexOf(" ") != -1) {
        ElMessage.error("添加信息不符合规范请重新添加")
        addDialog.form.classId = ""
        addDialog.form.className = ""
        return
    }
    for (const key in classlist.list) {
        if (classlist.list[key].classId == addDialog.form.classId) {
            ElMessage.error("班级编号重复请修改")
            return
        }
    }
    for (const key in classlist.list) {
        if (classlist.list[key].className == addDialog.form.className) {
            if (await ElMessageBox.confirm('修改后的班级名称与现有班级名称重复，确认添加吗?', '提示', {
                distinguishCancelAndClose: true,
                confirmButtonText: '添加',
                cancelButtonText: '取消'
            }).catch(() => {
                ElMessage.info("已取消添加")
             }) !== 'confirm') {
                return
            }
        }
    }
    classApi.addClass(addDialog.form)
        .then((r) => {
            if (r.data.ok) {
                ElMessage.success("添加成功")
                addDialog.open = false
                addDialog.form.classId = ""
                addDialog.form.className = ""
                pageSelect()
            }
        })
}


//删除
const deleteByid = (row) => {
    classApi.deleteByid(row.classId).then((r) => {
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
            classId: "",
            className: ""
        }
    }
)

const doUpdate = (flag) => {
    upDialog.open = true
    upDialog.form = JSON.parse(JSON.stringify(flag))
}

//修改 添加async执行异步操作阻塞消息弹出框
const updata = async () => {
    if (upDialog.form.className.toString(10).indexOf(" ") != -1) {
        ElMessage.error("班级名字不能包含空格")
        return
    }
    if (upDialog.form.className == "" || upDialog.form.className + "".split(" ").join("").length == 0) {
        ElMessage.error("班级名字不能为空")
        return
    }
    if (isNaN(Number(upDialog.form.className))) {
        ElMessage.error("班级名字只能是数字")
        return
    }
    for (const key in classlist.list) {
        console.log(key);
        if (classlist.list[key].className == upDialog.form.className) {

            // ElMessageBox.confirm(
            //     '修改后的班级名称与现有班级名称重复，确认修改吗?',
            //     '提示',
            //     {
            //         showCancelButton: true,
            //         confirmButtonText: '确认',
            //         cancelButtonText: '取消',
            //         type: 'warning',
            //     }
            // ).then(() => {

            // }).catch(() => {
            //     ElMessage({
            //         type: 'info',
            //         message: '已取消修改',
            //     })
            //     console.log("wanc");
            //     return
            // })

            //await关键字触发异步操作进行阻塞
            if (await ElMessageBox.confirm('修改后的班级名称与现有班级名称重复，确认修改吗?', '提示', {
                distinguishCancelAndClose: true,
                confirmButtonText: '修改',
                cancelButtonText: '取消'
            }).catch(() => {
                ElMessage.info("已取消修改")
             }) !== 'confirm') {
                return
            }
        }
    }
    classApi.updateByid(upDialog.form).then((r)=>{
        if(r.data.ok){
            ElMessage.success("修改成功")
            addDialog.open=false
            pageSelect()
        }
    })
}


const selectByid=()=>{
    if (classid.value.indexOf(" ") != -1) {
        ElMessage.error("输入的查询信息不能包含空格")
        return
    }
    if (classid.value.split(" ").join("").length == 0) {
        ElMessage.error("输入的查询信息不能为空")
        return
    }
    if (isNaN(Number(classid.value))) {
        ElMessage.error("输入的查询信息只能是数字")
        return
    }
    classApi.selectByid(classid.value).then((r)=>{
        if(r.data.ok){
            const flag=reactive({
                list:[r.data.data]
            })
            classlist.list=flag.list
            total=1
            ElMessage.success("查询成功")
        }else{
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