<template>
  <div id="baseLayout">
    <a-layout class="layout">
      <a-layout-header class="header">
        <a-row type="flex">
          <a-col flex="180px" class="margin">
            <router-link to="/">
              <div class="logo">
                <img :src="logo" alt="爸爸 - 表情包专业户"/>
                <span class="title">爸爸</span>
              </div>
            </router-link>
          </a-col>
          <a-col flex="auto">
            <a-menu
                theme="dark"
                mode="horizontal"
                :selectedKeys="['/home']"
                :style="{ lineHeight: '64px' }"
            >
              <a-menu-item key="/home">
                <router-link to="/">首页</router-link>
              </a-menu-item>
              <a-menu-item key="/yupi">
                <a href="https://docs.qq.com/doc/DUFFRVWladXVjeUxW" target="_blank">
                  鱼皮
                </a>
              </a-menu-item>
            </a-menu>
          </a-col>
          <a-col>
            <a-button type="primary" @click="doShowCreateModal">
              <template #icon>
                <CloudUploadOutlined/>
              </template>
              上传表情
            </a-button>
          </a-col>
        </a-row>
      </a-layout-header>
      <a-layout-content style="padding: 16px 50px">
        <router-view/>
      </a-layout-content>
      <a-layout-footer style="text-align: center">
        Father ©2021 Created by 鱼皮
      </a-layout-footer>
    </a-layout>
    <create-emoji-modal :visible="showCreateModal" :onClose="doCloseCreateModal"/>
  </div>
</template>

<script>
import {LOGO} from '../constant/index'
import {CloudUploadOutlined} from '@ant-design/icons-vue';
import CreateEmojiModal from "@/components/CreateEmojiModal";

export default {
  name: 'BaseLayout',
  components: {
    CloudUploadOutlined,
    CreateEmojiModal,
  },
  data() {
    return {
      logo: LOGO,
      showCreateModal: false,
    }
  },
  methods: {
    doShowCreateModal() {
      this.showCreateModal = true;
    },
    doCloseCreateModal() {
      this.showCreateModal = false;
    },
  }
}
</script>

<style scoped>
.logo {
  display: flex;
  align-items: center;
  height: 64px;
}

.logo > img {
  margin-right: 16px;
  height: 80%;
}

.title {
  font-size: 18px;
  color: #fff;
  margin-right: 16px;
  letter-spacing: 4px;
}
</style>