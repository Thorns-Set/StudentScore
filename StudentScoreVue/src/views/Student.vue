<template>
    <div>
        <header class="admin-header">
            <div id="title" style="line-height: 150px;font-size: 1.7rem">Student Score</div>
            <div>
                <el-icon size="2rem">
                    <DArrowLeft />
                </el-icon>
            </div>
            <div style="line-height: 150px;font-size: 1.7rem">
                <el-icon size="1.5rem" id="flag">
                    <User style="width:1em;height:1em;" />
                </el-icon>
            </div>
        </header>
        <div class="admin-body">
            <div>
                <el-menu :unique-opened="true" router id="menu">
                    <el-menu-item index="/StuInfo">
                        <el-icon>
                            <Discount />
                        </el-icon>个人信息管理
                    </el-menu-item>
                    <el-menu-item index="/StuCourse">
                        <el-icon>
                            <Discount />
                        </el-icon>班级课程信息查询
                    </el-menu-item>
                    <el-sub-menu index="1">
                        <template #title>
                            <el-icon>
                                <Setting />
                            </el-icon>
                            <span>成绩模块</span>
                        </template>
                        <el-menu-item index="/StuScoreAll">全校成绩查询</el-menu-item>
                        <el-menu-item index="/StuClassScore">班级成绩查询</el-menu-item>
                    </el-sub-menu>
                    <el-sub-menu index="2">
                        <template #title>
                            <el-icon>
                                <User />
                            </el-icon>
                            <span>统计模块</span>
                        </template>
                        <el-menu-item index="/StuPassRate">及格率统计</el-menu-item>
                        <el-menu-item index="/StuScoreAvg">平均分数统计</el-menu-item>
                        <el-menu-item index="/StuScoreMax">单科最高分统计</el-menu-item>
                    </el-sub-menu>
                    <el-button style="width: 100%;height: 50px;" id="but" @click="deleteSession()">
                        <a>退出登录</a>
                    </el-button>
                </el-menu>

            </div>
            <div id="main">
                <transition name="el-zoom-in-top">
                    <router-view></router-view>
                </transition>
            </div>
        </div>
    </div>
</template>

<script setup>

import { reactive, ref } from '@vue/reactivity';
import { useRoute } from 'vue-router'
import { onMounted } from '@vue/runtime-core';
import { ElMessage } from 'element-plus';
import stuApi from '../api/student'

// const teaId = sessionStorage.getItem("id");
// const teacher=teacherApi.get(teaId)


const stuId = parseInt(sessionStorage.getItem("id"))
let student;

const getStudent = () => {
    stuApi.selectById(stuId).then((r) => {
        if (r.data.ok) {
            student = r.data.data
            document.getElementById("flag").after(student.stuName)
        }
    })
}


getStudent()

const deleteSession = () => {
    sessionStorage.removeItem("id")
    location.reload()
}

</script>

<style scoped>

#but{
    background:
        linear-gradient(227deg, #EBEBEB 0%, #C5C6C8 100%),
        linear-gradient(155deg, rgba(255, 255, 255, 0.50) 5%, rgba(0, 0, 0, 0.50) 100%);
    background-blend-mode: multiply;
    padding: none;
}
a{
    text-decoration: none;
    color:black;
    position: absolute;
    left: 50px;
    line-height: 50px;
}
.admin-header {
    width: 100%;
    height: 150px;
    display: grid;
    grid-template-columns: 200px 50px 1fr;
    background-color: var(--el-color-primary);
    color: var(--el-color-white);
    line-height: 50px;
}

#title {
    line-height: 100px;
}

#menu {
    height: 100%;
}

.admin-body {
    display: grid;
    grid-template-columns: 200px 1fr;
    min-height: calc(100vh - 50px);
}

.admin-header>:nth-child(1) {
    text-align: center;
    line-height: 50px;
    font-weight: bold;
    font-size: 1.2rem;

}

.admin-header>:nth-child(2) {
    display: flex;
    align-items: center;

}

.admin-header>:nth-child(3) {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    padding-right: 2rem;

}

.admin-header {
    background:
        linear-gradient(227deg, #EBEBEB 0%, #C5C6C8 100%),
        linear-gradient(155deg, rgba(255, 255, 255, 0.50) 5%, rgba(0, 0, 0, 0.50) 100%);
    background-blend-mode: multiply;
}

ul {
    background:
        linear-gradient(227deg, #EBEBEB 0%, #C5C6C8 100%),
        linear-gradient(155deg, rgba(255, 255, 255, 0.50) 5%, rgba(0, 0, 0, 0.50) 100%);
    background-blend-mode: multiply;
}

ul>li {
    background:
        linear-gradient(227deg, #EBEBEB 0%, #C5C6C8 100%),
        linear-gradient(155deg, rgba(255, 255, 255, 0.50) 5%, rgba(0, 0, 0, 0.50) 100%);
    background-blend-mode: multiply;
}

/* #main{
    background:
      linear-gradient(227deg, #EBEBEB 0%, #C5C6C8 100%),
      linear-gradient(155deg, rgba(255,255,255,0.50) 5%, rgba(0,0,0,0.50) 100%);
  background-blend-mode: multiply;
} */
</style>