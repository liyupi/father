<template>
  <a-modal :visible="visible" title="表情上传" ok-text="提交" cancel-text="取消" @cancel="onClose" @ok="handleSubmit">
    <a-form :model="emoji" hide-required-mark :colon="false" label-align="left"
            :label-col="{ style: { width: '60px' } }">
      <a-form-item label="名称">
        <a-input v-model:value="emoji.name"/>
      </a-form-item>
      <a-form-item label="标签">
        <a-select
            v-model:value="emoji.tags"
            mode="multiple"
            style="width: 100%"
            placeholder="选择标签"
            :options="tagOptions"
        >
        </a-select>
      </a-form-item>
      <a-form-item label="图片">
        <a-upload
            v-model:file-list="fileList"
            name="avatar"
            list-type="picture-card"
            class="avatar-uploader"
            :show-upload-list="false"
            :before-upload="beforeUpload"
            :customRequest="doCustomUpload"
        >
          <img v-if="emoji.url" :src="emoji.url" alt="avatar" style="width: 100%"/>
          <div v-else>
            <loading-outlined v-if="loading"/>
            <plus-outlined v-else/>
          </div>
        </a-upload>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import {PlusOutlined, LoadingOutlined} from '@ant-design/icons-vue';
import {addEmoji, uploadEmoji} from "@/services/emoji";
import {mapState} from "vuex";

export default {
  name: "CreateEmojiModal",
  props: {
    visible: String,
    onClose: Function,
    onSuccess: Function,
  },
  components: {
    PlusOutlined,
    LoadingOutlined,
  },
  data() {
    return {
      emoji: {},
      fileList: [],
      loading: false,
    }
  },
  computed: mapState({
    tagOptions: state => state.tag.tags.map(tag => {
      return {
        value: tag.name
      }
    })
  }),
  methods: {
    async handleSubmit() {
      const {name, url} = this.emoji;
      if (!name) {
        this.$message.error('请输入图片名称');
        return;
      }
      if (!url) {
        this.$message.error('请上传图片');
        return;
      }
      const res = await addEmoji(this.emoji);
      if (res) {
        this.$message.success('提交成功，审核后可见');
        this.onClose();
        this.onSuccess && this.onSuccess();
      } else {
        this.$message.error('提交失败');
      }
    },
    beforeUpload(file) {
      const isFileTypeValid = ['image/jpeg', 'image/png', 'image/gif'].includes(file.type);
      if (!isFileTypeValid) {
        this.$message.error('只支持上传 jpg / png / gif 格式的图片');
        return false;
      }
      const isLt1M = file.size / 1024 / 1024 < 1;
      if (!isLt1M) {
        this.$message.error('图片大小不能超过 1M');
        return false;
      }
      return true;
    },
    async doCustomUpload(info) {
      const {file} = info;
      this.loading = true;
      const data = await uploadEmoji(file);
      if (data) {
        this.emoji.url = data;
      } else {
        this.$message.error('图片上传失败');
      }
      this.loading = false;
    },
  }
}
</script>

<style scoped>
</style>