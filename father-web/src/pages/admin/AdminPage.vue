<template>
  <a-table :columns="columns" :data-source="data" :scroll="{ x: 'auto' }">
    <template #picture="{text}">
      <a-image
          :width="150"
          :src="text"
      />
    </template>
    <template #action="{ record }">
      <span>
        <a-button type="link" style="padding-left: 0" @click="doReviewPass(record.id)">通过</a-button>
        <a-divider type="vertical"/>
        <a-button type="link" @click="doReviewReject(record.id)">拒绝</a-button>
        <a-divider type="vertical"/>
        <a-button type="link" danger @click="doDelete(record.id)">删除</a-button>
      </span>
    </template>
  </a-table>
</template>
<script>
import {defineComponent, onMounted, ref} from 'vue';
import {message} from 'ant-design-vue';
import {deleteEmoji, reviewEmoji, searchEmojis} from "@/services/emoji";
import EmojiReviewStatusEnum from "@/constant/EmojiReviewStatusEnum";

const columns = [
  {
    title: 'id',
    dataIndex: 'id',
    key: 'id',
    width: 100,
  },
  {
    title: '名称',
    dataIndex: 'name',
    key: 'name',
    width: 150,
  },
  {
    title: '图片',
    dataIndex: 'url',
    key: 'url',
    width: 150,
    slots: {customRender: 'picture'},
  },
  {
    title: '标签',
    key: 'tags',
    dataIndex: 'tags',
  },
  {
    title: '上传者',
    key: 'userId',
    dataIndex: 'userId',
    width: 150,
  },
  {
    title: '状态',
    key: 'reviewStatus',
    dataIndex: 'reviewStatus',
    width: 100,
  },
  {
    title: '操作',
    key: 'action',
    fixed: 'right',
    width: 250,
    slots: {customRender: 'action'},
  },
];

export default defineComponent({
  setup() {
    const data = ref([]);
    const total = ref(0);

    onMounted(async () => {
      const res = await searchEmojis();
      data.value = res.records;
      total.value = res.total;
    })

    /**
     * 审核通过
     * @param id
     */
    const doReviewPass = async (id) => {
      const res = await reviewEmoji(id, EmojiReviewStatusEnum.REVIEW_PASS);
      if (res) {
        message.success('操作成功');
      } else {
        message.error('操作失败');
      }
    }

    /**
     * 审核拒绝
     * @param id
     */
    const doReviewReject = async (id) => {
      const res = await reviewEmoji(id, EmojiReviewStatusEnum.REVIEW_REJECT);
      if (res) {
        message.success('操作成功');
      } else {
        message.error('操作失败');
      }
    }

    /**
     * 删除
     * @param id
     * @returns {Promise<void>}
     */
    const doDelete = async (id) => {
      const res = await deleteEmoji(id);
      if (res) {
        message.success('操作成功');
      } else {
        message.error('操作失败');
      }
    }

    return {
      data,
      total,
      columns,
      doReviewPass,
      doReviewReject,
      doDelete,
    };
  },
  components: {},
});
</script>