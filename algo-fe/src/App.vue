<template>
  <Headers></Headers>
  <div class="wrapper">
    <router-view></router-view>
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

<style scoped>
.wrapper {
  background-color: #f1f1f2;
}
</style>