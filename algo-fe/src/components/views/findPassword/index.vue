<template>
  <FindPassword v-if="step === 1" @step2="showStep2"/>
  <ChangeNewPassword v-else :email="email"/>
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
    const email = ref('')

    const showStep2 = (data) => {
      email.value = data
      step.value = 2
    }

    return {
      step,
      email,
      showStep2,
    }
  }
}
</script>

<style scoped>

</style>