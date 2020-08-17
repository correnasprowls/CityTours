import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
import axios from 'axios'
import vuetify from './plugins/vuetify';
import VueBingMaps from 'vue-bing-maps'
import bingmaps from 'bingmaps'
Vue.config.productionTip = false

axios.defaults.baseURL = process.env.VUE_APP_REMOTE_API;

new Vue({
  router,
  store,
  vuetify,
  bingmaps,
  VueBingMaps,
  render: h => h(App)
}).$mount('#app')
