import axios from "axios";
import router from "../router";
// import Element from "element-ui"

axios.defaults.baseURL = "http://localhost:51600/api"
// axios.defaults.baseURL = "http://localhost:51801/api"


const request = axios.create({
    timeout: 5000,
    headers: {
        'Content-Type': "application/json; charset=utf-8"
    }
})

request.interceptors.request.use(config => {

    config.headers['token'] = localStorage.getItem("token")

    return config
})

request.interceptors.response.use(
    response => {


        let res = response.data

        if (res.code === 200) {
            return response
        } else {
            return response
            // return Promise.reject(response.data.msg)
        }
    },
    error => {

        if (error.response) {
            // 确保 error.response 存在后再访问其属性
            if (error.response.status === 401) {
                router.push("/backlogin");
                return false;
            }
            if (error.response.data) {
                error.message = error.response.data.msg;
            }
        } else {
            // 处理没有 response 的错误（如网络错误等）
            error.message = "Network Error or Server did not respond";
        }
        // Element.Message.error(error.message, { duration: 3000 });
        // Element.Loading.zyt.comment.service().close();
        return Promise.reject(error);
    }
)
export default request
