<template>
  <button
      class="btn btn-light"
      :class="{ 'active': isActive ? isActive() : null }"
      @click="action"
      :title="title"
  >
    <i :class="`bi bi-${icon}`"></i>
  </button>
</template>

<script>
export default {
  props: {
    icon: {
      type: String,
      required: true,
    },
    title: {
      type: String,
      required: true,
    },
    action: {
      type: Function,
      required: true,
    },
    isActive: {
      type: Function,
      default: null,
    },
  },
}
</script>

<style scoped>
.btn-light {
  --bs-btn-bg: none;
  --bs-btn-border-color: none;
}
</style>