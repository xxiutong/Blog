<template>

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
            <el-menu-item index="/Publish" >
                <div class="title">
                    <span >发布</span>
                </div>
                
            </el-menu-item>
            <el-menu-item index="/Categorys" >
                <div class="title">
                    <span >分类</span>
                </div>
                
            </el-menu-item>
            
            <el-menu-item index="4">
                <div class="title">
                    <span >关于本站</span>
                </div>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script setup>

</script>
<script>
    export default {
        name: 'categorymenu'
    }
</script>
<style scoped lang="less">
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