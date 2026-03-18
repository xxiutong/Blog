<template>
    <div style="width:100%;height:100%;background-color:white;overflow:auto;">
        <div class="main">
            <el-card style="max-width: 480px">
                <p>使用微服务技术栈开发：
                </p>
                <p>后端使用SpringCloud,Springboot,Mybatis-plus等，前端使用VUE3开发。实现了包括博客发布、博客浏览、递归评论、按文章标题和按内容搜索，并对搜索到的标题段高亮显示,附件上传下载等。
                博客发布使用WangEditor,并使用ElasticSearch实现按文章标题和按内容搜索，并对搜索到的标题段高亮显示。使用feign实现服务间远程调用，使用kafka消息队列实现删除文章时对删除评论服务的异步调用。</p>

                <p>
                    后面将使用MongoDB+协同过滤算法开发博客推荐功能
                </p>
                <h1>作者：张亿通</h1>
                <h1>zhangyitong27@163.com</h1>
                
            </el-card>
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
const user=JSON.parse(window.sessionStorage.getItem("user"))
const categorylist=ref([])
const dialogFormVisible=ref(false);
const newCategory=ref('');
import {getCategories} from '../utils/api.js'
onMounted(async()=>{


})




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
    p{
        text-indent:2em;
    }
</style>
