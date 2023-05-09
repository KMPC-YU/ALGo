<template>
  <FindPassword v-if="step === 1" @step2="showStep2"/>
  <ChangeNewPassword v-else :userData="userData"/>
</template>

<script>
import FindPassword from './components/FindPassword.vue'
import ChangeNewPassword from './components/ChangeNewPassword.vue'
import { ref } from 'vue'

export default {
  components: {
    FindPassword,
    ChangeNewPassword,
  },
  setup() {
    const step = ref(1)
    const userData = ref({})

    const showStep2 = (data) => {
      userData.value = data
      step.value = 2
    }

    return {
      step,
      userData,
      showStep2,
    }
  }
}
</script>

<style scoped>

</style>