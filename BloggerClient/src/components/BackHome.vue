<template>
    <div class="backhome">
        <el-row>
            <el-col :span="4">
                <div class="menulist">
                    <div class="introduction" style="background-color: #eeeeee;">
                        <div class="information">
                            <div class="avater" style="margin-top: 8px;">
                                <img src="../images/avater.jpg" style="width: 100px;height: 100px;border-radius: 50%; "/>
                            </div>

                            <div style="font: bolder; font-size: 25px; height: 40px; color: #34343c;" >
                                {{host.name}}
                                <!-- 张亿通 -->
                            </div>

                            <div style="font-size: 15px; height: 40px;">
                                {{host.motto}}
                                <!-- 张亿通的博客 -->
                            </div>
                        </div>
                        
                        
                    </div>

                    <div class="categorymenu">
                        <el-menu
                            active-text-color="black"
                            background-color="#eeeeee"
                            class="el-menu-vertical-demo"
                            default-active="/Blogs"
                            text-color="gray"
                            router
                        >
                            <el-menu-item index="/Blogs" >
                                <div class="title">
                                    <span >首页</span>
                                </div>
                                
                            </el-menu-item>
                            <el-menu-item index="/Publish" v-if="user.role=='host'">
                                <div class="title">
                                    <span >发布</span>
                                </div>
                                
                            </el-menu-item>
                            <el-menu-item index="/Categorys" >
                                <div class="title">
                                    <span >分类</span>
                                </div>
                                
                            </el-menu-item>
                            
                            <el-menu-item index="/About">
                                <div class="title">
                                    <span >关于本站</span>
                                </div>
                            </el-menu-item>
                        </el-menu>
                    </div>
                </div>

            </el-col>
            <el-col :span="14">
                <div style="height: 100vh;" class="main">
                    <div class="tabbar">
                        <el-row>
                            <el-col :xs="12" :sm="6" :md="4" :lg="10" :xl="12">
                                <!-- <template> -->
                                    <div style="padding-left:50px;color:black">
                                        <el-breadcrumb :separator-icon="ArrowRight">
                                            <el-breadcrumb-item 
                                            v-for="(item,index) in router.currentRoute.value.matched"
                                            :key="index.toString()"
                                            :to="item.path">
                                            {{ item.meta.name }}
                                        </el-breadcrumb-item>


                                        </el-breadcrumb>
                                    </div>
                                    
                                <!-- </template> -->
                            </el-col>

                            <el-col :xs="12" :sm="6" :md="6" :lg="6" :xl="6">
                                <div >
                                    <el-input
                                        v-model="query"
                                        style="max-width: 210px;border-radius:210px"
                                        size="small"
                                        placeholder="搜索..."
                                        >
                                        <template #prepend>
                                            <el-button @click="search" :icon="Search" />
                                        </template>
                                    </el-input>
                                </div>
                            </el-col>

                            <el-col :xs="0" :sm="12" :md="14" :lg="4" :xl="4">
                                <el-avatar
                                    src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" size="33px" style="margin:0 8px;"
                                />
                                <el-dropdown style="margin:0 10px;cursor:pointer">
                                    <span class="el-dropdown-link">
                                        {{ user?.name }}<el-icon class="el-icon--right"><arrow-down /></el-icon>
                                    </span>
                                    <template #dropdown>
                                    <el-dropdown-menu>
                                        <el-dropdown-item @click="handleInformation">修改信息</el-dropdown-item>
                                        <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                                    </el-dropdown-menu>
                                    </template>
                                </el-dropdown>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="mainviews">
                        <router-view></router-view>
                    </div>
                </div>
            </el-col>
            <el-col :span="6">
                <div style="width: 100%;height: calc(100vh - 100px);background-color: #eeeeee; padding-top: 100px;">
                    <div style="width: 100%;background-color: #eeeeee; color: gray;">
                        <h3>MongoDB+协同过滤算法推荐待开发...</h3>
                    </div>
                    
                </div>
            </el-col>
        </el-row>
    </div>
</template>
<script setup>
import axios from '../utils/request.js'
import { onMounted, ref } from 'vue';
import router from "../router/index.js";
import { ElMessage } from 'element-plus'
import { ArrowRight } from '@element-plus/icons-vue'
import { Search } from '@element-plus/icons-vue'
import { getCategories } from '../utils/api';
const user=JSON.parse(window.sessionStorage.getItem("user"))
const query = ref('')
// onMounted(()=>{
//     getCategories()
//     console.log(router.currentRoute.value.matched)
// })

const host=ref({})
onMounted(async()=>{
    const resp=await axios.get(`/user/host`);
    host.value=resp.data.data; 
})
const search=async()=>{
    router.push(`/SearchResult?query=${query.value}`);
}

const logout=()=>{
    router.push("/backlogin")
}
</script>
<style scoped lang="less">
    
    .backhome{
        width: 100%;
        height: 100%;
        background-color: white
    };
    .menulist{
        width: 100%;
        height: 100vh; 
        background-color:#eeeeee;
        overflow: hidden;
        // padding-top: 20%;
        .introduction{
            width: 100%;
            height: 206px;
            margin-top: 20px;
            .information{
                
                color: #34343c;
                height: 100%;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: space-around;
            };
        };
    };
    .main{
        width: 100%;
        overflow: hidden;
        .tabbar{
            width: 100%;
            height: 50px;
            top: 0;
            position: fixed;
            background-color:white;
            // padding-top: 2px;
            // box-shadow: 0px,1px,1px,rgb(0, 0, 0,0.1);
            .el-row{
                height: 100%;
                box-shadow: 0px 1px 1px rgba(0, 0, 0,0.1);
            }
            .el-col{
                height: 100%;
                display: flex;
                align-items: center;
            }
            // background-color:blue;
        }

        .mainviews{
            position:relative;
            top:52px;
            width:calc(100% - 40px);
            overflow: auto;
            padding-left:30px;
            // background-color:red;
            height:calc(100vh - 52px)
            // min-height:calc(100vh - 50px);
        }
    }
    .categorymenu {
        text-align:center;
        .title {
            width: 100%;
        }
        // 主要是这个样式穿透
        :deep(.el-sub-menu__title) {
            padding-right: calc(var(--el-menu-base-level-padding) + var(--el-menu-level)*var(--el-menu-level-padding)) !important;
            :deep(.el-menu-item) {
                padding-left: calc(var(--el-menu-base-level-padding) + var(--el-menu-level)*var(--el-menu-level-padding)) !important;
            }
        }
        :deep(.el-sub-menu .el-sub-menu__icon-arrow ) {
            right: 10px;
        }
    }

    .example-showcase .el-dropdown-link {
        cursor: pointer;
        color: var(--el-color-primary);
        display: flex;
        align-items: center;}
</style>
