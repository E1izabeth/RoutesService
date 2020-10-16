import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import { BootstrapVue, IconsPlugin, BootstrapVueIcons, FormInputPlugin } from 'bootstrap-vue';
import VueRouter from 'vue-router';

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");

Vue.config.productionTip = false;
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(VueRouter)