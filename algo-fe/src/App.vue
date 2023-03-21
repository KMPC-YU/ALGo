<template>
  <Headers></Headers>
  <div>
    <router-view></router-view>
  </div>
</template>

<script>
import Headers from "@compo/Headers.vue";

export default {
  components: {
    Headers,
  },
}
</script>

<style scoped>

</style>