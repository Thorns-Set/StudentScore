//引入 router
//createRouter用于创建路由实例
//createWebHashHistory 配置内部使用的hash 模式的路由
import {createRouter,createWebHashHistory} from 'vue-router'



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
        component: ()=>import("../views/login.vue"),
        meta:{title:"登录页面"},
    },

    {   
        path:'/TeaAdmin',
        component: () => import("../views/teacher.vue"),
        meta: { title: "管理后台", auth: true },
        children: [
            {
                path:"/teacher",
                component: () => import("../views/teacher/Welcome.vue"),
                meta: { title: "个人主页"},
            },
            {
                path:"/role",
                component: () => import("../views/teacher/TeaScore.vue"),
                meta: { title: "成绩管理"},
            },
            {
                path:"/user",
                component: () => import("../views/teacher/User.vue"),
                meta: { title: "用户管理"},
            },
            {
                path:"/house",
                component: () => import("../views/teacher/House.vue"),
                meta:{ title:"房产信息管理"}
            },
          
        ]
    }
]

//创建路由对象
const router = createRouter({
    history:createWebHashHistory(),
    routes
})

//导出路由对象，以供其它组件使用
export default router




