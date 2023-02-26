<template>
    <div>
        <el-card class="box-card" style="width: 1200px;">
            <template #header>
                <div class="card-header">
                    <span>及格率统计</span>
                    <div>
                        <el-select v-model="data.examId" filterable placeholder="请选择考试信息" @change="handleChange">
                            <el-option v-for="item in examInfo.list" :key="item.examId" :label="item.examName"
                                :value="item.examId" />
                        </el-select>
                        <el-select v-model="data.classId" filterable placeholder="请选择班级信息" @change="handleChange">
                            <el-option v-for="item in classInfo.list" :key="item.classId" :label="item.className"
                                :value="item.classId" />
                        </el-select>
                        <el-button @click="getPassRate()">查询</el-button>
                    </div>
                </div>
            </template>
            <div id="mainChart" style="height:500px; width: 100%;">

            </div>
        </el-card>
    </div>
</template>


<script setup>
import { ref, onMounted, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import scoreApi from "../../api/score"
import examApi from "../../api/exam"
import { Search } from '@element-plus/icons-vue'
import classApi from '../../api/class'
import relationApi from '../../api/relation'
import $ from 'jquery'
import * as echarts from 'echarts'



const teaId = parseInt(sessionStorage.getItem("id"))

//接收查询出来的考试信息
const examInfo = reactive({ list: [] })

//查询考试信息
examApi.selectAllExam().then((r) => {
    examInfo.list = r.data.data
    // console.log(examInfo.list)
})

//接收查询出来的班级信息
const classInfo = reactive({ list: [] })

//查询班级信息
classApi.selectByTeaIdList(teaId).then((r) => {
    if (r.data.ok) {
        // console.log(r.data.data)
        classInfo.list = r.data.data
    } else {
        ElMessage.error(r.data.message)
    }
})

const data = reactive({
    examId: "",
    classId: "",
})

const value1 = ref(false)

// 使用刚指定的配置项和数据显示图表。
var myChart

// 指定图表的配置项和数据
var option = {
    color: [
        '#C70039'
    ],
    title: {
        text: '及格率统计图表'
    },
    tooltip: {},
    legend: {
        data: ['及格率']
    },
    xAxis: {
        data: ['语文', '数学', '英语', '政治', '历史', '地理']
    },
    yAxis: {
        name:"单位%"
    },
    series: [
        {
            name: '及格率',
            //柱状图
            type: 'bar',
            data: [],
            showBackground: true,
            backgroundStyle: {
                color: 'rgba(220, 220, 220, 0.8)'
            }
        }
    ]
};

// 进入异步队列 避免同步队列没获取到dom就加载echart
setTimeout(() => {
myChart = echarts.init(document.getElementById('mainChart'));
myChart.setOption(option);

}, 100);


const getPassRate = () => {
    if (data.classId == "") {
        ElMessage.error("请选择班级信息")
        return
    }
    if (data.examId == "") {
        ElMessage.error("请选择考试信息")
        return
    }
    scoreApi.selectPassRate(data.examId, data.classId)
        .then((r) => {
            var option = {
                color: [
                    '#C70039'
                ],
                title: {
                    text: '及格率统计图表'
                },
                tooltip: {},
                legend: {
                    data: ['及格率']
                },
                xAxis: {
                    data: ['语文', '数学', '英语', '政治', '历史', '地理']
                },
                yAxis: {
                    name:"单位%"
                },
                series: [
                    {
                        name: '及格率',
                        //柱状图
                        type: 'bar',
                        data: r.data.data,
                        showBackground: true,
                        backgroundStyle: {
                            color: 'rgba(220, 220, 220, 0.8)'
                        }
                    }
                ]
            };
            var myChart
            myChart = echarts.init(document.getElementById('mainChart'));
            myChart.setOption(option);
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