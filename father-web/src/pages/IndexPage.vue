<template>
  <div id="indexPage">
    <div class="margin-16">
      <a-input-search
          v-model:value="searchText"
          size="large"
          placeholder="搜索表情"
          enter-button
          @search="doSearchEmojis"
      />
    </div>
    <a-row>
      <a-col>
        <a-tabs type="card" v-model:active-key="activeTag" @tabClick="handleTabClick">
          <a-tab-pane v-for="tag in tagList" :key="tag.name" :tab="tag.name"/>
        </a-tabs>
      </a-col>
    </a-row>
    <a-list :grid="{ gutter: 16 }" :data-source="pictureList">
      <template #renderItem="{ item }">
        <a-list-item>
          <a-skeleton active :loading="loading" :avatar="{shape: 'square', size: 200}">
            <img :title="item.name" :alt="item.name" :src="item.url" class="picture"
                 @click="doShowEditModal(item)"/>
          </a-skeleton>
        </a-list-item>
      </template>
    </a-list>
    <a-pagination
        v-model:current="pageNum"
        v-model:page-size="pageSize"
        :total="total"
        style="text-align: center"
        @change="doPageChange"
    />
    <picture-modal :picture="currentPic" :visible="showPictureModal" :onClose="doClosePictureModal"/>
    <material-picker v-model="colors"/>
    <a-back-top/>
  </div>
</template>

<script>
import {searchEmojis} from '@/services/emoji'
import PictureModal from "@/components/PictureModal";
import {mapState} from 'vuex'
import EmojiReviewStatusEnum from "@/constant/EmojiReviewStatusEnum";

export default {
  name: 'HelloWorld',
  props: {},
  components: {
    PictureModal,
  },
  data() {
    return {
      pictureList: [],
      activeTag: '',
      currentPic: {},
      searchText: '',
      showPictureModal: false,
      loading: true,
      tagsLoading: true,
      pageNum: 1,
      pageSize: 20,
      total: 0,
      colors: {},
    }
  },
  computed: mapState({
    tagList: state => state.tag.tags
  }),
  created() {
    this.doSearchEmojis();
    this.$store.dispatch('tag/getAllTags');
  },
  watch: {
    activeTag() {
      this.doSearchEmojis();
    }
  },
  methods: {
    doPageChange() {
      this.doSearchEmojis();
    },
    async doSearchEmojis() {
      this.loading = true;
      const data = await searchEmojis({
        name: this.searchText,
        tag: this.activeTag,
        reviewStatus: EmojiReviewStatusEnum.REVIEW_PASS,
        pageSize: this.pageSize,
        pageNum: this.pageNum,
      });
      if (data) {
        this.pictureList = data.records;
        this.total = data.total;
      } else {
        this.$message.error('获取表情失败，请重试');
      }
      this.loading = false;
    },
    handleTabClick(tabKey) {
      if (tabKey === this.activeTag) {
        this.activeTag = '';
      }
    },
    doShowEditModal(picture) {
      this.showPictureModal = true;
      this.currentPic = picture;
    },
    doClosePictureModal() {
      this.showPictureModal = false;
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.picture {
  width: 100%;
  height: 200px;
}
</style>
