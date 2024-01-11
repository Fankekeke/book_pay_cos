<template>
  <a-modal v-model="show" title="图书详情" @cancel="onClose" :width="1200">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="bookData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">图书信息</span></a-col>
        <a-col :span="8"><b>图书名称：</b>
          {{ bookData.studentName }}
        </a-col>
        <a-col :span="8"><b>图书学号：</b>
          {{ bookData.code }}
        </a-col>
        <a-col :span="8"><b>联系方式：</b>
          {{ bookData.phone }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>省份：</b>
          {{ bookData.province }}
        </a-col>
        <a-col :span="8"><b>城市：</b>
          {{ bookData.city }}
        </a-col>
        <a-col :span="8"><b>区：</b>
          {{ bookData.area }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>专业：</b>
          {{ bookData.address }}
        </a-col>
        <a-col :span="8"><b>注册时间：</b>
          {{ bookData.createDate }}
        </a-col>
        <a-col :span="8"><b>出生日期：</b>
          {{ bookData.birthday }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="24"><b>详细地址：</b>
          {{ bookData.address }}
        </a-col>
      </a-row>
      <br/>
      <a-col :span="24">
        <a-form-item label='图册' v-bind="formItemLayout">
          <a-upload
            name="avatar"
            action="http://127.0.0.1:9527/file/fileUpload/"
            list-type="picture-card"
            :file-list="fileList"
            @preview="handlePreview"
            @change="picHandleChange"
          >
            <div v-if="fileList.length < 8">
              <a-icon type="plus" />
              <div book="ant-upload-text">
                Upload
              </div>
            </div>
          </a-upload>
          <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
            <img alt="example" style="width: 100%" :src="previewImage" />
          </a-modal>
        </a-form-item>
      </a-col>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'bookView',
  props: {
    bookShow: {
      type: Boolean,
      default: false
    },
    bookData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.bookShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      durgList: []
    }
  },
  watch: {
    bookShow: function (value) {
      if (value) {
        this.imagesInit(this.bookData.images)
      }
    }
  },
  methods: {
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
