//引入 router
//createRouter用于创建路由实例
//createWebHashHistory 配置内部使用的hash 模式的路由
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
        path:"/",
        component: () => import("../views/login.vue"),
        meta: { title: "登录页面" },
        name:"login"
    },

    {
        path: '/TeaAdmin',
        component: () => import("../views/teacher.vue"),
        meta: { title: "老师管理后台", auth: true },
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
                meta: { title: "及格率统计" }
            },
            {
                path: "/TeaScoreAvg",
                component: () => import("../views/teacher/TeaScoreAvg.vue"),
                meta: { title: "平均分数统计" }
            },
            {
                path: "/TeaScoreMax",
                component: () => import("../views/teacher/TeaScoreMax.vue"),
                meta: { title: "单科最高分统计" }
            },
            {
                path:"/StudentInfo",
                component: () => import("../views/teacher/StudentInfo.vue"),
                meta: { title: "学生信息查询" }
            }
        ]
    }
]

//创建路由对象
const router = createRouter({
    history: createWebHashHistory(),
    routes
})

//路由拦截器
router.beforeEach((to,from,next)=>{
    // console.log(to.name)
    // console.log(sessionStorage.getItem("id"))
    if(sessionStorage.getItem("id")!=null || to.name=="login"){
        next();
    }else{
        next({
            path:'/'
        })
    }
    next()
})

//导出路由对象，以供其它组件使用
export default router




