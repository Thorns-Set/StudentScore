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
        meta: { title: "老师管理后台", auth: true },
        name:"tea",
        children: [
            {
                path: "/teacher",
                component: () => import("../views/teacher/TeaInfo.vue"),
                meta: { title: "个人主页" },
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
                meta: { title: "学生信息查询" },
            }
        ]
    },
    {
        path: '/Admin',
        component: () => import("../views/admin.vue"),
        meta: { title: "管理员管理后台", auth: true },
        name:"admin",
        children: [
            {
                path:"/AdminClass",
                component:()=>import("../views/admin/AdminClass.vue"),
                meta:{title:"班级信息管理" },
            },
        ]
    },
    {
        path: '/Student',
        component: () => import("../views/Student.vue"),
        name:"stu",
        meta: { title: "学生后台", auth: true },
        children: [
           
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
    console.log(to);
    console.log(from);
    console.log(to.name!=from.name);
    // //规定只能由登录页面进入其他后台模块
    if(to.name!=undefined &&from.name!=undefined && from.name!="login" &&to.name!="login"){
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
    } else{
        next({
            path: '/'
        })
    }
    //非常规退出 将sessionStorage清空
    //返回登录页面且sessionStorage值未清空
    if(to.name=="login" && sessionStorage.getItem("id") != null){
        sessionStorage.clear();
        console.log("调用");
    }


    next()
})

//导出路由对象，以供其它组件使用
export default router




