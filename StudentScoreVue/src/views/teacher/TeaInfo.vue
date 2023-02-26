<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>基本个人信息</span>
        <el-button-group class="ml-4">
          <el-button type="primary" :icon="Edit" @click="doUpdate(teacher)">修改个人信息</el-button>
          <el-button type="primary" :icon="Edit" @click="doUpdatepassword()">修改密码</el-button>
        </el-button-group>
      </div>
    </template>
    <div class="text item" v-if="temp">教师编号：{{ teacher.teaId }}</div>
    <div class="text item" v-if="temp">教师姓名：{{ teacher.teaName }}</div>
    <div class="text item" v-if="temp">教师电话号码：{{ teacher.teaTel }}</div>
    <div class="text item" v-if="temp">教师电子邮件：{{ teacher.teaEmial }}</div>
    <div class="text item" v-if="temp">教师身份证号码：{{ teacher.teaIdentity }}</div>
    <div class="text item" v-if="temp">教师所教班级：{{ teacher.teaClassName }}</div>
    <div class="text item" v-if="temp">教师所教科目：{{ teacher.teaCourseName }}</div>
  </el-card>

  <!-- 修改个人信息页面模态框 -->
  <el-dialog v-model="dialog.open" :title="dialog.title + '对话框'" width="500px" draggable>
    <el-form label-position="right" label-width="120px" :model="dialog.form">
      <el-form-item label="教师编号">
        <el-input :readonly="dialog.readonly" v-model="dialog.form.teaId" />
      </el-form-item>
      <el-form-item label="教师姓名">
        <el-input v-model="dialog.form.teaName" />
      </el-form-item>
      <el-form-item label="教师电话号码">
        <el-input v-model="dialog.form.teaTel" />
      </el-form-item>
      <el-form-item label="教师电子邮件">
        <el-input v-model="dialog.form.teaEmial" />
      </el-form-item>
      <el-form-item label="教师身份证号码">
        <el-input v-model="dialog.form.teaIdentity" />
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
        <el-input v-model="passwordData.form.teaOldPassword" type="password" placeholder="请输入原密码" />
      </el-form-item>
      <el-form-item label="新密码">
        <el-input v-model="passwordData.form.teaPassword" type="password" placeholder="请输入新密码" />
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
import teacherApi from "../../api/teacher"
import { Edit } from '@element-plus/icons-vue'
import { ElMessage } from "element-plus";

//通过sessionStorage获取当前对象的用户信息
const teaId = parseInt(sessionStorage.getItem("id"))
// console.log(teaId)

const temp = ref(false)
let teacher

const getTeacher = () => {
  teacherApi.get(teaId)
    .then((r) => {
      if (r.data.ok) {
        teacher = r.data.data
        temp.value = true
        console.log(teacher)
        // console.log(temp)
        // console.log(typeof temp)
      }
    })
}
getTeacher()


//修改教师基本信息
//原始数据
const dialogData = {
  open: false,
  title: "修改",
  readonly: false,
  form: {
    teaId: "",
    teaName: "",
    teaTel: "",
    teaEmial: "",
    teaIdentity: ""
  },
};

//将数据转换为json格式
const dialog = reactive(JSON.parse(JSON.stringify(dialogData)));

const doit = () => {
  teacherApi.update(dialog.form).then((r) => {
    if (r.data.ok) {
      ElMessage.success("修改成功");
      //重新获取当前对象的信息
      getTeacher()
      //刷新页面
      location.reload()
      Object.assign(dialog, dialogData);
    }
  });
};

const doUpdate = (row) => {
  dialog.title = "修改";
  //打开模态框
  dialog.open = true;
  //设置只读
  dialog.readonly = true;

  dialog.form.teaId = row.teaId
  dialog.form.teaName = row.teaName
  dialog.form.teaTel = row.teaTel
  dialog.form.teaEmial = row.teaEmial
  dialog.form.teaIdentity = row.teaIdentity
}

//修改教师密码
//原始数据
const password = {
  open: false,
  title: "修改密码",
  form: {
    teaPassword: "",
    teaOldPassword: "",
    flag: ""
  },
};

const passwordData = reactive(JSON.parse(JSON.stringify(password)));

const doUpdatepassword = () => {
  passwordData.open = true;
}

const update = (passwordData) => {
  const data = reactive({
    id: teaId,
    password: passwordData.form.teaPassword,
    oldPassword: passwordData.form.teaOldPassword
  })
  // console.log(data)
  if (passwordData.form.teaPassword != passwordData.form.flag) {
    ElMessage.error("两次填写的密码不一致")
    return
  }
  teacherApi.updatePassword(data).then((r) => {
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