<template>
    <div>
        <el-card class="box-card" style="width: 1280px;">
            <template #header>
                <div class="card-header">
                    <span>教师信息</span>
                    <el-button type="primary" class="button" @click="selectStuPage"
                        style="margin-left:-500px ;">返回教师信息列表</el-button>
                    <el-button type="primary" class="button" @click="addDialog.open = true"
                        style="margin-left: -580px;">添加</el-button>
                    <el-select v-model="flagId" filterable placeholder="请选择查询条件" @change="handleChange"
                        style="margin-left:-200px">
                        <el-option v-for="item in selectCondition.list" :key="item.id" :label="item.name"
                            :value="item.id" />
                    </el-select>
                    <el-row :gutter="20" style="margin-right: -585px;margin-left: -585px;">
                        <el-input v-model="selectId" class="w-50 m-2" placeholder="请输入查询信息" :prefix-icon="Search" />
                    </el-row>
                    <el-button type="primary" class="button" @click="selectTeaInfo">查询</el-button>
                </div>
            </template>
            <el-table :data="teaList.list" style="width: 100%" @sort-change="sortTable" align="center">
                <!-- pror要跟传递过来的数据字段名相同 -->
                <el-table-column prop="teaId" label="教师编号" width="100" />
                <el-table-column prop="teaName" label="教师姓名" width="100" />
                <el-table-column prop="teaTel" label="电话号码" width="160" />
                <el-table-column prop="teaEmial" label="邮箱号" width="250" />
                <el-table-column prop="teaIdentity" label="身份证号码" width="200" />
                <el-table-column prop="teaPassword" label="教师密码" width="120" />
                <el-table-column fixed="right" label="操作" width="120">
                    <template #default="scope">
                        <el-button-group>
                            <el-button size="small" type="warning" @click="doUpdate(scope.row)">修改</el-button>
                            <el-button size="small" type="danger" @click="deleteByid(scope.row)">删除</el-button>
                        </el-button-group>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination background style="padding-top: 15px" @size-change="selectByClassIdOrStuName"
                @current-change="selectByClassIdOrStuName" v-model:current-page.sync="pageNow"
                :page-sizes="[2, 4, 6, 8, 10]" v-model:page-size="size" layout="total, sizes, prev, pager, next, jumper"
                v-model:total="total">
            </el-pagination>
        </el-card>
    </div>
    <!-- 修改模态框 -->
    <el-dialog v-model="upDialog.open" title="教师信息修改" width="600px" draggable>
        <el-form label-position="right" label-width="180px" :model="upDialog.form">
            <el-form-item label="教师编号">
                <el-input readonly="false" v-model="upDialog.form.teaId" />
            </el-form-item>
            <el-form-item label="教师姓名">
                <el-input v-model="upDialog.form.teaName" />
            </el-form-item>
            <el-form-item label="电话号码">
                <el-input v-model="upDialog.form.teaTel" />
            </el-form-item>
            <el-form-item label="邮箱号">
                <el-input v-model="upDialog.form.teaEmial" />
            </el-form-item>
            <el-form-item label="身份证号码">
                <el-input v-model="upDialog.form.teaIdentity" />
            </el-form-item>
            <el-form-item label="教师密码">
                <el-input v-model="upDialog.form.teaPassword" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="upDialog.open = false">关闭</el-button>
                <el-button type="primary" @click="doit()">修改</el-button>
            </span>
        </template>
    </el-dialog>

    <!-- 添加模态框 -->
    <el-dialog v-model="addDialog.open" title="教师信息添加" width="600px" draggable>
        <el-form label-position="right" label-width="180px" :model="addDialog.form">
            <el-form-item label="教师编号">
                <el-input v-model="addDialog.form.teaId" />
            </el-form-item>
            <el-form-item label="教师姓名">
                <el-input v-model="addDialog.form.teaName" />
            </el-form-item>
            <el-form-item label="电话号码">
                <el-input v-model="addDialog.form.teaTel" />
            </el-form-item>
            <el-form-item label="邮箱号">
                <el-input v-model="addDialog.form.teaEmial" />
            </el-form-item>
            <el-form-item label="身份证号码">
                <el-input v-model="addDialog.form.teaIdentity" />
            </el-form-item>
            <el-form-item label="教师密码">
                <el-input v-model="addDialog.form.teaPassword" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="addDialog.open = false">关闭</el-button>
                <el-button type="primary" @click="add()">添加</el-button>
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
import teacherApi from '../../api/teacher'
import { number } from "echarts";

//接收查询出来的教师信息 并跟表格数据绑定
const teaList = reactive({ list: [] })

//验证参数格式正则表达式
const regName = /^[\u4E00-\u9FA5]+$/;
const reg = new RegExp("[\\u4E00-\\u9FFF]+", "g");
const reg_tel = new RegExp(/^(13[0-9]{9})|(15[0-9]{9})|(17[0-9]{9})|(18[0-9]{9})|(19[0-9]{9}|(14[0-9]{9}))$/)
const reg_emial = new RegExp(/^[A-Za-z0-9\u4e00-\u9fa5]+[._-]*[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/)
const reg_identity = new RegExp(/^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]/)

//设置初始分页信息
// 每页显示条目个数
let size = 10
// 总条目数
let total = 0
//当前页数
let pageNow = 1


//定义变量
var flag = true

/**
 * 查询模块
 */

//分页查询全部教师信息
const selectStuPage = () => {
    flag = true
    teacherApi.selectPage(pageNow, size)
        .then((r) => {
            if (r.data.ok) {
                teaList.list = r.data.data.records
                total = r.data.data.total
            } else {
                ElMessage.error(r.data.message)
            }
        })
}
selectStuPage()

//选择框信息
const selectCondition = reactive({
    list: [
        {
            id: 1,
            name: "按教师编号查询"
        },
        {
            id: 2,
            name: "按教师姓名查询"
        }
    ]
})

//绑定查询条件选择框
const flagId = ref("")

//绑定查询框
const selectId = ref("")

//根据班级编号或者教师姓名分页查询教师信息
const selectTeaInfo = () => {
    const temp = reactive({
        data: {
            teaId: "",
            teaName: "",
            size: size,
            pageNow: pageNow
        }
    })

    if (flagId.value.toString(10).split(" ").join("").length == 0) {
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
    if (isNaN(Number(selectId.value)) && flagId.value == 1) {
        ElMessage.error("输入的查询信息不符合格式")
        return
    }
    if (!regName.test(selectId.value) && flagId.value == 2) {
        ElMessage.error("输入的查询信息不符合格式")
        return
    }

    if (flagId.value == 1) {
        temp.data.teaId = selectId.value
        temp.data.teaName = null
    } else if (flagId.value == 2) {
        temp.data.teaName = selectId.value
    }

    teacherApi.selectByTeaIdOrTeaName(temp.data)
        .then((r) => {
            if (r.data.ok) {
                teaList.list = r.data.data.list
                total = r.data.data.total
                flag = false
            } else {
                ElMessage.error(r.data.message)
            }
        })
}

const selectByClassIdOrStuName = () => {
    if (flag) {
        selectStuPage()
    } else {
        selectStuInfo()
    }
}

/**
 * 修改模块
 */
const upDialog = reactive(
    {
        open: false,
        form: {
            teaId: "",
            teaName: "",
            teaTel: "",
            teaEmial: "",
            teaIdentity: "",
            teaPassword: "",
        }
    }
)

const doUpdate = (flag) => {
    upDialog.open = true
    upDialog.form = JSON.parse(JSON.stringify(flag))
}

const doit = () => {

    //先对数据进行非空判断
    if (upDialog.form.teaName == "" || upDialog.form.teaName.split(" ").join("").length == 0) {
        ElMessage.error("教师姓名不能为空")
        return
    }
    if (upDialog.form.teaIdentity == "" || upDialog.form.teaIdentity.split(" ").join("").length == 0) {
        ElMessage.error("身份证号码不能为空")
        return
    }
    if (upDialog.form.teaPassword == "" || upDialog.form.teaPassword.split(" ").join("").length == 0) {
        ElMessage.error("教师密码不能为空")
        return
    }
  
    if (upDialog.form.teaName.indexOf(" ") != -1) {
        ElMessage.error("修改后的教师姓名不能包含空格")
        return
    }
   
    if (upDialog.form.teaIdentity.indexOf(" ") != -1) {
        ElMessage.error("修改后的身份证号不能包含空格")
        return
    }

    if (upDialog.form.teaPassword.indexOf(" ") != -1) {
        ElMessage.error("修改后的密码不能包含空格")
        return
    }
 
    if (upDialog.form.teaTel.indexOf(" ") != -1) {
        ElMessage.error("修改后的电话号码不能包含空格")
        return
    }
    if (upDialog.form.teaEmial.indexOf(" ") != -1) {
        ElMessage.error("修改后的电子邮箱号不能包含空格")
        return
    }

    //格式判断
    //手机号码格式判断
    if (upDialog.form.teaTel.split(" ").join("").length != 0 && !reg_tel.test(upDialog.form.teaTel)) {
        ElMessage.error("请输入正确的手机号码")
        return
    }
    //对身份证号格式判断
    if (upDialog.form.teaIdentity.split(" ").join("").length != 0 && !reg_identity.test(upDialog.form.teaIdentity)) {
        ElMessage.error("请输入正确的身份证号码")
        return
    }
    //对邮箱号格式判断
    if (upDialog.form.teaEmial.split(" ").join("").length != 0 && !reg_emial.test(upDialog.form.teaEmial)) {
        ElMessage.error("请输入正确的邮箱号码")
        return
    }


    if (/.*[\u4e00-\u9fa5]+.*$/.test(upDialog.form.teaPassword)) {
        ElMessage.error("密码不能含有汉字")
        return
    }

    if (!regName.test(upDialog.form.teaName)) {
        ElMessage.error("修改后的教师姓名只能包含汉字")
        return
    }
    teacherApi.update(upDialog.form)
        .then((r) => {
            if (r.data.ok) {
                ElMessage.success("修改成功")
                upDialog.open = false
                selectStuPage()
            } else {
                ElMessage.error(r.data.message)
            }
        })

}

/**
 * 添加模块
 */

//添加模态框
const addDialog = reactive({
    open: false,
    form: {
        teaId: "",
        teaName: "",
        teaTel: "",
        teaEmial: "",
        teaIdentity: "",
        teaPassword: "",
    }
})

//添加方法
const add = () => {
    //先对数据进行非空判断
    if (addDialog.form.teaId =="" ||addDialog.form.teaId.split(" ").join("").length == 0) {
        ElMessage.error("教师编号不能为空")
        return
    }

    if (addDialog.form.teaName == "" || addDialog.form.teaName.split(" ").join("").length == 0) {
        ElMessage.error("教师姓名不能为空")
        return
    }
    //upDialog.form.classId+""转为字符串
    if (addDialog.form.classId == "" || addDialog.form.classId + "".split(" ").join("").length == 0) {
        ElMessage.error("班级编号不能为空")
        return
    }
    if (addDialog.form.teaIdentity == "" || addDialog.form.teaIdentity.split(" ").join("").length == 0) {
        ElMessage.error("身份证号码不能为空")
        return
    }
    if (addDialog.form.teaPassword == "" || addDialog.form.teaPassword.split(" ").join("").length == 0) {
        ElMessage.error("教师密码不能为空")
        return
    }
  

    if (addDialog.form.teaId.indexOf(" ") != -1) {
        ElMessage.error("添加的教师编号不能包含空格")
        return
    }
    if (addDialog.form.teaName.indexOf(" ") != -1) {
        ElMessage.error("添加的教师姓名不能包含空格")
        return
    }
    if (addDialog.form.teaIdentity.indexOf(" ") != -1) {
        ElMessage.error("添加的身份证号不能包含空格")
        return
    }

    if (addDialog.form.teaPassword.indexOf(" ") != -1) {
        ElMessage.error("添加的密码不能包含空格")
        return
    }
    if (addDialog.form.teaTel.indexOf(" ") != -1) {
        ElMessage.error("添加的电话号码不能包含空格")
        return
    }
    if (addDialog.form.teaEmial.indexOf(" ") != -1) {
        ElMessage.error("添加的电子邮箱号不能包含空格")
        return
    }

    //格式判断
    //手机号码格式判断
    if (addDialog.form.teaTel.split(" ").join("").length != 0 && !reg_tel.test(addDialog.form.teaTel)) {
        ElMessage.error("请添加正确的手机号码")
        return
    }
    //对身份证号格式判断
    if (addDialog.form.teaIdentity.split(" ").join("").length != 0 && !reg_identity.test(addDialog.form.teaIdentity)) {
        ElMessage.error("请添加正确的身份证号码")
        return
    }
    //对邮箱号格式判断
    if (addDialog.form.teaEmial.split(" ").join("").length != 0 && !reg_emial.test(addDialog.form.teaEmial)) {
        ElMessage.error("请添加正确的邮箱号码")
        return
    }

    if (isNaN(Number(addDialog.form.teaId))) {
        ElMessage.error("教师编号不符合格式")
        return
    }

    if (/.*[\u4e00-\u9fa5]+.*$/.test(addDialog.form.teaPassword)) {
        ElMessage.error("密码不能含有汉字")
        return
    }

    if (!regName.test(addDialog.form.teaName)) {
        ElMessage.error("添加的教师姓名只能包含汉字")
        return
    }

    teacherApi.addTeacher(addDialog.form)
    .then((r)=>{
        if (r.data.ok) {
            ElMessage.success("添加成功")
            addDialog.open=false
            selectStuPage()
        }else{
            ElMessage.error(r.data.message)
        }
    })
}

/**
 * 删除模块
 */
//删除
const deleteByid = (row) => {
    teacherApi.deleteById(row.teaId)
        .then((r) => {
            if (r.data.ok) {
                ElMessage.success("删除成功")
                selectStuPage()
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