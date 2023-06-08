<template>
  <div class="container">
    <div class="card d-flex justify-content-center align-items-center">
      <div class="logo text-center">
        <img src="/ALGo_Logo.ico" alt="" width="50">
        <span class="fs-1 align-middle"> ALGo</span>
      </div>
      <div class="card-body">
        <div class="mb-5">
          <h4 class="card-title mb-3">비밀번호를 찾고자하는 아이디 정보를 입력하세요.</h4>
          <div class="progress" style="max-width: 100px; height: 10px">
            <div class="progress-bar progress-bar-striped progress-bar-animated" style="width: 50%"></div>
          </div>
        </div>
        <div class="mb-3">
          <label for="username" class="form-label fw-bold">아이디</label>
          <input type="text" v-model="username" id="username" maxlength="20" :disabled="sendMail"
                 @blur="usernameValidCheck" class="form-control form-control-lg"
                 :class="{'': usernameValid === 1, 'is-valid': usernameValid === 2, 'is-invalid': usernameValid === 3}">
          <div class="invalid-feedback"> {{ usernameMessage }} </div>
        </div>
        <div class="mb-3">
          <label for="email" class="form-label fw-bold">이메일</label>
          <div class="row">
            <div class="col-lg-8">
              <input type="text" v-model="email" id="email" maxlength="40" :disabled="sendMail"
                     @blur="emailValidCheck" class="form-control form-control-lg"
                     :class="{'': emailValid === 1, 'is-valid': emailValid === 2, 'is-invalid': emailValid === 3}">
              <div class="invalid-feedback"> {{ emailMessage }} </div>
            </div>
            <div class="col-lg-4 ms-auto mt-lg-0 mt-2">
              <button class="btn btn-lg btn-primary" @click="sendVerifyEmailCode" :disabled="sendMail">인증번호 받기</button>
            </div>
          </div>
        </div>
        <div v-show="inputCodeEnable" class="row mt-2">
          <div class="col-9">
            <input type="tel" v-model="authCode" placeholder="인증번호 입력" maxlength="6"
                   @keyup.enter="checkAuthCode" class="form-control form-control-lg"
                   :class="{'': authCodeValid === 1, 'is-valid': authCodeValid === 2, 'is-invalid': authCodeValid === 3}">
            <div class="invalid-feedback"> {{ authCodeMessage }} </div>
          </div>
          <div class="col-auto text-danger">
            <p style="text-align:center; vertical-align:middle; top: 20%">남은시간: {{ leftTime }}초</p>
          </div>
          <div v-show="leftTime <= 290" class="mt-1">
            <a type="button" class="code-text" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
              인증번호를 받지 못하셨나요?
            </a>
          </div>
        </div>
        <button class="btn btn-lg btn-primary form-control mt-4" @click="checkAuthCode">다음</button>
      </div>
    </div>
  </div>
  <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">인증번호를 받지 못하셨나요?</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          이메일이 정확한지 확인해 주세요.<br>
          서버 환경에 따라 인증번호 발송이 늦어질 수 있습니다.<br>
          계속해서 인증 이메일이 오지 않으면, 다시 인증번호를 요청해 주세요.<br>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" @click="sendVerifyEmailCode(true)" data-bs-dismiss="modal">인증메일 재발송</button>
          <button type="button" class="btn btn-secondary" @click="changeEmail" data-bs-dismiss="modal">다른 이메일로 인증</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import * as FindpwAPI from '@/services/findpw.js'
import Swal from 'sweetalert2'

export default {
  emits: ['step2'],
  setup(props, { emit }) {
    const isComplete = ref(false)

    const username = ref('')
    const usernameValid = ref(1)
    const usernameMessage = ref('')

    const email = ref('')
    const emailValid = ref(1)
    const emailMessage = ref('')

    const sendMail = ref(false)  // 인증메일 발송여부
    const inputCodeEnable = ref(false)  // 인증메일 입력칸 활성화 여부

    const authCode = ref('')  // 인증 코드
    const authCodeValid = ref(1)
    const authCodeMessage = ref('')

    const usernameValidCheck = () => {
      if (username.value.length === 0) {
        usernameValid.value = 3
        usernameMessage.value = '아이디를 입력하세요.'
      } else if (!/^[a-zA-Z0-9_-]{5,20}$/.test(username.value)) {
        usernameValid.value = 3
        usernameMessage.value = '5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.'
      } else {
        FindpwAPI.usernameDuplicateCheck(username.value)
          .then(() => {
            usernameValid.value = 3
            usernameMessage.value = '해당 아이디로 가입된 정보가 없습니다.'
            })
          .catch(() => {
            usernameValid.value = 2
          })
      }
    }

    const emailValidCheck = () => {
      if (email.value.length === 0) {
        emailValid.value = 3
        emailMessage.value = '이메일을 입력하세요'
      } else if (!/^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\.[A-Za-z0-9\\-]+/.test(email.value)) {
        emailValid.value = 3
        emailMessage.value = '이메일 형식을 확인해주세요.'
      } else {
        FindpwAPI.emailDuplicateCheck(email.value)
          .then(() => {
            emailValid.value = 3
            emailMessage.value = '해당 이메일로 가입된 정보가 없습니다.'
          })
          .catch(() => {
            emailValid.value = 2
          })
      }
    }

    const sendVerifyEmailCode = (resend = false) => {
      usernameValidCheck()
      emailValidCheck()

      if (usernameValid.value === 2&& emailValid.value === 2) {
        if (resend === false) {
          Swal.fire({icon: 'success', title: '인증코드 발송 완료!', showConfirmButton: false, timer: 1500})
        } else {
          clearTimeout(Timer)
          leftTime.value = 300
          Swal.fire({icon: "success", title: '재발송 완료!', showConfirmButton: false, timer: 1500})
        }

        startTimer()
        sendMail.value = true
        inputCodeEnable.value = true

        FindpwAPI.sendVerifyEmailCode(email.value, username.value)
          .then(() => {
            authCodeMessage.value = '이메일로 발송된 6자리의 인증번호를 입력해 주세요.'
          })
          .catch((err) => {
            Swal.fire({icon: 'error', title: '인증코드 발송 실패...', showConfirmButton: false, timer: 1500})
            sendMail.value = false
            console.error(err)
          })
      }
    }

    let Timer;
    const leftTime = ref(300)
    const startTimer = () => {
      if(leftTime.value > 0) {
        Timer = setTimeout(() => {
          leftTime.value--
          startTimer()
        }, 1000)
      }
    }

    const checkAuthCode = () => {
      if (usernameValid.value === 2&& emailValid.value === 2 && inputCodeEnable.value && sendMail.value) {
        if (leftTime.value > 0) {

          FindpwAPI.verifyEmailCode(email.value, authCode.value)
            .then(() => {
              authCodeValid.value = 2
              isComplete.value = true
              emit('step2', {
                username: username.value,
                email: email.value,
              })
            })
            .catch(() => {
              authCodeValid.value = 3
              authCodeMessage.value = '인증번호가 일치하지 않습니다.'
            })
        } else {
          Swal.fire({icon: 'error', title: '인증시간 만료', text: '다시 인증해주세요.', showConfirmButton: false, timer: 1500})
        }
      }
    }

    const changeEmail = () => {
      email.value = ''
      sendMail.value = false
      inputCodeEnable.value = false
      authCode.value = ''
      clearTimeout(Timer)
      leftTime.value = 300
    }

    return {
      username,
      usernameValid,
      usernameMessage,
      usernameValidCheck,
      email,
      emailValid,
      emailMessage,
      emailValidCheck,
      authCode,
      authCodeValid,
      authCodeMessage,
      checkAuthCode,
      leftTime,
      sendMail,
      inputCodeEnable,
      sendVerifyEmailCode,
      changeEmail,
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
.code-text {
  text-decoration: none;
  color:black;
}
.code-text:hover {
  text-decoration: underline;
  color: black;
}
</style>