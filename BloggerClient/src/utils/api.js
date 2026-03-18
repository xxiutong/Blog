import axios from '../utils/request.js'

export async function getCategories(){
    const resp=await axios.get("/category/list");
    if(resp.data.code===200){
        
        return resp;
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


export async function getBlogs(page,pageSize,query,categoryId){
    const resp=await axios.get(`/blog/page?page=${page.value}&pageSize=${pageSize.value}&query=${query.value}&categoryId=${categoryId.value}`);
    if(resp.data.code===200){
        return resp;
    }else{
        ElMessage(
            {
                type:"warning",
                showClose:true,
                message:resp.data.errorMessage
            }
        )
        return resp;
    }
    // console.log(resp)
}

export async function getDetail(id){
    const resp=await axios.get(`/blog/detail?id=${id}`);
    if(resp.data.code===200){
        return resp;
    }else{
        ElMessage(
            {
                type:"warning",
                showClose:true,
                message:resp.data.errorMessage
            }
        )
        return resp;
    }
}