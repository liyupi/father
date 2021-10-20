<template>
  <a-modal :visible="visible" :title="`表情下载 - ${picture?.name}`" :style="{minWidth: '320px'}" width="90vw"
           :mask-closable="false"
           destroyOnClose
           @cancel="handleClose">
    <div>
      <a-row :gutter="24" type="flex">
        <a-col class="margin-16" flex="400px">
          <a-typography-title :level="4">预览</a-typography-title>
          <div id="previewBox" ref="previewBox">
            <div class="preview" ref="preview"/>
            <div :style="textStyle">{{ textConfig.inputText }}</div>
          </div>
        </a-col>
        <a-col class="margin-16" :md="12" :lg="8">
          <a-typography-title :level="4">图片编辑</a-typography-title>
          <div class="content">
            <section class="cropper-area">
              <div class="img-cropper">
                <vue-cropper
                    ref="cropper"
                    containerStyle="width:100%"
                    :key="picture?.url"
                    :src="picture?.url"
                    :alt="picture?.name"
                    :auto-crop-area="1"
                    :min-container-height="200"
                    preview=".preview"
                    @crop="onCrop"
                />
              </div>
              <div class="actions">
                <a
                    href="#"
                    role="button"
                    @click.prevent="zoom(0.2)"
                >
                  放大
                </a>
                <a
                    href="#"
                    role="button"
                    @click.prevent="zoom(-0.2)"
                >
                  缩小
                </a>
                <a
                    href="#"
                    role="button"
                    @click.prevent="move(-10, 0)"
                >
                  左移
                </a>
                <a
                    href="#"
                    role="button"
                    @click.prevent="move(10, 0)"
                >
                  右移
                </a>
                <a
                    href="#"
                    role="button"
                    @click.prevent="move(0, -10)"
                >
                  上移
                </a>
                <a
                    href="#"
                    role="button"
                    @click.prevent="move(0, 10)"
                >
                  下移
                </a>
                <a
                    href="#"
                    role="button"
                    @click.prevent="rotate(90)"
                >
                  右旋
                </a>
                <a
                    href="#"
                    role="button"
                    @click.prevent="rotate(-90)"
                >
                  左旋
                </a>
                <a
                    ref="flipX"
                    href="#"
                    role="button"
                    @click.prevent="flipX"
                >
                  水平翻转
                </a>
                <a
                    ref="flipY"
                    href="#"
                    role="button"
                    @click.prevent="flipY"
                >
                  垂直翻转
                </a>
                <a
                    href="#"
                    role="button"
                    @click.prevent="reset"
                >
                  重置
                </a>
              </div>
            </section>
          </div>
        </a-col>
        <a-col flex="auto">
          <a-typography-title :level="4">表情配字</a-typography-title>
          <a-form ref="formRef" :model="textConfig" layout="horizontal" label-align="left" :label-col="{ style: { width: '80px' } }"
                  :colon="false">
            <a-form-item label="文字" name="inputText">
              <a-input v-model:value="textConfig.inputText"/>
            </a-form-item>
            <a-form-item label="字号" name="fontSize">
              <a-input-number v-model:value="textConfig.fontSize" :min="12"/>
            </a-form-item>
            <a-form-item label="粗细" name="fontWeight">
              <a-radio-group v-model:value="textConfig.fontWeight" button-style="solid">
                <a-radio-button value="100">较细</a-radio-button>
                <a-radio-button value="500">正常</a-radio-button>
                <a-radio-button value="900">较粗</a-radio-button>
              </a-radio-group>
            </a-form-item>
            <a-form-item label="位置" name="position">
              <a-radio-group v-model:value="textConfig.position" button-style="solid">
                <a-radio-button value="inner">图内</a-radio-button>
                <a-radio-button value="outer">图外</a-radio-button>
              </a-radio-group>
            </a-form-item>
            <a-form-item label="对齐" name="textAlign">
              <a-radio-group v-model:value="textConfig.textAlign" button-style="solid">
                <a-radio-button value="left">左</a-radio-button>
                <a-radio-button value="center">中</a-radio-button>
                <a-radio-button value="right">右</a-radio-button>
              </a-radio-group>
            </a-form-item>
            <a-form-item label="字间距" name="letterSpacing">
              <a-input-number v-model:value="textConfig.letterSpacing" :min="0"/>
            </a-form-item>
            <a-form-item label="边距" name="padding">
              <a-input-number v-model:value="textConfig.padding" :min="0"/>
            </a-form-item>
            <a-form-item label="背景色" name="background">
              <popu-color-picker v-model:color="textConfig.background" mode="fk"/>
            </a-form-item>
            <a-form-item label="字体颜色" name="color">
              <popu-color-picker v-model:color="textConfig.color" mode="fk"/>
            </a-form-item>
          </a-form>
        </a-col>
      </a-row>
    </div>
    <template #footer>
      <a-row :gutter="16">
        <a-col :span="12">
          <a-button key="submit" size="large" block @click="doDownload">
            <template #icon>
              <DownloadOutlined/>
            </template>
            下载原图
          </a-button>
        </a-col>
        <a-col :span="12">
          <a-button key="submit" size="large" type="primary" block @click="doGenerate">
            <template #icon>
              <ExperimentOutlined/>
            </template>
            生成新图
          </a-button>
        </a-col>
      </a-row>
    </template>
  </a-modal>
</template>

<script>
import VueCropper from 'vue-cropperjs';
import {DownloadOutlined, ExperimentOutlined} from '@ant-design/icons-vue';
import html2canvas from 'html2canvas';
import {saveAs} from 'file-saver';
import 'cropperjs/dist/cropper.css';

export default {
  components: {
    VueCropper,
    DownloadOutlined,
    ExperimentOutlined,
  },
  props: {
    picture: Object,
    visible: Boolean,
    onClose: Function,
  },
  data() {
    return {
      cropImg: '',
      data: null,
      finalImg: '',
      textConfig: {
        inputText: '',
        fontSize: 32,
        position: 'outer',
        fontWeight: '500',
        textAlign: 'center',
        padding: 16,
        color: {
          hex8: 'black',
        },
        background: {
          hex8: '#F7FBF6',
        },
        letterSpacing: 5,
      },
    };
  },
  computed: {
    // 配字样式
    textStyle() {
      const styleObj = {...this.textConfig};
      styleObj.fontSize = `${styleObj.fontSize}px`;
      styleObj.padding = `${styleObj.padding}px`;
      styleObj.letterSpacing = `${styleObj.letterSpacing}px`;
      if (styleObj.position === 'inner') {
        styleObj.position = 'absolute';
        styleObj.bottom = 0;
        styleObj.width = '100%'
      } else {
        styleObj.position = '';
      }
      styleObj.background = styleObj.background.hex8;
      styleObj.color = styleObj.color.hex8;
      return styleObj;
    },
    // 完整图片名称
    pictureName() {
      if (this.picture?.url && this.picture?.name) {
        const {name} = this.picture;
        return this.picture.name + name.substring(name.lastIndexOf('.'));
      }
      return '';
    }
  },
  methods: {
    handleClose() {
      this.$refs.formRef.resetFields();
      this.onClose();
    },
    // 下载原图
    doDownload() {
      if (this.picture?.url) {
        saveAs(this.picture.url, this.pictureName);
      }
    },
    // 生成新图
    doGenerate() {
      html2canvas(document.getElementById('previewBox'), {
        useCORS: true,
      }).then((canvas) => {
        this.finalImg = canvas.toDataURL("image/png");
        saveAs(this.finalImg, this.pictureName);
      });
    },
    flipX() {
      const dom = this.$refs.flipX;
      let scale = dom.getAttribute('data-scale');
      scale = scale ? -scale : -1;
      this.$refs.cropper.scaleX(scale);
      dom.setAttribute('data-scale', scale);
    },
    flipY() {
      const dom = this.$refs.flipY;
      let scale = dom.getAttribute('data-scale');
      scale = scale ? -scale : -1;
      this.$refs.cropper.scaleY(scale);
      dom.setAttribute('data-scale', scale);
    },
    move(offsetX, offsetY) {
      this.$refs.cropper.move(offsetX, offsetY);
    },
    reset() {
      this.$refs.cropper.reset();
    },
    rotate(deg) {
      this.$refs.cropper.rotate(deg);
    },
    zoom(percent) {
      this.$refs.cropper.relativeZoom(percent);
    },
    onCrop() {
      this.$refs.previewBox.style.width = this.$refs.preview.style.width;
    }
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
body {
  font-family: Arial, Helvetica, sans-serif;
  width: 1024px;
  margin: 0 auto;
}

input[type="file"] {
  display: none;
}

.header h2 {
  margin: 0;
}

.header a {
  text-decoration: none;
  color: black;
}

.content {
  display: flex;
  justify-content: space-between;
}

.cropper-area {
  width: 100%;
}

.actions {
  margin-top: 1rem;
}

.actions a {
  display: inline-block;
  padding: 5px 15px;
  background: #0062CC;
  color: white;
  text-decoration: none;
  border-radius: 3px;
  margin-right: 1rem;
  margin-bottom: 1rem;
}

#previewBox {
  text-align: center;
  background: #efefef;
  border: 1px solid #eee;
  position: relative;
  box-sizing: content-box;
}

.preview {
  width: 100%;
  height: 400px;
  overflow: hidden;
  margin: 0 auto;
}

.cropped-image img {
  max-width: 100%;
}
</style>