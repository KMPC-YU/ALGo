<template>
  <Headers></Headers>
  <div class="wrapper">
    <router-view></router-view>
    <Footer></Footer>
  </div>
</template>

<script>
import Headers from "@compo/Headers.vue";
import Footer from "@compo/Footer.vue";

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