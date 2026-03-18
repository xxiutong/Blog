<template>
    <div style="width:100%;height:100%;background-color:white;overflow:auto;">
        <div class="main">
            <el-card style="width: 100%;margin: 0 auto">
                <el-page-header @back="router.go(-1)">

                </el-page-header>
                <el-descriptions :column="1" title="博客详情" size="default" border style="margin-top: 10px">
                <el-descriptions-item label="标题">{{ blog.title }}</el-descriptions-item>
                <el-descriptions-item label="描述">{{ blog.profile }}</el-descriptions-item>
                <el-descriptions-item label="类别">
                    <el-tag>
                        <el-icon><CollectionTag /></el-icon>
                        {{ blog.category.name }}
                    </el-tag>
                    
                </el-descriptions-item>

                <el-descriptions-item label="作者">
                    <el-tag>
                    <el-icon>
                        <Avatar/>
                    </el-icon>
                    {{ blog.author.name }}
                    <!-- {{ blog.author.id }} -->
                    </el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="时间">{{ blog.formatTime }}</el-descriptions-item>
                </el-descriptions>
                <el-divider content-position="center">博客内容</el-divider>
                <div v-html="blog.content" style="width: 100%;margin: 0 auto"></div>
                <!-- <u-comment :config="config" @submit="submit" @like="like"> -->
                <!-- <template>导航栏卡槽</template>
                <template #info>用户信息卡槽</template>
                <template #card>用户信息卡片卡槽</template> -->
                <!-- <template #opearte>操作栏卡槽</template> -->
                <!-- </u-comment> -->

                <el-divider content-position="center">附件</el-divider>
                <div style="display: flex; flex-direction: column; justify-content: center;align-items: center;" v-if="blog.attachment!=undefined">
                    <div style="width: fit-content;">
                        <img src="../assets/imgs/zip.jpg" style="display: block; margin: 0 auto; max-width: 60px; height: auto;" />
                    </div>
                    <div style="width: fit-content;">
                        附件.zip
                    </div>
                    <div>
                        <el-button type="primary" size="small" @click="downloadzip">
                            下载<el-icon><Download /></el-icon>
                        </el-button>
                    </div>
                </div>
                <el-divider content-position="center">评论区</el-divider>
                <div style="padding: 0px">
                    <u-comment :config="config" @submit="submit" @like="like">
                    <!-- <template #list-title>全部评论</template> -->
                    </u-comment>
                </div>
            </el-card>
            <div style="height: 20px;width: 100%;background-color: white;">

            </div>
        </div>
    </div>
</template>

<script setup>
import axios from '../utils/request.js'
import { onMounted, ref,reactive} from 'vue';
import router from "../router/index.js";
import { ElMessage } from 'element-plus'
import { ArrowRight } from '@element-plus/icons-vue'
import { Search } from '@element-plus/icons-vue'
import {getDetail} from '../utils/api.js'

import {UToast} from 'undraw-ui'
import emoji from '../emoji/emoji'
const cuser=JSON.parse(window.sessionStorage.getItem("user"))
const id=ref('');
const blog=ref({
    category:{},
    author:{}
})
onMounted(async()=>{
    id.value=router.currentRoute.value.query.id;

    blog.value=(await getDetail(id.value)).data.data;
    // console.log(blog.value)
    const req = await axios.get(`/comment/${id.value}`)
    config.comments = req.data.data
})

function downloadzip(){
    window.location.href = `${blog.value.attachment}`
}





// const config = ref({
//   user: {
//     id: 1,
//     username: 'user',
//     avatar: 'https://static.juzicon.com/avatars/avatar-200602130320-HMR2.jpeg?x-oss-process=image/resize,w_100',
//     // 评论id数组 建议:存储方式用户id和文章id和评论id组成关系,根据用户id和文章id来获取对应点赞评论id,然后加入到数组中返回
//     likes: [1, 2, 11]
//   },
//   emoji: emoji,
//   comments: []
// })

const config = reactive({
//   user: systemStore.userInfo,
  user: {
    id: cuser.id,
    username: cuser.name,
    avatar: cuser.avatar,
    // 评论id数组 建议:存储方式用户id和文章id和评论id组成关系,根据用户id和文章id来获取对应点赞评论id,然后加入到数组中返回
    // likes: [1, 2, 11]
  },
  emoji: emoji,
  comments: [],
  showLevel: false,
  showHomeLink: false,
  showAddress: false,
  showLikes: false
})
const submit = async ({content, parentId, files, finish}) => {
  console.log('提交评论: ' + content, parentId, files)

  const comment = {

    parentId: parentId ? parentId : 0,
    aid: id.value,
    uid: config.user.id,
    content: content,
    user: {
      username: config.user.username,
      avatar: config.user.avatar
    },
    reply: null
  }
  await axios.post(`/comment/post`, comment)
  // await load()
//   const req = await axios.get(`/comment/${id.value}`)
//   config.comments = req.data.data
//   window.location.reload();
    try{
        finish(comment)
    }catch(e){
        window.location.reload();
    }
  
  UToast({message: '评论成功!', type: 'info'})


  // setTimeout(() => {
  //   finish(comment)
  //   UToast({ message: '评论成功!', type: 'info' })
  // }, 200)
}



</script>

<style scoped lang="less">
    .main{
        padding-left:30px;padding-top:30px;padding-right:60px;
        overflow:auto;
        height:calc(100% - 30px);  
        width:calc(100% - 80px - 30px -60px);
        color:black;
    }

    h1{
        font-size:1.6rem;
        font-weight: 400;
    }
    a{color:#2a408e;}


</style>
