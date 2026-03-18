<template>
    <div style="width:100%;height:100%;background-color:white;overflow:auto;">
        <div class="blogs">
            <div v-for="(item, index) in bloglist"
            :key="index.toString()"
            class="blog">
                <h1  @click="handleDetail(item.id)">
                    <a href="#">
                        <!-- {{item.title}} -->
                        <div v-html="item.h_title"></div>
                    </a>
                </h1>
                <p class="ellipsis">
                    {{item.profile}}
                </p>
                <div style="width:100%;height:30px;margin-top:6px;margin-bottom:10px; position: relative;">
                    <span><el-icon><Calendar /></el-icon> {{ item.formatTime }}</span>
                    <span style="margin-left:15px"><el-icon><Files /></el-icon>{{item.categoryName}}</span>
                    <!-- <span style="position: absolute; right: 10px;">
                        <el-button type="primary" size="small" @click="update(item.id)" v-if="user.role==='host'">修改</el-button>
                        <el-button type="danger" size="small" @click="del(item.id)" v-if="user.role==='host'">删除</el-button>
                    </span> -->
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
import { getBlogs } from '../utils/api';
const user=JSON.parse(window.sessionStorage.getItem("user"))
const bloglist=ref([])
const query=ref("")
const handleDetail=(id)=>{
    router.push(`/Detail?id=${id}`)
}
// const del=async(id)=>{
//     const res=await axios.delete(`/blog/${id}`);
//     const resp=await getBlogs(page,pageSize,query,categoryId)
//     total.value=resp.data.data.total;
//     bloglist.value=resp.data.data.records;
//     if(res.data.code===200){
//         ElMessage(
//             {
//                 type:"success",
//                 showClose:true,
//                 message:"删除成功"
//             }
//         )
//     }else{
//         ElMessage(
//             {
//                 type:"warning",
//                 showClose:true,
//                 message:res.data.errorMessage
//             }
//         )
//     }
// }
// const update=async(id)=>{
//     router.push(`/Publish?id=${id}`);

// }
onMounted(async()=>{
    query.value=router.currentRoute.value.query.query;
    query.value= query.value===undefined?'':query.value
    const resp=await axios.get(`/blog/search?query=${query.value}`)
    bloglist.value=resp.data.data;
    console.log(resp.data.data)
})
</script>
<style scoped lang="less">
.blogs{
    padding-left:30px;padding-top:30px;padding-right:60px;
    overflow:auto;
    height:calc(100% - 30px);  
    width:calc(100% - 80px - 30px -60px);
    color:black;
    .blog{
        width:100%;
        // height:180px;
        border-bottom: 1px solid #f3f3f3;
        h1{
            font-size:1.2rem;
            a{
                color:#2a408e;}
        }

        p{
            color:dimgray;
            font-size:0.9rem;
            margin:0 0 0 0;
        }

        span{
            color:dimgray;
            font-size:0.6rem;
            text-align:center;
        }


        
    }

    .pagination-block{
        width:100%;
        height:30px;
        margin-top: 20px;
        margin-bottom: 80px;
        // margin-left:30px;
    }

    .ellipsis {
        overflow: hidden;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        text-overflow: ellipsis;
        white-space: normal;
    }
}
</style>