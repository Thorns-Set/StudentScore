<template>
    <div id="main">
        <div id="login_box">
            <h2>欢迎使用“天天向上”学生成绩管理系统</h2>
            <br>

            <div class="mb-2 flex items-center text-sm">
                <el-radio-group v-model="identity" @change="idchange()" class="ml-4">
                    <el-radio label="1" size="large" name="identity" value="1">管理员</el-radio>
                    <el-radio label="2" size="large" name="identity" value="2">教师</el-radio>
                    <el-radio label="3" size="large" name="identity" value="3">学生</el-radio>
                </el-radio-group>
            </div>
            <div class="input_box">
                <input type="text" id="idInput" placeholder="请输入教职工编号" v-model="loginfo.user">
            </div>
            <div class="input_box">
                <input type="password" placeholder="请输入密码" v-model="loginfo.password">
            </div>
            <div class="input_box">
                <input type="text" placeholder="请输入验证码" v-model="code">
            </div>
            <canvas id="myCanvas" @click="showCheck()"></canvas>
            <button @click="login()">登录</button><br>
        </div>
    </div>
</template>


<script setup>

import { reactive, ref } from "@vue/reactivity"
import { ElMessage } from "element-plus";
import loginApi from "../api/login";
import { useRouter } from "vue-router";
import $ from 'jquery'

//验证码实现
var codeFlag = "";
//生成验证码位数为四
function createCode() {
    codeFlag = "";
    var selectChar = new Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    for (var i = 0; i < 4; i++) {
        var charIndex = Math.floor(Math.random() * 60);
        codeFlag += selectChar[charIndex];
    }
    if (codeFlag.length != 4) {
        createCode()
    }
    return codeFlag;
}

setTimeout(() => {
    showCheck()
}, 100);

//将生成的验证码展示到前端页面
function showCheck() {
    createCode()
    var c = document.getElementById("myCanvas");
    var ctx = c.getContext("2d");
    ctx.clearRect(0, 0, 1000, 1000);
    ctx.font = "80px 'Hiragino Sans GB'";
    ctx.fillStyle = "#E8DFE8";
    ctx.fillText(codeFlag, 0, 100);
}

const router = useRouter();

//表单绑定的验证码
const code = ref("")

//登录信息
const loginfo = reactive({
    user: "",
    password: ""
})

//身份单选框值
const identity = ref("1")
//单选框值改变绑定事件
const idchange = () => {
    if (identity.value == "1") {
        $("#idInput").attr("placeholder", "请输入管理员编号")
    } else if (identity.value == "2") {
        $("#idInput").attr("placeholder", "请输入教师编号")
    } else {
        $("#idInput").attr("placeholder", "请输入学生学号")
    }
}

const login = () => {
    if (loginfo.user.split(" ").join("").length == 0) {
        // console.log(teacher.user);
        if (identity.value == "1") {
            ElMessage.error("管理员编号不能为空")
        } else if (identity.value == "2") {
            ElMessage.error("教师编号不能为空")
        } else {
            ElMessage.error("学生学号不能为空")
        }
        return
    } else if (loginfo.password.split(" ").join("").length == 0) {
        ElMessage.error("密码不能为空")
        return
    } else if (code.value.split(" ").join("").length == 0) {
        ElMessage.error("验证码不能为空")
        return
    }
    // else if (code.value != codeFlag) {
    //     ElMessage.error("验证码输入错误,请重新输入")
    //     showCheck()
    //     teacher.code = ""
    //     return
    // }
    //根据单选框的值判断登录人员身份
    if (identity.value == "1") {
        loginApi.adminlogin(loginfo).then((r) => {
            if (r.data.ok) {
                ElMessage.success("登陆成功")
                const id = r.data.data.adminId
                // console.log(flag)
                //将当前登录对象信息传送到系统页面
                sessionStorage.setItem("id", id)
                router.push('/Admin')
                sessionStorage.setItem("name","admin")
            }else {
                ElMessage.error(r.data.message)
            }
        })
    } else if (identity.value == "2") {
        loginApi.tealogin(loginfo).then(r => {
            if (r.data.ok) {
                ElMessage.success("登录成功");
                const id = r.data.data.teaId
                // console.log(flag)
                //将当前登录对象信息传送到系统页面
                sessionStorage.setItem("id", id)
                router.push('/TeaAdmin')
                sessionStorage.setItem("name","tea")
            } else {
                ElMessage.error(r.data.message)
            }
        }
        )
    } else {
        loginApi.stulogin(loginfo).then((r) => {
            if(r.data.ok){
                ElMessage.success("登陆成功")
                const id = r.data.data.stuId
                // console.log(r.data.data)
                //将当前登录对象信息传送到系统页面
                sessionStorage.setItem("id", id)
                sessionStorage.setItem("name","stu")
                router.push('/Student')
            }else {
                ElMessage.error(r.data.message)
            }
        })
    }
}


</script>


<style scoped>
body {
    margin-top: 0px;
}

#main {
    margin: 0px;
    height: 100%;
    width: 100%;
    background-image: url(./img/Starry.jpg);
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
    position: relative;
    top: 250px;

}

h2 {
    color: #ffffff90;
    margin-top: 5%;
}

.input-box {

    margin-top: 5%;
}

span {
    color: #fff;
}

input {
    border: 0;
    width: 60%;
    font-size: 15px;
    color: #ffffff;
    background: transparent;
    border-bottom: 2px solid #fff;
    padding: 5px 10px;
    outline: none;
    margin-top: 10px;
}

button {
    margin-top: 50px;
    margin-left: -50px;
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

::-webkit-input-placeholder {
    color: #ffffff;
}

canvas {
    width: 50px;
    height: 35px;
    position: relative;
    top: -70px;
    right: -220px;
}
</style>