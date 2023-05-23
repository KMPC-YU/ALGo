<template>
  <div>
    <Headers></Headers>
    <div>
      <router-view></router-view>
    </div>
    <Footer></Footer>
  </div>
</template>

<script>
import Headers from "./components/common/Headers.vue";
import Footer from "./components/common/Footer.vue";

export default {
  components: {
    Headers,
    Footer,
  },
}
</script>