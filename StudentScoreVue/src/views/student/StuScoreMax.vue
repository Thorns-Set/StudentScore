<template>
    <div>
        <el-card class="box-card" style="width: 1200px;">
            <template #header>
                <div class="card-header">
                    <span>单科最高分数统计</span>
                    <div>
                        <el-select v-model="data.examId" filterable placeholder="请选择考试信息" @change="handleChange">
                            <el-option v-for="item in examInfo.list" :key="item.examId" :label="item.examName"
                                :value="item.examId" />
                        </el-select>
                        <el-button @click="getScoreMax()">查询</el-button>
                    </div>
                </div>
            </template>
            <div id="mainChart" style="height:500px; width: 100%;">

            </div>

            <el-card class="box-card" style="width: 350px;float: left;height: 260px;">
                <template #header>
                    <div style="text-align: center;">语文最高分</div>
                </template>
                <el-table :data="languageData.list" border style="width: 340px;margin: auto;">
                    <!-- pror要跟传递过来的数据字段名相同 -->
                    <el-table-column prop="languageScoreID" label="学号" width="100" />
                    <el-table-column prop="languageScoreName" label="学生姓名" width="100" />
                    <el-table-column prop="languageScoreMax" label="语文最高分" width="100" />
                </el-table>
            </el-card>

            <el-card class="box-card" style="width: 350px;float: left;height: 260px;">
                <template #header>
                    <div style="text-align: center;">数学最高分</div>
                </template>
                <el-table :data="mathData.list" border style="width: 340px;margin: auto;">
                    <!-- pror要跟传递过来的数据字段名相同 -->
                    <el-table-column prop="mathScoreID" label="学号" width="100" />
                    <el-table-column prop="mathScoreName" label="学生姓名" width="100" />
                    <el-table-column prop="mathScoreMax" label="数学最高分" width="100" />
                </el-table>
            </el-card>

            <el-card class="box-card" style="width: 350px;float: left;height: 260px;">
                <template #header>
                    <div style="text-align: center;">英语最高分</div>
                </template>
                <el-table :data="englishData.list" border style="width: 340px;margin: auto;">
                    <!-- pror要跟传递过来的数据字段名相同 -->
                    <el-table-column prop="englishScoreID" label="学号" width="100" />
                    <el-table-column prop="englishScoreName" label="学生姓名" width="100" />
                    <el-table-column prop="englishScoreMax" label="英语最高分" width="100" />
                </el-table>
            </el-card>

            <el-card class="box-card" style="width: 350px;float: left;height: 260px;">
                <template #header>
                    <div style="text-align: center;">政治最高分</div>
                </template>
                <el-table :data="politicsData.list" border style="width: 340px;margin: auto;">
                    <!-- pror要跟传递过来的数据字段名相同 -->
                    <el-table-column prop="politicsScoreID" label="学号" width="100" />
                    <el-table-column prop="politicsScoreName" label="学生姓名" width="100" />
                    <el-table-column prop="politicsScoreMax" label="政治最高分" width="100" />
                </el-table>
            </el-card>

            <el-card class="box-card" style="width: 350px;float: left;height: 260px;">
                <template #header>
                    <div style="text-align: center;">历史最高分</div>
                </template>
                <el-table :data="historyData.list" border style="width: 340px;margin: auto;">
                    <!-- pror要跟传递过来的数据字段名相同 -->
                    <el-table-column prop="historyScoreID" label="学号" width="100" />
                    <el-table-column prop="historyScoreName" label="学生姓名" width="100" />
                    <el-table-column prop="historyScoreMax" label="历史最高分" width="100" />
                </el-table>
            </el-card>

            <el-card class="box-card" style="width: 350px;float: left;height: 260px;">
                <template #header>
                    <div style="text-align: center;">地理最高分</div>
                </template>
                <el-table :data="geogData.list" border style="width: 340px;margin: auto;">
                    <!-- pror要跟传递过来的数据字段名相同 -->
                    <el-table-column prop="geogScoreID" label="学号" width="100" />
                    <el-table-column prop="geogScoreName" label="学生姓名" width="100" />
                    <el-table-column prop="geogScoreMax" label="地理最高分" width="100" />
                </el-table>
            </el-card>
            
            <el-card class="box-card" style="width: 350px;float: left;height: 260px;">
                <template #header>
                    <div style="text-align: center;">总分最高分</div>
                </template>
                <el-table :data="totalData.list" border style="width: 340px;margin: auto;">
                    <!-- pror要跟传递过来的数据字段名相同 -->
                    <el-table-column prop="totalScoreID" label="学号" width="100" />
                    <el-table-column prop="totalScoreName" label="学生姓名" width="100" />
                    <el-table-column prop="totalScoreMax" label="总分最高分" width="100" />
                </el-table>
            </el-card>
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
import stuApi from '../../api/student'


const stuId = parseInt(sessionStorage.getItem("id"))

//接收查询出来的考试信息
const examInfo = reactive({ list: [] })


//查询考试信息
examApi.selectAllExam().then((r) => {
    examInfo.list = r.data.data
    // console.log(examInfo.list)
})

const getclassID = () => {
    stuApi.selectById(stuId)
        .then((r) => {
            data.classId = r.data.data.classId
        })
}

setTimeout(() => {
    getclassID()
}, 1);


const data = reactive({
    examId: "",
    classId: "",
})


var myChart

// 指定图表的配置项和数据
var option = {
    color: [
        '#C70039'
    ],
    title: {
        text: '单科最高分数统计图表'
    },
    tooltip: {},
    legend: {
        data: ['单科最高分数']
    },
    xAxis: {
        data: ['语文最高分数', '数学最高分数', '英语最高分数', '政治最高分数', '历史最高分数', '地理最高分数', "最高总分"]
    },
    yAxis: {
        name: "单位（分）"
    },
    series: [
        {
            name: '最高分数',
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


setTimeout(() => {
    myChart = echarts.init(document.getElementById('mainChart'));
    myChart.setOption(option);
}, 100);


const getScoreMax = () => {
    if (data.classId == "") {
        ElMessage.error("请选择班级信息")
        return
    }
    if (data.examId == "") {
        ElMessage.error("请选择考试信息")
        return
    }
    scoreApi.selectScoreMax(data.examId, data.classId)
        .then((r) => {
            if (!r.data.ok) {
                ElMessage.error(r.data.message)
                return
            }
            // console.log(r.data.data);
            // console.log(r.data.data.totalMaxList);
            totalData.list = r.data.data.totalMaxList
            languageData.list = r.data.data.languageMaxList
            mathData.list = r.data.data.mathMaxList
            englishData.list = r.data.data.englishMaxList
            politicsData.list = r.data.data.politicsMaxList
            historyData.list = r.data.data.historyMaxList
            geogData.list = r.data.data.geogMaxList
            console.log(languageData.list);
            myChart.setOption({
                series: [
                    {
                        name: '最高分数',
                        data: [languageData.list[0].languageScoreMax, mathData.list[0].mathScoreMax, englishData.list[0].englishScoreMax, politicsData.list[0].politicsScoreMax, historyData.list[0].historyScoreMax, geogData.list[0].geogScoreMax, totalData.list[0].totalScoreMax],
                    }
                ]
            })
        })
}

const languageData = reactive({ list: [] })
const mathData = reactive({ list: [] })
const englishData = reactive({ list: [] })
const politicsData = reactive({ list: [] })
const historyData = reactive({ list: [] })
const geogData = reactive({ list: [] })
const totalData = reactive({ list: [] })
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