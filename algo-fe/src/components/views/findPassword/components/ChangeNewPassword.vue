<template>
  <div class="container">
    <div class="card d-flex justify-content-center align-items-center">
      <div class="logo text-center">
        <img src="/ALGo_Logo.ico" alt="" width="50">
        <span class="fs-1 align-middle"> ALGo</span>
      </div>
      <div class="card-body">
        <div class="mb-5">
          <h4 class="card-title mb-3">로그인에 사용할 새로운 비밀번호를 입력해주세요.</h4>
          <div class="progress" style="max-width: 100px; height: 10px">
            <div class="progress-bar progress-bar-striped progress-bar-animated" style="width: 100%"></div>
          </div>
        </div>
        <div class="mb-3">
          <label for="password" class="form-label fw-bold">새로운 비밀번호</label>
          <input type="password" v-model="password" id="password" maxlength="20"
                  @blur="passwordValidCheck" @keyup.enter="passwordValidCheck"
                 class="form-control form-control-lg"
                 :class="{'': passwordValid === 1, 'is-valid': passwordValid === 2, 'is-invalid': passwordValid === 3}">
          <div class="invalid-feedback"> {{ passwordMessage }} </div>
        </div>
        <div class="mb-3">
          <label for="password2" class="form-label fw-bold">비밀번호 재확인</label>
          <input type="password" v-model="password2" id="password2" maxlength="20"
                 @blur="password2ValidCheck" @keyup.enter="password2ValidCheck"
                 class="form-control form-control-lg"
                 :class="{'': password2Valid === 1, 'is-valid': password2Valid === 2, 'is-invalid': password2Valid === 3}">
          <div class="invalid-feedback"> {{ password2Message }} </div>
        </div>
        <button class="btn btn-lg btn-primary form-control mt-4" @click="changePassword">완료</button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import * as FindpwAPI from '@/services/findpw.js'
import router from '@/router/router'

export default {
  props: {
    userData: {
      type: Object,
      required: true,
    }
  },
  setup(props) {
    const password = ref('')
    const passwordValid = ref(1)
    const passwordMessage = ref('')

    const password2 = ref('')
    const password2Valid = ref(1)
    const password2Message = ref('')

    const passwordValidCheck = () => {
      if (password.value.length === 0) {
        passwordValid.value = 3
        passwordMessage.value = '새로운 비밀번호를 입력하세요.'
      } else if (!/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/.test(password.value)) {
        passwordValid.value = 3
        passwordMessage.value = '비밀번호는 최소 8자 이상, 대소문자와 숫자, 특수문자를 모두 포함해야 합니다.'
      } else {
        passwordValid.value = 2
      }
    }

    const password2ValidCheck = () => {
      if (password2.value.length === 0) {
        password2Valid.value = 3
        password2Message.value = '비밀번호 재확인을 입력하세요.'
      } else if (password.value !== password2.value) {
        password2Valid.value = 3
        password2Message.value = '비밀번호가 일치하지 않습니다.'
      } else {
        password2Valid.value = 2
      }
    }

    const changePassword = () => {
      passwordValidCheck()
      password2ValidCheck()

      if (passwordValid.value === 2 && password2Valid.value === 2) {
        FindpwAPI.changePassword(props.userData.username, props.userData.email, password.value, password2.value)
          .then(() => {
          alert('비밀번호 변경이 완료되었습니다. 로그인화면으로 이동합니다.')
          router.push({name: 'Login'})
          })
          .catch((err) => {
            console.log(err.response.data.message)
          })
      }
    }

    return {
      password,
      password2,
      passwordValid,
      password2Valid,
      passwordMessage,
      password2Message,
      passwordValidCheck,
      password2ValidCheck,
      changePassword,
    }
  }
}
</script>

<style scoped>
.container {
  padding: 20px;
}
.card {
  border: none;
  background-color: #f1f1f2;
  margin: 0 auto;
}
.card-body {
  max-width: 510px;
}
.logo {
  padding: 20px 0;
}
</style>