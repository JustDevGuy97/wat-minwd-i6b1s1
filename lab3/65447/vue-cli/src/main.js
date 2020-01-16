import Vue from 'vue'
import App from './App.vue'
import VueResource from 'vue-resource'
import 'bootstrap/dist/css/bootstrap.css'
import JQuery from 'jquery'
// import 'sigma'

Vue.use(VueResource);


new Vue({
  el: '#app',
  render: h => h(App)
})
