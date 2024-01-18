<template>
  <a-row :gutter="20" style="width: 100%;margin-top: 20px">
    <a-col :span="24" style="margin-top: 15px;margin-bottom: 35px;">
      <a-input-search placeholder="搜索学生" style="width: 300px;margin: 0 auto" v-model="key" @search="selectDrugList" />
    </a-col>
    <a-col :span="24"></a-col>
    <a-col :span="6" v-for="(item, index) in studentList" :key="index" style="margin-bottom: 15px">
      <div style="width: 100%;margin-bottom: 15px;text-align: left;box-shadow: rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;">
        <a-card :bordered="false" @click="handleUserViewOpen(item)" hoverable>
          <a-carousel autoplay style="height: 150px;" v-if="item.images !== undefined && item.images !== ''">
            <div style="width: 100%;height: 150px" v-for="(item, index) in item.images.split(',')" :key="index">
              <img :src="'http://127.0.0.1:9527/imagesWeb/'+item" style="width: 100%;height: 250px">
            </div>
          </a-carousel>
          <a-card-meta :title="item.studentName" :description="item.code.slice(0, 18)+'...'" style="margin-top: 10px"></a-card-meta>
          <div style="font-size: 12px;font-family: SimHei;margin-top: 8px">
            <span>{{ item.birthday }}</span> |
            <span style="color: #f5222d; font-size: 13px;float: right">{{ item.major }}</span>
          </div>
        </a-card>
      </div>
    </a-col>
    <student-view
      @close="handleUserViewClose"
      :userShow="userView.visiable"
      :userData="userView.data">
    </student-view>
  </a-row>
</template>

<script>
import StudentView from '../../manage/student/StudentView'

export default {
  name: 'Cart',
  components: {StudentView},
  data () {
    return {
      drugList: [],
      userView: {
        visiable: false,
        data: null
      },
      drugView: {
        visiable: false,
        data: null
      },
      cartView: {
        visiable: false,
        data: []
      },
      key: '',
      classList: [],
      studentList: []
    }
  },
  mounted () {
    this.selectClassList()
  },
  methods: {
    selectClassList () {
      this.$get('/cos/class-info/list').then((r) => {
        this.classList = r.data.data
      })
    },
    selectStudentList (classId) {
      this.$get(`/cos/student-info/selectStudentByClass/${classId}`).then((r) => {
        this.studentList = r.data.data
      })
    },
    handleUserViewOpen (row) {
      this.userView.data = row
      this.userView.visiable = true
    },
    handleUserViewClose () {
      this.userView.visiable = false
    },
    cartOpen () {
      this.cartView.visiable = true
    },
    handleDrugViewClose () {
      this.drugView.visiable = false
    },
    handleCartViewClose () {
      this.cartView.visiable = false
    },
    handleCartViewSuccess () {
      this.cartView.visiable = false
      this.cartView.data = []
      this.$message.success('添加订单成功')
    },
    handleDrugViewSuccess (drugData) {
      let check = false
      this.cartView.data.forEach(e => {
        if (e.id === drugData.id) {
          e.total = drugData.total
          check = true
        }
      })
      if (!check) {
        this.cartView.data.push(drugData)
      }
      this.drugView.visiable = false
    },
    drugDetail (row) {
      this.drugView.visiable = true
      this.drugView.data = row
    },
    selectDrugList () {
      this.$get(`/cos/commodity-info/commodity/list`, {key: this.key}).then((r) => {
        this.drugList = r.data.data
      })
    }
  }
}
</script>

<style scoped>
>>> .ant-card-meta-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-meta-description {
  font-size: 12px;
  font-family: SimHei;
}
>>> .ant-divider-with-text-left {
  margin: 0;
}

>>> .ant-card-head-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-extra {
  font-size: 13px;
  font-family: SimHei;
}
.ant-carousel >>> .slick-slide {
  text-align: center;
  height: 150px;
  line-height: 150px;
  overflow: hidden;
}
</style>
