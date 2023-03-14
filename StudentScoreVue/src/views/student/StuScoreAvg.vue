<template>
    <div>
        <el-card class="box-card" style="width: 1200px;">
            <template #header>
                <div class="card-header">
                    <span>平均分数统计</span>
                    <div>
                        <el-select v-model="data.examId" filterable placeholder="请选择考试信息" @change="handleChange">
                            <el-option v-for="item in examInfo.list" :key="item.examId" :label="item.examName"
                                :value="item.examId" />
                        </el-select>
                        <el-button @click="getScoreAvg()">查询</el-button>
                    </div>
                </div>
            </template>
            <div id="mainChart" style="height:500px; width: 100%;">

            </div>
            <el-table :data="scoreAvg.list" border style="width: 820px;margin: auto;">
                <!-- pror要跟传递过来的数据字段名相同 -->
                <el-table-column prop="languageScoreAvg" label="语文平均分" width="120" />
                <el-table-column prop="mathScoreAvg" label="数学平均分" width="120" />
                <el-table-column prop="englishScoreAvg" label="英语平均分" width="120" />
                <el-table-column prop="politicsScoreAvg" label="政治平均分" width="120" />
                <el-table-column prop="historyScoreAvg" label="历史平均分" width="120" />
                <el-table-column prop="geogScoreAvg" label="地理平均分" width="120" />
                <el-table-column prop="totalScoreAvg" label="总平均分" width="100" />
            </el-table>
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
        text: '平均分数统计图表'
    },
    tooltip: {},
    legend: {
        data: ['平均分数']
    },
    xAxis: {
        data: ['语文分数', '数学分数', '英语分数', '政治分数', '历史分数', '地理分数',"总平均分"]
    },
    yAxis: {
        name: "单位（分）"
    },
    series: [
        {
            name: '平均分',
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


const getScoreAvg=()=>{
    if (data.examId == "") {
        ElMessage.error("请选择考试信息")
        return
    }
    scoreApi.selectScoreAvg(data.examId,data.classId)
    .then((r)=>{
        if(r.data.ok){
            const temp=r.data.data
            scoreAvg.list[0]=r.data.data
            // console.log(scoreAvg.list);
            myChart.setOption({
                series: [
                    {
                        name: '平均分',
                        data: [temp.languageScoreAvg,temp.mathScoreAvg,temp.englishScoreAvg,temp.politicsScoreAvg,temp.historyScoreAvg,temp.geogScoreAvg,temp.totalScoreAvg]
                    }
                ]
            });
        }else{
            ElMessage.error(r.data.message)
        }
    })
}

const scoreAvg=reactive({list:[]})
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