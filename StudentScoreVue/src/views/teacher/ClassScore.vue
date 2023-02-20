<template>
    <div>
        <el-card class="box-card" style="width: 1200px;">
            <template #header>
                <div class="card-header">
                <span>班级成绩管理</span>
                <el-button type="primary" class="button" @click="selectByExamId" style="margin-left: -320px;">返回成绩列表</el-button>
                <el-row :gutter="20" style="margin-right: -360px;margin-left: -150px;">
                    <el-input
                    v-model="stuName"
                    class="w-50 m-2"
                    placeholder="请输入学生姓名"
                    :prefix-icon="Search"
                    />
                </el-row>
                <el-button type="primary" class="button" @click="selectStuScore" style="margin-left: 40px;">查询</el-button>
                <el-button type="primary" class="button" @click="openT">选择具体考试和班级</el-button>
                </div>
            </template>
            <el-table :data="scoreList.list" style="width: 100%" @sort-change="sortTable">
                <!-- pror要跟传递过来的数据字段名相同 -->
                <el-table-column fixed prop="scoreId" label="成绩编号" width="100" />
                <el-table-column prop="stuId" label="学号" width="100" />
                <el-table-column prop="stuName" label="学生姓名" width="120" />
                <el-table-column prop="examName" label="考试名称" width="160" />
                <el-table-column prop="className" label="班级编号" width="120" />
                <el-table-column prop="languageScore" label="语文成绩" sortable=custom width="120" />
                <el-table-column prop="mathScore" label="数学成绩" sortable=custom width="120" />
                <el-table-column prop="englishScore" label="英语成绩" sortable=custom width="120" />
                <el-table-column prop="politicsScore" label="政治成绩" sortable=custom width="120" />
                <el-table-column prop="historyScore" label="历史成绩" sortable=custom width="120" />
                <el-table-column prop="geogScore" label="地理成绩" sortable=custom width="120" />
                <el-table-column prop="totalPoints" label="总分" sortable=custom width="120" />
                <el-table-column fixed="right" label="操作" width="120">
                <template #default="scope">
                    <el-button-group>
                    <el-button size="small" type="warning" @click="">修改</el-button>
                    </el-button-group>
                </template>
                </el-table-column>
            </el-table>
            <el-pagination
            background 
            style="padding-top: 15px"
            @size-change="changePageSize"
            @current-change="changePageSize" 
            v-model:current-page.sync="pageObj.pageNow"
            :page-sizes="[2,4,6,8,10]"
            v-model:page-size="pageObj.size"
            layout="total, sizes, prev, pager, next, jumper"
            v-model:total="total">
            </el-pagination>
        </el-card>
    </div>

    <el-dialog v-model="dialogData.open" :title="dialogData.title" width="500px" draggable>
        <el-select v-model="pageObj.examId" filterable placeholder="请选择考试信息" @change="handleChange">
        <el-option
            v-for="item in examInfo.list"
            :key="item.examId"
            :label="item.examName"
            :value="item.examId"
        />
        </el-select>
        <el-select v-model="pageObj.classId" filterable placeholder="请选择班级信息" @change="handleChange">
        <el-option
            v-for="item in classInfo.list"
            :key="item.classId"
            :label="item.className"
            :value="item.classId"
        />
        </el-select>
        <template #footer>
        <span class="dialog-footer">
            <el-button type="primary" @click="clear">清除以选择信息</el-button>
            <el-button @click="selectByExamIdClassId()">确定</el-button>
            <el-button type="primary" @click="openF">取消</el-button>
        </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref, onMounted, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import scoreApi from "../../api/score"
import examApi from "../../api/exam"
import { Search } from '@element-plus/icons-vue'
import classApi from '../../api/class'

//接收查询出来的成绩数据 并跟表格数据绑定
const scoreList=reactive({ list:[] })


//定义对象 储存条件分页查询属性
const pageObj=reactive({
    teaId:parseInt(sessionStorage.getItem("id")),
    examId:"",
    classId:"",
    pageNow:1,
    size:10,
    sortName:"",
    order:""
})

  // 总条目数
let total=0
//定义查询框绑定的姓名
const stuName=ref("")

//获取当前老师编号
const teaId=parseInt(sessionStorage.getItem("id"))

//模态框基础值
const dialog = {
  open: true,
  title: "请选择具体考试信息和班级信息",
  readonly:false,
};

const dialogData = reactive(JSON.parse(JSON.stringify(dialog)));


const openT=()=>{
  dialogData.open=true
//   console.log(dialogData.open)
}
const openF=()=>{
  dialogData.open=false
}

//接收查询出来的考试信息
const examInfo=reactive({ list:[] })

//查询考试信息
examApi.selectAllExam().then((r)=>{
  examInfo.list=r.data.data
  // console.log(examInfo.list)
})


//接收查询出来的班级信息
const classInfo=reactive({ list:[] })

//查询班级信息
classApi.selectByTeaIdList(teaId).then((r)=>{
    if(r.data.ok){
        console.log(r.data.data)
        classInfo.list=r.data.data
    }else{
        ElMessage.error(r.data.message)
    }
})

//成绩分页查询
const scorePage=()=>{
    console.log("调用分页查询方法")
  scoreApi.selectExamIdClassId(pageObj)
  .then((r)=>{
    // console.log(r);
    if(r.data.ok){
      scoreList.list=r.data.data.list
      total=r.data.data.total
      // console.log(scoreList.list.classId)
      // console.log(scoreList.list)
      // console.log("page"+total)
    }
  })
}

//查询方法
const selectByExamIdClassId=()=>{
    pageObj.pageNow=1
    console.log(pageObj)
    if((pageObj.examId==null || pageObj.examId=="") && (pageObj.classId==null || pageObj.classId=="")){
        ElMessage.error("请先选择考试编号或班级编号")
    }else{
        scorePage()
    }
    openF()
}

//清除以选择数据
const clear=()=>{
    if(pageObj.classId=="" && pageObj.examId==""){
        ElMessage.info("当前没有选择任何信息")
    }else{
        pageObj.examId=""
        pageObj.classId=""
    }
}

//排序按钮改变触发
const sortTable=(custom)=>{
    if((pageObj.examId==null || pageObj.examId=="") && (pageObj.classId==null || pageObj.classId=="")){
        ElMessage.error("请先选择考试编号或班级编号")
        return
    }
    pageObj.sortName=custom.prop
    if (custom.order=="ascending") {
        pageObj.order="ASC"
        pageObj.pageNow=1
        scorePage()

    }else if(custom.order=="descending"){
        pageObj.order="DESC"
        pageObj.pageNow=1
        scorePage()
    }else{
        pageObj.order=""
        scorePage()
    }
}

const changePageSize=()=>{
    scorePage()
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