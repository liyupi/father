import {createApp} from 'vue';
import * as VueRouter from 'vue-router';
import {
    Button,
    Modal,
    List,
    Menu,
    Row,
    Col,
    Image,
    Layout,
    Pagination,
    Input,
    Form,
    message,
    Divider,
    BackTop,
    Upload,
    Skeleton,
    Typography,
    InputNumber,
    Radio,
    Table,
    Tabs,
    Select,
} from 'ant-design-vue';
import Vue3ColorPicker from "vue3-colorpicker";
import App from './App';
import routes from './config/routes';
import store from './store'
import './config/axiosConfig';
import 'ant-design-vue/dist/antd.css';
import './global.css';
import "vue3-colorpicker/style.css";

const router = VueRouter.createRouter({
    history: VueRouter.createWebHistory(),
    routes,
})

const app = createApp(App);
app.config.productionTip = false;
app.config.globalProperties.$message = message;

app.use(router);
app.use(store);
// 局部加载，大概能省 1/5 的空间
app.use(Button).use(Modal).use(List).use(Menu).use(Row).use(Col).use(Image).use(Layout).use(Pagination)
    .use(Input).use(Form).use(Divider).use(BackTop).use(Upload).use(Skeleton).use(Typography).use(Table)
    .use(InputNumber).use(Radio).use(Tabs).use(Select);
app.use(Vue3ColorPicker);
app.mount('#app');


