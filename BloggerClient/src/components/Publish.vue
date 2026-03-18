<template>
    <div style="width:100%;height:100%;background-color:white;overflow:auto;">
        <div class="publish">
            <!-- <el-page-header @back="$route.go(-1)">

            </el-page-header> -->
            <el-form
                ref="ruleFormRef"
                :model="form"
                :rules="rules"
                label-width="120px"
                class="ruleForm"
                :size="formSize"
                status-icon
            >

            <el-form-item label="标题" prop="name">
                <el-input v-model="form.title"/>
            </el-form-item>


            <el-form-item label="所属分类" prop="categoryId">

                <el-select v-model="form.categoryId" placeholder="选择分类" style="width:100%">
                    <el-option
                        v-for="(item, index) in categorylist"
                        :label="item.name"
                        :value="item.id"
                        :key="index.toString()"
                    ></el-option>
                </el-select>


            </el-form-item>

            <el-form-item label="博客摘要" prop="profile">

                <el-input
                    v-model="form.profile"
                    style="width: 65%"
                    type="textarea"
                    resize="none"
                    placeholder="摘要：帮读者快速了解内容"
                />

            </el-form-item>

            <el-form-item label="博客详情" prop="content">
                <div style="z-index: 1001;">
                    <div style="border: 1px solid #ccc">
                        <Toolbar
                            style="border-bottom: 1px solid #ccc"
                            :editor="editorRef"
                            :defaultConfig="toolbarConfig"
                            mode="default"
                        />
                        <Editor
                            style="height: 500px; overflow-y: hidden;"
                            v-model="form.content"
                            :defaultConfig="editorConfig"
                            mode="default"
                            @onCreated="handleCreated"
                        />
                    </div>
                </div>
                
            </el-form-item>

            <el-form-item label="附件上传" style="width:100%">
                
                <el-upload
                    ref="uploadRef"
                    class="upload-demo"
                    accept=".zip"
                    action="http://localhost:51600/api/files/upload_zip"
                    :on-success="handle_upzip"
                    :onChange="handleChangeFile"
                    :limit="1"
                    :on-exceed="handleExceed"
                    :auto-upload="false"
                >
                    <template #trigger>
                    <el-button type="primary">上传ZIP</el-button>
                    </template>

                    <template #tip>
                    <div class="el-upload__tip text-red">
                        限制上传1个ZIP文件，新的文件将进行覆盖
                    </div>
                    </template>
                </el-upload>
            </el-form-item>

            </el-form>

            <div style="width:100%; margin-top:30px;margin-bottom:60px;padding-left:30px;">
                <div style="width:88px;margin:0 auto;">
                    <el-button type="primary" @click="SubmitForm()"> 发布博客 </el-button>
                </div>
                
            </div>
        </div>


        
    </div>
</template>

<script setup>
import axios from '../utils/request.js'
import { onMounted, ref } from 'vue';
import router from "../router/index.js";
import { ElMessage } from 'element-plus'
import { ArrowRight } from '@element-plus/icons-vue'
import { Search } from '@element-plus/icons-vue'
import {getCategories} from '../utils/api.js'

import {getDetail} from '../utils/api.js'
const user=JSON.parse(window.sessionStorage.getItem("user"))

const lenfiles=ref(0)

// 表单数据定义
const form = reactive({
  id:undefined,
  title:undefined,
  profile:undefined,
  categoryId:undefined,
  content:undefined,
  attachment:undefined,
})
const uploadRef = ref(null)
function resetForm() {
  form.id = undefined
  form.title = undefined
  form.profile=undefined
  form.categoryId = undefined
  form.content = undefined
  form.attachment = undefined

  
  if(lenfiles.value>0){
    uploadRef.value.clearFiles();
    uploadRef.value = null
  }
  lenfiles.value=0
  
}
const categorylist =ref([])

async function Sub(){
    const resp=await axios.post("/blog/sub",form);
    if(resp.data.code===200){
        ElMessage(
            {
                type:"success",
                showClose:true,
                message:"发布成功"
            }
        );
        setTimeout(() => {
            // console.log("2 seconds later: Action performed!");
            resetForm();
        }, 2000);
        
        
    }else{
        ElMessage(
            {
                type:"warning",
                showClose:true,
                message:resp.data.errorMessage
            }
        )
    }
}

function handle_upzip(res){

    ElMessage.success('上传附件成功')

    form.attachment=res.data.url;
    Sub();
}

async function SubmitForm(){
    
    console.log(lenfiles.value)
    if (lenfiles.value>0) {
        uploadRef.value.submit()
    }else{
        Sub();
    } 
}

onMounted(async()=>{
    const resp=await getCategories()
    categorylist.value=resp.data.data;

    form.id=router.currentRoute.value.query.id;

    if(form.id!=undefined){
        const res=(await getDetail(form.id));
        const detail=res.data.data;
        form.id=detail.id;
        form.title=detail.title;
        form.profile=detail.profile;
        form.categoryId=detail.category.id;
        form.content=detail.content;
        form.attachment=detail.attachment;
    }

})

import {  shallowRef,onBeforeUnmount, reactive} from 'vue';

import '@wangeditor/editor/dist/css/style.css' // 引入 css
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

const editorRef = shallowRef()
const mode = 'default'
const valueHtml = ref('')
const toolbarConfig = { }
const editorConfig = { placeholder: '请自行添加博客详情...',
  MENU_CONF: {}
}
editorConfig.MENU_CONF['uploadImage'] = {
  server: 'http://localhost:51600/api/files/upload_picture',
  fieldName: 'multipartFile'
  // 继续写其他配置...

  //【注意】不需要修改的不用写，wangEditor 会去 merge 当前其他配置
}

editorConfig.MENU_CONF['uploadVideo'] = {
    server: 'http://localhost:51600/api/files/upload_video',
    fieldName: 'multipartFile'
}
    const handleChange = (editor) => {
    //   console.log('change:', editor.getHtml());
    };


    onBeforeUnmount(() => {
        const editor = editorRef.value
        if (editor == null) return
        editor.destroy()
    })
    const handleCreated = (editor) => {
      editorRef.value = editor // 记录 editor 实例，重要！
    }
    

import { genFileId } from 'element-plus'



const handleExceed = (files) => {
  upload.value.clearFiles()
  const file = files[0]
  file.uid = genFileId()
  upload.value.handleStart(file)
}

const handleChangeFile=(uploadFile, uploadFiles)=>{
    lenfiles.value=uploadFiles.length
}

</script>

<style scoped lang="less">
.publish{
    padding-top:30px;padding-right:60px;padding-bottom:50px;
    overflow:auto;
    height:calc(100% - 30px -50px);  
    width:calc(100% - 80px -60px);
    color:black;

    // border-bottom: 1px solid white;

}

.el-form--inline .el-form-item {
  margin-right: 10px;
}

.form_header {
  background-color: #666666;
}

.pagination {
  margin-top: 10px;
}

.el-button {
  height: 28px;
  width:88px;
  border-radius: 3px;
}

.my-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.el-textarea {
  height: 140px;
  :deep(.el-textarea__inner) {
    height: 140px;
  }
}
</style>