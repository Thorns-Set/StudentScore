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
                        <el-button @click="getPassRate()">查询</el-button>
                    </div>
                </div>
            </template>
            <div id="mainChart" style="height:500px; width: 100%;">

            </div>
            <el-table :data="passNum.list" border style="width: 820px;margin: auto;">
                <!-- pror要跟传递过来的数据字段名相同 -->
                <el-table-column fixed prop="total" label="总人数" width="100" />
                <el-table-column prop="languageNum" label="语文及格人数" width="120" />
                <el-table-column prop="mathNum" label="数学及格人数" width="120" />
                <el-table-column prop="englishNum" label="英语及格人数" width="120" />
                <el-table-column prop="politicsNum" label="政治及格人数" width="120" />
                <el-table-column prop="historyNum" label="历史及格人数" width="120" />
                <el-table-column prop="geogNum" label="地理及格人数" width="120" />
            </el-table>
        </el-card>
        <div style="width: 1600px;height: 400px;">
            <el-card class="box-card" style="width: 530px;float: left;margin-left: 0;">
                <template #header>
                    <div style="text-align: center;">语文及格人数统计</div>
                </template>
                <div id="languageChart" style="height:400px; width: 490px;margin: auto;">

                </div>
                <el-table :data="passNum.list" border style="width: 260px;margin: auto;">
                    <!-- pror要跟传递过来的数据字段名相同 -->
                    <el-table-column fixed prop="languageFlunk" label="语文不及格人数" width="130px" />
                    <el-table-column prop="languageNum" label="语文及格人数" width="130px" />
                </el-table>
            </el-card>

            <el-card class="box-card" style="width: 530px;float: left;">
                <template #header>
                    <div style="text-align: center;">数学及格人数统计</div>
                </template>
                <div id="mathChart" style="height:400px; width: 490px;margin: auto;">

                </div>
                <el-table :data="passNum.list" border style="width: 260px;margin: auto;">
                    <el-table-column fixed prop="mathFlunk" label="数学不及格人数" width="130px" />
                    <el-table-column prop="mathNum" label="数学及格人数" width="130px" />
                </el-table>
            </el-card>

            <el-card class="box-card" style="width: 530px;float: left;">
                <template #header>
                    <div style="text-align: center;">英语及格人数统计</div>
                </template>
                <div id="englishChart" style="height:400px; width: 490px;margin: auto;">

                </div>
                <el-table :data="passNum.list" border style="width: 260px;margin: auto;">
                    <el-table-column fixed prop="englishFlunk" label="英语不及格人数" width="130px" />
                    <el-table-column prop="englishNum" label="英语及格人数" width="130px" />
                </el-table>
            </el-card>
        </div>

        <div style="width: 1600px;height: 400px;">
            <el-card class="box-card" style="width: 530px;float: left;margin-left: 0;">
                <template #header>
                    <div style="text-align: center;">政治及格人数统计</div>
                </template>
                <div id="politicsChart" style="height:400px; width: 490px;margin: auto;">

                </div>
                <el-table :data="passNum.list" border style="width: 260px;margin: auto;">
                    <!-- pror要跟传递过来的数据字段名相同 -->
                    <el-table-column fixed prop="politicsFlunk" label="政治不及格人数" width="130px" />
                    <el-table-column prop="politicsNum" label="政治及格人数" width="130px" />
                </el-table>
            </el-card>

            <el-card class="box-card" style="width: 530px;float: left;">
                <template #header>
                    <div style="text-align: center;">历史及格人数统计</div>
                </template>
                <div id="historyChart" style="height:400px; width: 490px;margin: auto;">

                </div>
                <el-table :data="passNum.list" border style="width: 260px;margin: auto;">
                    <el-table-column fixed prop="historyFlunk" label="历史不及格人数" width="130px" />
                    <el-table-column prop="historyNum" label="历史及格人数" width="130px" />
                </el-table>
            </el-card>

            <el-card class="box-card" style="width: 530px;float: left;">
                <template #header>
                    <div style="text-align: center;">地理及格人数统计</div>
                </template>
                <div id="geogChart" style="height:400px; width: 490px;margin: auto;">

                </div>
                <el-table :data="passNum.list" border style="width: 260px;margin: auto;">
                    <el-table-column fixed prop="geogFlunk" label="地理不及格人数" width="130px" />
                    <el-table-column prop="geogNum" label="地理及格人数" width="130px" />
                </el-table>
            </el-card>
        </div>
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
        name: "单位%"
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
    languageChart = echarts.init(document.getElementById('languageChart'))
    languageChart.setOption(languageOption)
    mathChart = echarts.init(document.getElementById('mathChart'))
    mathChart.setOption(mathOption)
    englishChart = echarts.init(document.getElementById('englishChart'))
    englishChart.setOption(englishOption)
    politicsChart = echarts.init(document.getElementById('politicsChart'))
    politicsChart.setOption(politicsOption)
    historyChart = echarts.init(document.getElementById('historyChart'))
    historyChart.setOption(historyOption)
    geogChart = echarts.init(document.getElementById('geogChart'))
    geogChart.setOption(geogOption)
}, 100);

//获取及格率信息
const getPassRate = () => {
    if (data.examId == "") {
        ElMessage.error("请选择考试信息")
        return
    }
    scoreApi.selectPassRate(data.examId, data.classId)
        .then((r) => {
            if (!r.data.ok) {
                ElMessage.error(r.data.message)
            }
            myChart.setOption({
                series: [
                    {
                        name: '及格率',
                        data: r.data.data,
                    }
                ]
            });
        })
    scoreApi.selectPassNum(data.examId, data.classId)
        .then((r) => {
            console.log(r.data.data)
            //后端返回类型为对象，不能直接赋给list数组值
            passNum.list[0] = r.data.data
            console.log(passNum.list[0])
            languageChart.setOption({
                series: [
                    {
                        data: [
                            {
                                value: r.data.data.languageNum,
                                name: '及格人数'
                            },
                            {
                                value: r.data.data.total - r.data.data.languageNum,
                                name: '不及格人数'
                            }]
                    }
                ]
            })
            mathChart.setOption({
                series: [
                    {
                        data: [
                            {
                                value: r.data.data.mathNum,
                                name: '及格人数'
                            },
                            {
                                value: r.data.data.total - r.data.data.mathNum,
                                name: '不及格人数'
                            }]
                    }
                ]
            })
            englishChart.setOption({
                series: [
                    {
                        data: [
                            {
                                value: r.data.data.englishNum,
                                name: '及格人数'
                            },
                            {
                                value: r.data.data.total - r.data.data.englishNum,
                                name: '不及格人数'
                            }]
                    }
                ]
            })
            politicsChart.setOption({
                series: [
                    {
                        data: [
                            {
                                value: r.data.data.politicsNum,
                                name: '及格人数'
                            },
                            {
                                value: r.data.data.total - r.data.data.politicsNum,
                                name: '不及格人数'
                            }]
                    }
                ]
            })
            historyChart.setOption({
                series: [
                    {
                        data: [
                            {
                                value: r.data.data.historyNum,
                                name: '及格人数'
                            },
                            {
                                value: r.data.data.total - r.data.data.historyNum,
                                name: '不及格人数'
                            }]
                    }
                ]
            })
            geogChart.setOption({
                series: [
                    {
                        data: [
                            {
                                value: r.data.data.geogNum,
                                name: '及格人数'
                            },
                            {
                                value: r.data.data.total - r.data.data.geogNum,
                                name: '不及格人数'
                            }]
                    }
                ]
            })
        })
}

//接收及格人数统计结果
const passNum = reactive({ list: [] })

//各科成绩饼图挂载的数据
var languageOption = {
    series: [
        {
            type: 'pie',
            color: ['green', 'red'],
            stillShowZeroSum: false,
            data: [
                {
                    value: 0,
                    name: '及格人数'
                },
                {
                    value: 0,
                    name: '不及格人数'
                }
            ]
        }
    ]
};
var languageChart

var mathOption = {
    series: [
        {
            type: 'pie',
            color: ['green', 'red'],
            stillShowZeroSum: false,
            data: [
                {
                    value: 0,
                    name: '及格人数'
                },
                {
                    value: 0,
                    name: '不及格人数'
                }
            ]
        }
    ]
};
var mathChart

var englishOption = {
    series: [
        {
            type: 'pie',
            color: ['green', 'red'],
            stillShowZeroSum: false,
            data: [
                {
                    value: 0,
                    name: '及格人数'
                },
                {
                    value: 0,
                    name: '不及格人数'
                }
            ]
        }
    ]
};
var englishChart

var politicsOption = {
    series: [
        {
            type: 'pie',
            color: ['green', 'red'],
            stillShowZeroSum: false,
            data: [
                {
                    value: 0,
                    name: '及格人数'
                },
                {
                    value: 0,
                    name: '不及格人数'
                }
            ]
        }
    ]
};
var politicsChart

var historyOption = {
    series: [
        {
            type: 'pie',
            color: ['green', 'red'],
            stillShowZeroSum: false,
            data: [
                {
                    value: 0,
                    name: '及格人数'
                },
                {
                    value: 0,
                    name: '不及格人数'
                }
            ]
        }
    ]
};
var historyChart

var geogOption = {
    series: [
        {
            type: 'pie',
            color: ['green', 'red'],
            stillShowZeroSum: false,
            data: [
                {
                    value: 0,
                    name: '及格人数'
                },
                {
                    value: 0,
                    name: '不及格人数'
                }
            ]
        }
    ]
};
var geogChart



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