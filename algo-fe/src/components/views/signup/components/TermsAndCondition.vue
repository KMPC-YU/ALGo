<template>
  <div class="container">
    <div class="card d-flex justify-content-center align-items-center">
      <div class="logo text-center">
        <img src="/ALGo_Logo.ico" alt="" width="50">
        <span class="fs-1 align-middle"> ALGo</span>
      </div>
      <div class="form-check mb-4 mt-4">
        <input class="form-check-input" type="checkbox" v-model="checkBoxAllChecked" @click="toggleCheckBoxAll" value="" id="checkBoxAll">
        <label class="form-check-label fw-bold text-decoration-underline" for="checkBoxAll">
          ALGo 이용약관, 개인정보 수집 및 이용에 모두 동의합니다.
        </label>
      </div>
      <div class="card-body">
        <div class="mb-4">
          <div class="fs-4 fw-bold">
            <i class="bi bi-check-lg me-1"></i>
            <label for="condition-terms1" class="form-label">이용약관</label>
          </div>
          <textarea class="form-control shadow-sm" id="condition-terms1" :value="conditionAndTerms1" cols="70" rows="10" readonly></textarea>
          <div class="col-12 d-flex justify-content-end mt-2">
            <div class="form-check">
              <input v-if="!checkValidate" class="form-check-input me-2" type="checkbox" v-model="checkBox1Checked" value="" id="termsCheckBox1">
              <input v-else class="form-check-input me-2" type="checkbox" v-model="checkBox1Checked"
                     :class="checkBox1Checked ? 'is-valid' : 'is-invalid'" value="" id="termsCheckBox1">
              <label class="form-check-label" for="termsCheckBox1">
                ALGo 이용약관에 동의합니다
              </label>
              <div class="invalid-feedback">
                ALGo 이용약관에 동의해주세요.
              </div>
            </div>
          </div>
        </div>
        <div class="mb-4">
          <div class="fs-4 fw-bold">
            <i class="bi bi-check-lg me-1"></i>
            <label for="condition-terms2" class="form-label">개인정보 수집 및 이용</label>
          </div>
          <textarea class="form-control shadow-sm" id="condition-terms2" :value="conditionAndTerms2" cols="70" rows="10" readonly></textarea>
          <div class="col-12 d-flex justify-content-end mt-2">
            <div class="form-check">
              <input v-if="!checkValidate" class="form-check-input me-2" v-model="checkBox2Checked" type="checkbox" value="" id="termsCheckBox2">
              <input v-else class="form-check-input me-2" v-model="checkBox2Checked" type="checkbox" value="" id="termsCheckBox2"
                     :class="checkBox2Checked ? 'is-valid' : 'is-invalid'">
              <label class="form-check-label" for="termsCheckBox2">
                개인정보 수집 및 이용에 동의합니다
              </label>
              <div class="invalid-feedback">
                개인정보 수집 및 이용에 동의해주세요.
              </div>
            </div>
          </div>
        </div>
        <div class="row mt-5" id="agreeBottom">
          <div class="col">
            <button class="btn btn-secondary btn-lg me-3 form-control">취소</button>
          </div>
          <div class="col">
            <button class="btn btn-primary btn-lg form-control" @click="nextStep()">다음</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted, watch } from "vue";
export default {
  emits: ['step2'],
  setup(props, { emit }) {
    onMounted(() => {
      getTermsAndCondition()
    })

    const checkBox1Checked = ref(false)
    const checkBox2Checked = ref(false)
    const checkValidate = ref(false)

    const conditionAndTerms1 = '이용약관 내용'
    const conditionAndTerms2 = '개인정보 수집 및 동의 내용'

    const checkBoxAllChecked = ref(false)

    const formValid = computed(() => checkBox1Checked.value && checkBox2Checked.value);

    watch([checkBox1Checked, checkBox2Checked], () => {
      checkBoxAllChecked.value = checkBox1Checked.value && checkBox2Checked.value;
    });

    const toggleCheckBoxAll = () => {
      checkBoxAllChecked.value = !checkBoxAllChecked.value
      checkBox1Checked.value = checkBoxAllChecked.value;
      checkBox2Checked.value = checkBoxAllChecked.value;
    };

    const getTermsAndCondition = () => {
      // Todo : 이용약관 정보 API 추가
    }
    const nextStep = () => {
      checkValidate.value = true
      if (formValid.value) {
        emit('step2', true)
      }
    }
    return {
      conditionAndTerms1, conditionAndTerms2,
      nextStep, checkBox1Checked, checkBox2Checked, checkValidate, checkBoxAllChecked, toggleCheckBoxAll,
    }
  }
}
</script>

<style scoped>
.logo {
  padding: 20px 0;
}
.card {
  border: none;
  background-color: #f1f1f2;
  margin: 0 auto;
}
.container {
  padding: 20px;
}
textarea {
  resize: none;
}
.form-check-input, .form-check-label {
  cursor: pointer;
}
.invalid-feedback {
  cursor: default;
}
</style>