<template>
  <div>
    <Headers v-if="currentRoute !== '/admin'"></Headers>
    <div>
      <router-view></router-view>
    </div>
    <Footer></Footer>
  </div>
</template>

<script>
import Headers from "./components/common/Headers.vue";
import Footer from "./components/common/Footer.vue";
import router from "@/router/router.js";
import { computed } from 'vue'

export default {
  components: {
    Headers,
    Footer,
  },
  setup() {
    const currentRoute = computed(() => {
      return router.currentRoute.value.path
    })

    return {
      currentRoute
    }
  }
}
</script>