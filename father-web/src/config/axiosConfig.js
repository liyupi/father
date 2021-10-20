/**
 * 全局 Axios 配置
 * @author liyupi
 */
import axios from "axios";

// 根据环境设定请求后端 url 地址
axios.defaults.baseURL = process.env.NODE_ENV === 'production' ?
    '换成自己的线上地址' : 'http://localhost:8081/api';

// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    response = response.data;
    if (response?.code !== 0) {
        return Promise.reject(response);
    }
    // 对响应数据做点什么
    return response;
}, function (error) {
    // 对响应错误做点什么
    return Promise.reject(error);
});
