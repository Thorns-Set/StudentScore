<template>
    <el-card class="box-card">
        <template #header>
            <div class="card-header">
                <span>基本个人信息</span>
                <el-button-group class="ml-4">
                    <el-button type="primary" :icon="Edit" @click="doUpdate(student)">修改个人信息</el-button>
                    <el-button type="primary" :icon="Edit" @click="doUpdatepassword()">修改密码</el-button>
                </el-button-group>
            </div>
        </template>
        <div class="text item" v-if="temp">学号：{{ student.stuId }}</div>
        <div class="text item" v-if="temp">学生姓名：{{ student.stuName }}</div>
        <div class="text item" v-if="temp">学生性别：{{ student.stuSex }}</div>
        <div class="text item" v-if="temp">学生电话号码：{{ student.stuTel }}</div>
        <div class="text item" v-if="temp">学生电子邮件：{{ student.stuEmial }}</div>
        <div class="text item" v-if="temp">学生身份证号码：{{ student.stuIdentity }}</div>
        <div class="text item" v-if="temp">学生班级编号：{{ student.classId }}</div>
        <div class="text item" v-if="temp">学生班级名称：{{ classInfo.className }}</div>
    </el-card>

    <!-- 修改个人信息页面模态框 -->
    <el-dialog v-model="dialog.open" :title="dialog.title + '对话框'" width="500px" draggable>
        <el-form label-position="right" label-width="120px" :model="dialog.form">
            <el-form-item label="学号">
                <el-input :readonly="dialog.readonly" v-model="dialog.form.stuId" />
            </el-form-item>
            <el-form-item label="学生姓名">
                <el-input v-model="dialog.form.stuName" />
            </el-form-item>
            <el-form-item label="学生性别">
                <el-input v-model="dialog.form.stuSex" />
            </el-form-item>
            <el-form-item label="学生电话号码">
                <el-input v-model="dialog.form.stuTel" />
            </el-form-item>
            <el-form-item label="学生电子邮件">
                <el-input v-model="dialog.form.stuEmial" />
            </el-form-item>
            <el-form-item label="学生身份证号码">
                <el-input v-model="dialog.form.stuIdentity" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialog.open = false">关闭</el-button>
                <el-button type="primary" @click="doit()">{{ dialog.title }}</el-button>
            </span>
        </template>
    </el-dialog>

    <!-- 修改个人密码页面模态框 -->
    <el-dialog v-model="passwordData.open" :title="passwordData.title" width="500px" draggable>
        <el-form label-position="right" label-width="120px" :model="passwordData.form">
            <el-form-item label="原密码">
                <el-input v-model="passwordData.form.stuOldPassword" type="password" placeholder="请输入原密码" />
            </el-form-item>
            <el-form-item label="新密码">
                <el-input v-model="passwordData.form.stuPassword" type="password" placeholder="请输入新密码" />
            </el-form-item>
            <el-form-item label="确认密码">
                <el-input v-model="passwordData.form.flag" type="password" placeholder="请再输入一遍新密码确认" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="passwordData.open = false">关闭</el-button>
                <el-button type="primary" @click="update(passwordData)">修改</el-button>
            </span>
        </template>
    </el-dialog>
</template>
  
<script setup>

import { ref, onMounted, reactive } from "vue";
import { Edit } from '@element-plus/icons-vue'
import { ElMessage } from "element-plus";
import stuApi from '../../api/student'
import classApi from '../../api/class'

//通过sessionStorage获取当前对象的用户信息
const stuId = parseInt(sessionStorage.getItem("id"))

const temp = ref(false)
let student
let classInfo

const getstudent = () => {
    stuApi.selectById(stuId)
        .then((r) => {
            if (r.data.ok) {
                student = r.data.data
                classApi.selectByid(student.classId)
                    .then((r) => {
                        classInfo = r.data.data
                        temp.value = true
                    })
            }
        })
}
getstudent()


//修改教师基本信息
//原始数据
const dialogData = {
    open: false,
    title: "修改",
    readonly: false,
    form: {
        stuId: "",
        stuName: "",
        stuSex: "",
        stuTel: "",
        stuEmial: "",
        stuIdentity: ""
    },
};

//将数据转换为json格式
const dialog = reactive(JSON.parse(JSON.stringify(dialogData)));

const doit = () => {
    const reg_tel = new RegExp(/^(13[0-9]{9})|(15[0-9]{9})|(17[0-9]{9})|(18[0-9]{9})|(19[0-9]{9}|(14[0-9]{9}))$/)
    const reg_emial = new RegExp(/^[A-Za-z0-9\u4e00-\u9fa5]+[._-]*[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/)
    const reg_identity = new RegExp(/^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]/)

    //非空判断
    if (dialog.form.stuName.split(" ").join("").length == 0) {
        ElMessage.error("学生姓名不能为空")
        return
    }
    if (dialog.form.stuIdentity.split(" ").join("").length == 0) {
        ElMessage.error("身份证号不能为空")
        return
    }
    if (dialog.form.stuName.indexOf(" ") != -1 || dialog.form.stuTel.indexOf(" ") != -1 || dialog.form.stuIdentity.indexOf(" ") != -1 || dialog.form.stuEmial.indexOf(" ") != -1) {
        ElMessage.error("修改后的数据格式不正确请重新修改,数据不能包含空格")
        return
    }

    //格式判断
    //手机号码格式判断
    if (dialog.form.stuTel.split(" ").join("").length != 0 && !reg_tel.test(dialog.form.stuTel)) {
        ElMessage.error("请输入正确的手机号码")
        return
    }
    //对身份证号格式判断
    if (!reg_identity.test(dialog.form.stuIdentity)) {
        ElMessage.error("请输入正确的身份证号码")
        return
    }
    //对邮箱号格式判断
    if (dialog.form.stuEmial.split(" ").join("").length != 0 && !reg_emial.test(dialog.form.stuEmial)) {
        ElMessage.error("请输入正确的邮箱号码")
        return
    }
    if (dialog.form.stuSex != "男" && dialog.form.stuSex != "女") {
        ElMessage.error("学生性别格式错误")
        return
    }
    const regName = /^[\u4E00-\u9FA5]+$/;
    if (!regName.test(dialog.form.stuName)) {
        ElMessage.error("修改后的学生姓名只能包含汉字")
        return
    }
    stuApi.updateByStuId(dialog.form)
        .then((r) => {
            if (r.data.ok) {
                ElMessage.success("修改成功")
                dialog.open = false
                getstudent()
                location.reload()
            } else {
                ElMessage.error(r.data.message)
            }
        })
};

const doUpdate = (row) => {
    dialog.title = "修改";
    //打开模态框
    dialog.open = true;
    //设置只读
    dialog.readonly = true;

    dialog.form.stuId = row.stuId
    dialog.form.stuName = row.stuName
    dialog.form.stuSex = row.stuSex
    dialog.form.stuTel = row.stuTel
    dialog.form.stuEmial = row.stuEmial
    dialog.form.stuIdentity = row.stuIdentity
}

//修改教师密码
//原始数据
const password = {
    open: false,
    title: "修改密码",
    form: {
        stuPassword: "",
        stuOldPassword: "",
        flag: ""
    },
};

const passwordData = reactive(JSON.parse(JSON.stringify(password)));

const doUpdatepassword = () => {
    passwordData.open = true;
}

const update = (passwordData) => {
    const data = reactive({
        stuId: stuId,
        password: passwordData.form.stuPassword,
        oldPassword: passwordData.form.stuOldPassword
    })
    // console.log(data)
    if (passwordData.form.stuPassword != passwordData.form.flag) {
        ElMessage.error("两次填写的密码不一致")
        return
    }
    if (data.password.indexOf(" ") != -1) {
        ElMessage.error("密码不能包含空格")
        passwordData.form.stuPassword = ""
        passwordData.form.flag = ""
        return
    }
    if(/.*[\u4e00-\u9fa5]+.*$/.test(data.password)){
        ElMessage.error("密码不能含有汉字")
        return
    }
    stuApi.updatePassword(data).then((r) => {
        if (r.data.ok) {
            ElMessage.success("修改成功");
            Object.assign(passwordData, password);
        } else {
            ElMessage.error(r.data.message)
            // console.log(r.data.message)
        }
    })
}

</script>
  
<style>
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.text {
    font-size: 26px;
}

.item {
    margin-bottom: 18px;
}

.box-card {
    margin: auto;
    width: 1000px;
    /* background:
        linear-gradient(227deg, #EBEBEB 0%, #C5C6C8 100%),
        linear-gradient(155deg, rgba(255,255,255,0.50) 5%, rgba(0,0,0,0.50) 100%);
        background-blend-mode: multiply; */
}
</style>