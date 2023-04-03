<template>
  <div>
    <terms-and-condition v-if="signupStep === 1" @step2="showStep2"></terms-and-condition>
    <new-user-info v-else-if="signupStep === 2"></new-user-info>
  </div>
</template>

<script>
import TermsAndCondition from "./components/TermsAndCondition.vue";
import NewUserInfo from "./components/NewUserInfo.vue";
import { ref } from "vue";
export default {
  components: {
    TermsAndCondition,
    NewUserInfo,
  },
  setup() {
    const signupStep = ref(1)

    const showStep2 = (data) => {
      if (data) {
        signupStep.value = 2
      }
    }

    const showStep3 = () => {
      signupStep.value = 3
    }

    return {
      signupStep, showStep2, showStep3,
    }
  }
}
</script>

<style scoped>

</style>