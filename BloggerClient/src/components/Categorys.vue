<template>
    <div style="width:100%;height:100%;background-color:white;overflow:auto;">
        <div class="main">

            <div style="display: flex; justify-content: space-between; align-items: center;">
                <div>
                    <h1>
                        目录
                    </h1>
                </div>
                <div>
                    <el-button type="primary" @click="dialogFormVisible = true" v-if="user.role==='host'">+ 新建类别</el-button>
                </div>
                
            </div>
            <div class="cates">
                <div v-for="(item, index) in categorylist"
                :key="index.toString()" 
                style="background-color: #eeeeee;width: 100%;height: 40px;display: flex;align-items: center; position: relative; margin-top: 10px;">
                    <span style="margin-left: 5px;"><el-icon><Folder /></el-icon> </span> <span style="margin-left: 6px;"> <router-link :to="`/Blogs?categoryId=${item.id}`">{{ item.name }}</router-link></span> <span style="position: absolute;right: 20px;"> <el-icon><ArrowRight /></el-icon></span>
                    <span style="position: absolute; right: 10px;"> <el-button type="danger" @click="del(item.id)" size="small" v-if="user.role==='host'">删除</el-button>  </span>
                </div>
            </div>
        </div>

        <el-dialog v-model="dialogFormVisible" title="添加类别" width="500">
            
            <el-input v-model="newCategory" autocomplete="off" />

            <template #footer>
            <div class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="add">
                确认
                </el-button>
            </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import axios from '../utils/request.js'
import { onMounted, ref } from 'vue';
import router from "../router/index.js";
import { ElMessage } from 'element-plus'
import { ArrowRight } from '@element-plus/icons-vue'
import { Search } from '@element-plus/icons-vue'
const user=JSON.parse(window.sessionStorage.getItem("user"))
const categorylist=ref([])
const dialogFormVisible=ref(false);
const newCategory=ref('');
import {getCategories} from '../utils/api.js'
onMounted(async()=>{

    const resp=await getCategories()
    console.log(resp.data.data)
    categorylist.value=resp.data.data;
    // console.log(router.currentRoute.value.matched)
})

const del=async(id)=>{

    const resp=await axios.delete(`/category/${id}`);

    if(resp.data.code===200){
        const resp2=await getCategories();
        categorylist.value=resp2.data.data;
        ElMessage(
        {
            type:"success",
            showClose:true,
            message:"删除成功！"
        })
        
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

async function add(){
    dialogFormVisible.value = false
    const resp=await axios.post(`/category`,{'name':newCategory.value,});

    if(resp.data.code===200){
        const resp2=await getCategories();
        categorylist.value=resp2.data.data;
        ElMessage(
        {
            type:"success",
            showClose:true,
            message:"添加成功！"
        })
        
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

</script>

<style scoped lang="less">
    .main{
        padding-left:30px;padding-top:30px;padding-right:60px;
        overflow:auto;
        height:calc(100% - 30px);  
        width:calc(100% - 80px - 30px -60px);
        color:black;

        // .cates{

        // }
    }

    h1{
        font-size:1.6rem;
        font-weight: 400;
    }
    .router-link-active {
        text-decoration: none;
        color: #2a408e;
    }
    .router-link {
        text-decoration: none;
        color: #2a408e;
    }
    a {
        text-decoration: none;
        color: #2a408e;
    }
    // router-link{color:#2a408e;}
</style>
