<template>
    <div id="app">
        <div class="page">
          <h1>请选择出发时间和目的地</h1>
            <login></login>
            <!-- <comHeader></comHeader> -->
            <!-- <navigator></navigator> -->
            <user-sider></user-sider>
            <!-- 子路由 -->
            <router-view></router-view>
        </div>
        <div class="footer">
            <span><a href="tel:059296363">联系客服</a></span>|
            <span><a href="https://www.yxzc01.com/api/base/appDownload?from=groupmessage&isappinstalled=0">下载APP</a></span>|
            <span><router-link to="/about">关于我们</router-link></span>
        </div>
    </div>
</template>
<script>
import { mapState, mapActions } from 'vuex'
const login = () => import('@/components/login/login.vue')
const comHeader = () => import('@/components/navigator/header.vue')
const navigator = () => import('@/components/navigator/nav_slide.vue')
const userSider = () => import('@/components/userSidebar/sideBar.vue')
// const suggest = () => import('@/components/suggest/suggest.vue')
export default{
  name: 'page',
  mounted () {
    this.$store.dispatch('init_data')
    // 默认进入子路由地址
    this.$router.push({path: '/pickup'})
    this.$store.dispatch('post_validateOpenId').then((data) => {
      if (data.errCode) {
        this.$createDialog({
          type: 'confirm',
          title: '账号被封',
          content: '您的账号已被封，原因是：恶意刷单。如有疑问请联系客服。',
          confirmBtn: {
            text: '我知道了',
            active: true,
            disabled: false,
            href: 'javascript:;'
          },
          cancelBtn: {
            text: '联系客服',
            active: false,
            disabled: false,
            href: 'javascript:;'
          },
          onConfirm: () => {},
          onCancel: () => {}
        }).show()
      }
    }).catch((err) => {
      console.log(err)
    })
    // 延迟执行高德定位
    setTimeout(() => {
      this.$store.dispatch('Geolocation')
    }, 1000)
  },
  computed: {
    ...mapState([
      'loginMask',
      'isMask',
      'showUserBar'])
  },
  methods: {
    ...mapActions({sendOrder: 'sendOrderApi'})
  },
  components: {
    login,
    comHeader,
    navigator,
    userSider
    // suggest
  },
  watch: {
    $route (to, from) {
      // 监听路由变化，对location组件重置
/*      if ((to.path === '/pickup' && from.path === '/dropoff') || (to.path === '/dropoff' && from.path === '/pickup')) {
        this.$store.commit('RESET_ALL_LOCATION')
        this.$store.commit('SET_COUPON', {
          id: '',
          value: 0,
          money: 0,
          discount: 0,
          type: 0,
          title: '',
          des: '',
          date: 0})
      } */
    }
  }
}
</script>
<style lang='less'>
@import '../../assets/css/animate.css';
#app {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  .page {
    flex: 1 0 auto;
    width: 100%;
    padding: 10px;
    box-sizing: border-box;
    &:after {
      content: '';
      position: relative;
      height: 0;
      width: 0;
      clear: both;
    }
    h1{
      font-size: 16px;
      color: #6b7886;
      text-align: center;
      line-height: 2;
    }
  }
}

.footer {
  flex: 0 0 auto;
  line-height: 40px;
  font-size: 12px;
  color: #7e8c8d;
  text-align: center;
  span {
    padding: 0 20px;
  }
}
.page {
  .content {
    position: relative;
    width: 100%;
    box-sizing: border-box;
  }
}
.flex-content{
  display: flex;
  .flex-item{
    flex-grow:1;
  }
  .gap:not(:first-child) {
    position: relative;
      &:before{
        content: '';
        position: absolute;
        width: 1px;
        height: 25px;
        background: #e5e7ea;
        top: 25%;
        left: 0;
      }
    }
}
</style>
