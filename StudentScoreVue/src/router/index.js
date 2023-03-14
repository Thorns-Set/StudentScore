//引入 router
//createRouter用于创建路由实例
//createWebHashHistory 配置内部使用的hash 模式的路由
import { ElMessage } from 'element-plus';
import { createRouter, createWebHashHistory } from 'vue-router'



//创建路由表
// const routes = [
//     {
//         path: '/',
//         name: 'Home',
//         component: Home
//     },
//     {
//         path: '/about',
//         name:'About',
//         component: About
//     },
//     {
//         path: '/todolist',
//         name:'TodoList',
//         component: TodoList
//     }
// ]

const routes = [
    {
        path: "/",
        component: () => import("../views/login.vue"),
        meta: { title: "登录页面" },
        name: "login"
    },

    {
        path: '/TeaAdmin',
        component: () => import("../views/teacher.vue"),
        meta: { title: "教师后台", auth: true },
        name: "tea",
        children: [
            {
                path: "/teacher",
                component: () => import("../views/teacher/TeaInfo.vue"),
                meta: { title: "教师个人主页" },
            },
            {
                path: "/role",
                component: () => import("../views/teacher/TeaScore.vue"),
                meta: { title: "全校成绩查询" },
            },
            {
                path: "/user",
                component: () => import("../views/teacher/ClassScore.vue"),
                meta: { title: "班级成绩管理" },
            },
            {
                path: "/passrate",
                component: () => import("../views/teacher/TeaPassRateStatistics.vue"),
                meta: { title: "及格率统计" },
            },
            {
                path: "/TeaScoreAvg",
                component: () => import("../views/teacher/TeaScoreAvg.vue"),
                meta: { title: "平均分数统计" },
            },
            {
                path: "/TeaScoreMax",
                component: () => import("../views/teacher/TeaScoreMax.vue"),
                meta: { title: "单科最高分统计" },
            },
            {
                path: "/StudentInfo",
                component: () => import("../views/teacher/StudentInfo.vue"),
                meta: { title: "学生信息管理" },
            }
        ]
    },
    {
        path: '/Admin',
        component: () => import("../views/admin.vue"),
        meta: { title: "管理员后台", auth: true },
        name: "admin",
        children: [
            {
                path: "/AdminClass",
                component: () => import("../views/admin/AdminClass.vue"),
                meta: { title: "班级信息管理" },
            },
            {
                path: "/AdminExam",
                component: () => import("../views/admin/AdminExam.vue"),
                meta: { title: "考试信息管理" },
            },
            {
                path: "/AdminRelation",
                component: () => import("../views/admin/AdminRelation.vue"),
                meta: { title: "教师所教课程班级管理" },
            },
            {
                path: "/AdminStu",
                component: () => import("../views/admin/AdminStu.vue"),
                meta: { title: "学生信息管理" },
            },
            {
                path: "/AdminTea",
                component: () => import("../views/admin/AdminTea.vue"),
                meta: { title: "教师信息管理" },
            },
            {
                path: "/AdminScore",
                component: () => import("../views/admin/AdminScore.vue"),
                meta: { title: "全校成绩管理" },
            },
            {
                path: "/AdminPassRate",
                component: () => import("../views/admin/AdminPassRate.vue"),
                meta: { title: "全校成绩及格率统计" },
            },
            {
                path: "/AdminScoreAvg",
                component: () => import("../views/admin/AdminScoreAvg.vue"),
                meta: { title: "全校平均分数统计" },
            },
            {
                path: "/AdminScoreMax",
                component: () => import("../views/admin/AdminScoreMax.vue"),
                meta: { title: "全校最高分数统计" },
            },
        ]
    },
    {
        path: '/Student',
        component: () => import("../views/Student.vue"),
        name: "stu",
        meta: { title: "学生后台", auth: true },
        children: [
            {
                path: "/StuInfo",
                component: () => import("../views/student/StuInfo.vue"),
                meta: { title: "学生个人信息管理" },
            },
            {
                path: "/StuCourse",
                component: () => import("../views/student/StuCourse.vue"),
                meta: { title: "班级课程查询" },
            },
            {
                path: "/StuScoreAll",
                component: () => import("../views/student/StuScoreAll.vue"),
                meta: { title: "查询全校成绩" },
            },
            {
                path: "/StuClassScore",
                component: () => import("../views/student/StuClassScore.vue"),
                meta: { title: "班级成绩查询" },
            },
            {
                path: "/StuPassRate",
                component: () => import("../views/student/StuPassRate.vue"),
                meta: { title: "班级及格率统计" },
            },
            {
                path: "/StuScoreAvg",
                component: () => import("../views/student/StuScoreAvg.vue"),
                meta: { title: "班级平均分数统计" },
            },
            {
                path: "/StuScoreMax",
                component: () => import("../views/student/StuScoreMax.vue"),
                meta: { title: "班级最高分统计" },
            },
        ]
    },
]

//创建路由对象
const router = createRouter({
    history: createWebHashHistory(),
    routes
})

//路由拦截器
router.beforeEach((to, from, next) => {
    // //规定只能由登录页面进入其他后台模块
    if (to.name != undefined && from.name != undefined && from.name != "login" && to.name != "login") {
        ElMessage.error("不可跳转至其他后台模块")
        console.log(to);
        console.log(from);
        next({
            path: '/'
        })
    }
    //登录拦截器
    if (sessionStorage.getItem("id") != null || to.name == "login") {
        next();
    } else {
        next({
            path: '/'
        })
    }
    //非常规退出 将sessionStorage清空
    //返回登录页面且sessionStorage值未清空
    if (to.name == "login" && sessionStorage.getItem("id") != null) {
        sessionStorage.clear();
        console.log("调用");
    }
    if (to.meta.title) { // 判断是否有标题
        document.title = to.meta.title;
    }

    next()
})

//导出路由对象，以供其它组件使用
export default router




