<template>
  <div>
    <el-card class="box-card" style="width: 1200px;">
      <template #header>
        <div class="card-header">
          <span>成绩管理</span>
          <el-button type="primary" class="button" @click="selectByExamId" style="margin-left: -360px;">返回成绩列表</el-button>
          <el-row :gutter="20" style="margin-right: -360px;margin-left: -150px;">
            <el-input v-model="stuName" class="w-50 m-2" placeholder="请输入学生姓名" :prefix-icon="Search" />
          </el-row>
          <el-button type="primary" class="button" @click="selectStuScore">查询</el-button>
          <el-button type="primary" class="button" @click="openT">选择考试信息</el-button>
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
        <el-table-column fixed="right" prop="totalPoints" label="总分" sortable=custom width="120" />
        <!-- <el-table-column fixed="right" label="操作" width="120">
          <template #default="scope">
            <el-button-group>
            <el-button size="small" type="warning" @click="doUpdate(scope.row)">修改</el-button>
            <el-button size="small" type="danger" @click="doDelete(scope.row)">删除</el-button>
            </el-button-group>
          </template>
        </el-table-column> -->
      </el-table>
      <el-pagination background style="padding-top: 15px" @size-change="findSizeChange" @current-change="findPage"
        v-model:current-page.sync="pageNow" :page-sizes="[2, 4, 6, 8, 10]" v-model:page-size="size"
        layout="total, sizes, prev, pager, next, jumper" v-model:total="total">
      </el-pagination>
    </el-card>
  </div>

  <!-- 选择具体考试模态框 -->
  <el-dialog v-model="dialogData.open" :title="dialogData.title" width="500px" draggable>
    <el-select v-model="examId" filterable placeholder="请选择考试信息" @change="handleChange">
      <el-option v-for="item in examInfo.list" :key="item.examId" :label="item.examName" :value="item.examId" />
    </el-select>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="selectByExamId(examId.value)">确定</el-button>
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


//接收查询出来的成绩数据 并跟表格数据绑定
const scoreList = reactive({ list: [] })


//设置初始分页信息
// 每页显示条目个数
let size = 10
// 总条目数
let total = 0
//当前页数
let pageNow = 1
//排序字段名
let sortName
//排序方法
let order


//成绩分页方法
const scorePage = () => {
  scoreApi.selectAllPage(examId.value, pageNow, size)
    .then((r) => {
      // console.log(r);
      if (r.data.ok) {
        scoreList.list = r.data.data.list
        total = r.data.data.total
        // console.log(scoreList.list.classId)
        // console.log(scoreList.list)
        // console.log("page"+total)
      }
    })
}

//接收查询出来的考试信息
const examInfo = reactive({ list: [] })

//查询考试信息
examApi.selectAllExam().then((r) => {
  examInfo.list = r.data.data
  // console.log(examInfo.list)
})


//选择的考试编号
const examId = ref()

//模态框基础值
const dialog = {
  open: true,
  title: "请选择具体考试信息",
  readonly: false,
};

const dialogData = reactive(JSON.parse(JSON.stringify(dialog)));


const openT = () => {
  dialogData.open = true
  console.log(dialogData.open)
}
const openF = () => {
  dialogData.open = false
}

//当选择下拉框选中值发生变化时触发该事件
const handleChange = (val) => {
  // console.log(examId.value)
}

const selectByExamId = () => {
  // console.log(examId.value.type)
  pageNow = 1
  if (examId.value == null) {
    ElMessage.error("请先选择考试信息")
  } else {
    scorePage()
  }
  openF()
}

//成绩排序方法
const scorePageSort = () => {
  if (examId.value == null) {
    ElMessage.error("请先选择考试信息")
    return
  }
  scoreApi.selectAllPageSort(examId.value, pageNow, size, sortName, order)
    .then((r) => {
      if (r.data.ok) {
        scoreList.list = r.data.data.list
        total = r.data.data.total
      }
    })
}

//对表单数据进行排序
const sortTable = (custom) => {
  // console.log(custom)
  sortName = custom.prop
  if (custom.order == "ascending") {
    order = "ASC"
    pageNow = 1
    scorePageSort()
    // console.log(order)
  } else if (custom.order == "descending") {
    order = "DESC"
    pageNow = 1
    scorePageSort()
    // console.log(order)
  } else {
    order = null
    scorePage()
    // console.log(order)
    // console.log(custom)
  }
}

// onMounted(() => {
//   scorePage();
// });

//改变每页条数size时调用
const findSizeChange = (size) => {
  console.log("当每页条数改变的时候" + size);
  if (order != null) {
    scorePageSort()
  } else {
    scorePage()
  }
}

//页码pageNow发生改变时调用
const findPage = () => {
  console.log(pageNow)
  if (order != null) {
    scorePageSort()
  } else {
    scorePage()
  }
}

//定义查询框绑定的姓名
const stuName = ref("")

//根据学生姓名和考试id查询学生具体成绩方法
const selectStuScore = () => {
  //对输入框进行非空判断和空格判断
  // console.log(examId.value)
  if (examId.value == null) {
    ElMessage.error("请先选择考试编号")
    return
  }
  if (stuName.value.indexOf(" ")!=-1) {
    ElMessage.error("输入的查询信息不能包含空格")

  } else if (stuName.value == '' || stuName.value.split(" ").join("").length == 0) {
    ElMessage.error("姓名不能为空！！！")
  } else {
    scoreApi.selectByStuNameScore(examId.value, stuName.value)
      .then((r) => {
        if (r.data.ok) {
          scoreList.list = r.data.data.list
          total = r.data.data.total
        } else {
          ElMessage.error(r.data.message)
        }
      })
  }
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