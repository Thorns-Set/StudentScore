<template>
    <div id="main">
        <div id="login_box">
            <h2>欢迎使用“天天向上”学生成绩管理系统</h2>
            <br>
            <br>
            <br>
            <div id="input_box">
                <input type="text" placeholder="请输入教职工编号" v-model="teacher.user">
            </div>
            <div class="input_box">
                <input type="password" placeholder="请输入密码" v-model="teacher.password">
            </div>
            <button @click="login()">登录</button><br>
        </div> 
    </div>

</template>

<script setup>
import { reactive, ref } from "@vue/reactivity"
import { ElMessage } from "element-plus";
import loginApi from "../api/login";
import {useRouter} from "vue-router";


const router=useRouter();

const teacher=reactive({
    user:"",
    password:"",
})

const login=()=>{
    if(teacher.user==null){
        console.log(teacher.user);
        ElMessage.error("教职工编号不能为空")
        return
    }else if(teacher.password==""||teacher.password==null){
        ElMessage.error("密码不能为空")
        return
    }
    loginApi.login(teacher).then(r=>{
            if(r.data.ok){
                ElMessage.success("登录成功");
                const teaId=r.data.data.teaId
                // console.log(flag)
                //将当前登录对象信息传送到系统页面
                sessionStorage.setItem("id",teaId)
                router.push('/TeaAdmin')
            }else{
                ElMessage.error(r.data.message)
            }
        }
    )
}


</script>


<style scoped>
    body{
       margin-top: 0px;
    }

    #main{
        margin: 0px;
        height: 100%;
        width: 100%;
        background-image: url(/ocean-3605547_1280.jpg);
        background-repeat: no-repeat;
        background-size: cover;
        min-width: 1000px;
        width: 100%;
        font-size: 14px;
        overflow: hidden;
        min-height: calc(100vh);
    }

    #login_box {      
    width: 21%;
    height: 400px;
    background-color: #00000060;
    margin: auto;
    text-align: center;
    border-radius: 10px;
    padding: 50px 50px;
    position:relative;
    top: 250px;
    }

    h2 {
    color: #ffffff90;
    margin-top: 5%;
    }

    #input-box {
    margin-top: 5%;
    }

    span {
    color: #fff;
    }

    input {
    border: 0;
    width: 60%;
    font-size: 15px;
    color: #fff;
    background: transparent;
    border-bottom: 2px solid #fff;
    padding: 5px 10px;
    outline: none;
    margin-top: 10px;
    }

    button {
    margin-top: 50px;
    width: 60%;
    height: 30px;
    border-radius: 10px;
    border: 0;
    color: #fff;
    text-align: center;
    line-height: 30px;
    font-size: 15px;
    background-image: linear-gradient(to right, #30cfd0, #330867);
    }

    #sign_up {
    margin-top: 45%;
    margin-left: 60%;
    }

    a {
    color: #b94648;
    }

</style>