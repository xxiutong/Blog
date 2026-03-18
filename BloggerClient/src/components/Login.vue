<template>
    <div class="login_container">
        <div class="login_box">
            <div class="avatar_box">
                <img src="../assets/vue.svg" alt=""/>
            </div>
            <el-form :model="loginForm" class="login_form" label-width="0">
                <div  class="title">
                    <h3>ZYT个人博客前台</h3>
                </div>
                
                <el-form-item prop="phone">
                    <el-input v-model="loginForm.phone" placeholder="电话">
                        <template #prefix>
                            <el-icon><Iphone /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>

                <el-form-item prop="password">
                    <el-input v-model="loginForm.password" type="password" placeholder="密码">
                        <template #prefix>
                            <el-icon><Lock /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item >
                    <div class="btn">
                        <div>
                            <el-button type="primary" style="margin-top: 20%;" @click="login">登录</el-button>
                        </div>
                    </div>
  
                </el-form-item>

                
            </el-form>
        </div>
    </div>
</template>
<script setup>

import axios from "../utils/request.js"
import { ref } from "vue"
import router from "../router/index.js";
import { ElMessage } from 'element-plus'
    const loginForm=ref({
        phone:'',
        password:''
    })

    async function login(){
        const resp=await axios.post("/user/login",loginForm.value);
        if(resp.data.code===200){
            window.sessionStorage.setItem("user",JSON.stringify(resp.data.data.user))
            window.localStorage.setItem("token",resp.data.data.token)
            ElMessage(
                {
                    type:"success",
                    showClose:true,
                    message:"登陆成功！"
                }
            )
            setTimeout(() => {
                router.push('/home')
            }, 500);     
        }else{
            ElMessage(
                {
                    type:"warning",
                    showClose:true,
                    message:resp.data.errorMessage
                }
            )
        }
        // console.log(resp)
    }

</script>

<style lang="less" scoped>
    .login_container{
        // background-color: #2b4b6b;
        background-image: url('../assets/back.png');
        background-size:cover;
        background-position: center;
        height: 100%;
        width: 100%;
        // padding: 120px;
        // margin: 0 auto;

    }
    .login_box{
        opacity: 0.8;
        width: 450px;
        height: 350px;
        background-color: #fff;
        border-radius: 5px;
        position: absolute;
        left: 70%;
        top:50%;
        transform: translate(-50%,-50%);
        .avatar_box{
            width: 130px;
            height: 130px;
            border: 1px solid #eee;
            border-radius: 50%;
            padding: 10px;
            box-shadow: 0 0 1px #ddd;
            position: absolute;
            left: 50%;
            transform: translate(-50%,-50%);
            background-color: #0ee;
            img{
                width: 100%;
                height: 100%;
                border-radius: 50%;
                background-color: #fff;
            }
        }
    }
    .login_form{
        position: absolute;
        bottom: 0%;
        width: 100%;
        padding: 0 10px;
        box-sizing:border-box
    }
    .btn{
        width: 100%;
        // margin: 0 auto;
        display: flex;
        justify-content: center;
        // margin-left: 10%;
        
    }
    .title{
        width: fit-content;
        height: fit-content;
        position: absolute;
        color: black;
        // top: 0%;
        left: 50%;
        
        transform: translate(-50%,-100%);
    }

</style>
