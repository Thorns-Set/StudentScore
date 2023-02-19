<template>
  <div>
    <el-card class="box-card" >
        <template #header>
        <div class="card-header">
            <span>房产信息管理</span>
            <el-button type="primary" class="button" @click="doadd()">添加</el-button>
        </div>
        </template>
         <el-table :data="roleList.list" style="width: 100%">
            <el-table-column fixed  prop="date" label="#" width="40px">
                <template #default="scope">
                {{ scope.$index + 1 }}
                </template>
            </el-table-column>
            <el-table-column prop="houseId" label="房产信息编号" width="130px"/>
            <el-table-column prop="code" label="城市编号" width="90px"/>
            <el-table-column prop="regId" label="报名编号" width="90px"/>
            <el-table-column prop="houseType" label="房产证件类型" width="130px"/>
            <el-table-column prop="houseNum" label="房产证件编号" width="130px"/>
            <el-table-column prop="houseName" label="房产所有人姓名"  width="150px"/>
            <el-table-column prop="houseUserType" label="房产所有人身份证证件类型" width="240px" />
            <el-table-column prop="houseUserTypeNum" label="房产所有人身份证证件号码" width="240px"/>
            <el-table-column prop="houseAddress" label="房产地址详情"  width="130px"/>
            <el-table-column prop="houseRelation" label="房产所有人与学生关系"  width="200px"/>
            <el-table-column prop="houseTel" label="房产所有人联系电话" width="180px"/>

            <el-table-column fixed="right" label="操作" align="center">
                <template #default="scope">
                    <el-button-group>
                    <el-button size="small" type="success">查看</el-button>
                    <el-button size="small" type="warning" @click="doUpdate(scope.row)">修改</el-button>
                    <el-button size="small" type="danger" @click="doDelete(scope.row)">删除</el-button>
                    </el-button-group>
                </template>
            </el-table-column>
        </el-table>
    </el-card>
  </div>

    
  <el-dialog v-model="dialog.open" :title="dialog.title+'对话框'" width="600px" draggable>
      <el-form label-position="right" label-width="180px" :model="dialog.form">
          <el-form-item label="房产信息编号" >
              <el-input :readonly="dialog.readonly" v-model="dialog.form.houseId" />
          </el-form-item>
          <el-form-item label="城市编号">
              <el-input v-model="dialog.form.code" />
          </el-form-item>
          <el-form-item label="报名编号">
              <el-input v-model="dialog.form.regId" />
          </el-form-item>
          <el-form-item label="房产证件类型">
              <el-input v-model="dialog.form.houseType" />
          </el-form-item>
            <el-form-item label="房产证件编号">
              <el-input v-model="dialog.form.houseNum" />
          </el-form-item>
          <el-form-item label="房产所有人姓名">
              <el-input v-model="dialog.form.houseName" />
          </el-form-item>
          <el-form-item label="房产所有人身份证证件类型">
              <el-input v-model="dialog.form.houseUserType" />
          </el-form-item>
            <el-form-item label="房产所有人身份证证件号码">
              <el-input v-model="dialog.form.houseUserTypeNum" />
          </el-form-item>
          <el-form-item label="房产地址详情">
              <el-input v-model="dialog.form.houseAddress" />
          </el-form-item>
          <el-form-item label="房产所有人与学生关系">
              <el-input v-model="dialog.form.houseRelation" />
          </el-form-item>
            <el-form-item label="房产所有人联系电话">
              <el-input v-model="dialog.form.houseTel" />
          </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
              <el-button @click="dialog.open = false">关闭</el-button>
              <el-button type="primary" @click="doit()">{{ dialog.title }}</el-button>
          </span>
      </template>
  </el-dialog>
</template>


<script setup>
import { ref, onMounted, reactive } from "vue";
// ref reactive 区别
import { ElMessage, ElMessageBox } from "element-plus";



const roleList = reactive({ list: [] });

const getRoleList = () => {
  api.list().then((r) => {
    console.debug(r);
    // roleList.value = r.data.data;
    roleList.list = r.data.data;
  });
};
onMounted(() => {
  getRoleList();
});

//删除
const doDelete = (row) => {
  console.debug(row);
  ElMessageBox.confirm(`您确定要删除[${row.houseId}]吗?`, "删除确认")
    .then((r) => {
      api.delete(row.houseId).then((r) => {
        console.debug(r);
        if (r.data.ok) {
          ElMessage.success("删除成功");
          getRoleList();
        } else {
          ElMessage.error(r.data.message);
        }
      });
    })
    .catch(() => { });
};

//添加和修改
const dialogData = {//原始数据
  open: false,
  title: "添加",
  readonly:false,
  form: {
    houseId: "",
    code: "",
    roleId: "",
    houseType: "",
    houseNum: "",
    houseName: "",
    houseUserType: "",
    houseUserTypeNum: "",
    houseAddress: "",
    houseRelation: "",
    houseTel: "",
  },
};


    
const dialog = reactive(JSON.parse(JSON.stringify(dialogData)));//响应数据
const doit = () => {
  if (dialog.title == "添加") {
    api.add(dialog.form).then((r) => {
      if (r.data.ok) {
        ElMessage.success("添加成功");
        getRoleList();
        Object.assign(dialog, dialogData);
      }
    });
  } else {
    api.update(dialog.form).then((r) => {
      if (r.data.ok) {
        ElMessage.success("修改成功");
        getRoleList();
        Object.assign(dialog, dialogData);
      }
    });
  }
};

const doUpdate = (row) => {
  dialog.title = "修改";
  //打开模态框
  dialog.open = true;
  //设置只读
  dialog.readonly=true;
  dialog.form = {...row};
}

const doadd = () =>{
  Object.assign(dialog, dialogData);
  dialog.open=true
}

</script>


<style scoped>
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.box-card {
    width: 1000px;
  margin: 1em auto;
}

</style>