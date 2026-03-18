// 路由文件
import { createRouter, createWebHistory } from "vue-router";

import Home from '../components/Home.vue'
import BackHome from '../components/BackHome.vue'
import BackLogin from '../components/BackLogin.vue'

import Blogs from '../components/Blogs.vue'
import Publish from '../components/Publish.vue'
import Categorys from '../components/Categorys.vue'
import Detail from '../components/Detail.vue'
import Login from '../components/Login.vue'
import About from '../components/About.vue'
import SearchResult from '../components/SearchResult.vue'
import No404 from '../components/404.vue'
const routes = [
    {
        path: '/',
        redirect:"/backlogin",
    },
    {
        path: '/backhome',
        name: 'backhome',
        meta:{
            name:"系统后台"
        },
        component: BackHome,
        redirect:"/Blogs",
        children:[
            {
                path:'/Blogs',
                component: Blogs,
                meta:{
                    name:"首页"
                },
            },

            {
                path:'/Detail',
                component: Detail,
                meta:{
                    name:"详情"
                },
            },
            {
                path:'/Categorys',
                component: Categorys,
                meta:{
                    name:"分类"
                }
            },
            {
                path:'/Publish',
                component: Publish,
                meta:{
                    name:"发布"
                }
            },
            {
                path:'/About',
                component: About,
                meta:{
                    name:"关于"
                }
            },
            {
                path:'/SearchResult',
                component: SearchResult,
                meta:{
                    name:"搜索结果"
                }
            }
        ],
    },
    {
        path: '/backlogin',
        name: 'backlogin',
        component: BackLogin
    },
    {
        path: '/home',
        name: 'home',
        meta:{
            name:"系统前台"
        },
        component: Home,
        redirect:"/home/Blogs",
        children:[
            {
                path:'/home/Blogs',
                component: Blogs,
                meta:{
                    name:"首页"
                },
            },

            {
                path:'/home/Detail',
                component: Detail,
                meta:{
                    name:"详情"
                },
            },
            {
                path:'/home/Categorys',
                component: Categorys,
                meta:{
                    name:"分类"
                }
            },
            {
                path:'/home/About',
                component: About,
                meta:{
                    name:"关于"
                }
            },
            {
                path:'/home/SearchResult',
                component: SearchResult,
                meta:{
                    name:"搜索结果"
                }
            }
            // {
            //     path:'/Publish',
            //     component: Publish,
            //     meta:{
            //         name:"发布"
            //     }
            // },
        ],
    },
    {
        path: '/login',
        name: 'login',
        component: Login
    },
    {
        path:'/*',
        name:"404",
        component: No404
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})


// 挂载路由守卫
router.beforeEach((to,from,next)=>{
    if(to.path!="/Publish" && to.path!="/home/Blogs" &&to.path!="/home/Publish" &&to.path!="/home/Detail" &&to.path!="/home/About" &&to.path!="/home/Categorys"){
      return next();
    }
    const user=window.sessionStorage.getItem("user");
    if(user==undefined) return next("/backlogin");
    next();
})

export default router;