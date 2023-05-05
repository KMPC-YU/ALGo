<template>
  <div class="container">
    <div class="card d-flex justify-content-center align-items-center">
      <div class="logo text-center">
        <img src="/ALGo_Logo.ico" alt="" width="50">
        <span class="fs-1 align-middle"> ALGo</span>
      </div>
      <div class="card-body">
        <div class="mb-5">
          <h4 class="card-title mb-3">비밀번호 찾기</h4>
          <div class="progress" style="max-width: 100px; height: 10px">
            <div class="progress-bar progress-bar-striped progress-bar-animated" style="width: 50%"></div>
          </div>
        </div>
        <div class="mb-3">
          <label for="username" class="form-label fw-bold">아이디</label>
          <input type="text" v-model="username" id="username" maxlength="20" :disabled="sendMail"
                 class="form-control form-control-lg">
        </div>
        <div class="mb-3">
          <label for="email" class="form-label fw-bold">이메일</label>
          <div class="row">
            <div class="col-lg-auto">
              <input type="text" v-model="email" id="email" maxlength="40" :disabled="sendMail"
                     @blur="checkEmail" @keyup.enter="checkEmail(true)"
                     class="form-control form-control-lg">
              <p v-show="enableErrorMessage" class="text-danger"> {{ errorMessage }} </p>
            </div>
            <div class="col-lg-auto mt-lg-0 mt-2">
              <button class="btn btn-lg btn-primary" @click="checkEmail(true)" :disabled="sendMail">인증번호 받기</button>
            </div>
          </div>
        </div>
        <div v-show="inputCodeEnable" class="row mt-2">
          <div class="col-9">
            <input type="tel" v-model="authCode" class="form-control form-control-lg" placeholder="인증번호 입력" maxlength="6" @keyup.enter="checkAuthCode">
            <p v-show="enableCodeErrorMessage" class="text-danger"> {{ codeErrorMessage }} </p>
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
          이메일이 정확한지 확인해 주세요.<br/>
          서버 환경에 따라 인증번호 발송이 늦어질 수 있습니다.<br/>
          계속해서 인증 이메일이 오지 않으면, 다시 인증번호를 요청해 주세요.<br/>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" @click="sendVerifyMail(true)" data-bs-dismiss="modal">인증메일 재발송</button>
          <button type="button" class="btn btn-secondary" @click="changeEmail" data-bs-dismiss="modal">다른 이메일로 인증</button>
        </div>
      </div>
    </div>
  </div>
  <!-- <button class="btn btn-warning" @click="test">test</button> -->
</template>

<script>
import { ref } from 'vue'
import useAxios from '@/modules/axios'
import Swal from 'sweetalert2'

export default {
  emits: ['step2'],
  setup(props, { emit }) {
    const { axiosGet, axiosPost } = useAxios()
    const email = ref('')
    const username = ref('')
    const emailValidation = ref(false)  // 이메일 정규식 검사 결과
    const emailDuplicated = ref(false)  // 이메일 중복 여부
    const emailDuplicatedCheck = ref(false) // 이메일 중복검사를 했는지 여부
    const sendMail = ref(false) // 인증메일 발송여부
    const inputCodeEnable = ref(false)  // 인증메일 입력칸 활성화 여부
    const enableErrorMessage = ref(false) // 에러 메시지 출력 여부
    const errorMessage = ref('')  // 에러 메시지
    const authCode = ref('')  // 인증 코드

    const enableCodeErrorMessage = ref(false)
    const codeErrorMessage = ref('')
    const isComplete = ref(false)

    const resetErrorMessage = () => {
      enableErrorMessage.value = false
      errorMessage.value = ''
      enableCodeErrorMessage.value = false
      codeErrorMessage.value = ''
    }

    const checkEmail = (sendMail = false) => {  // 이메일(아이디) 정규식 검사
      resetErrorMessage()
      emailValidation.value = false
      emailDuplicated.value = false
      emailDuplicatedCheck.value = false

      const validateEmail = /^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\.[A-Za-z0-9\\-]+/
      emailValidation.value = !(!validateEmail.test(email.value) || !email.value);
      if (!emailValidation.value) {
        enableErrorMessage.value = true
        errorMessage.value = '이메일 형식을 확인해주세요.'
      } else {
        duplicateCheck(sendMail)
      }
    }

    const duplicateCheck = (sendMail) => { // 이메일(아이디) 중복 검사
      resetErrorMessage()
      if (email.value && emailValidation.value) {
        emailDuplicatedCheck.value = true
        axiosGet(`/api/v1/verify-email?email=${email}`, (res) => {
          emailDuplicated.value = res.data
          if (emailDuplicated.value) {
            if (sendMail === true) {
              sendVerifyMail()
            }
          } else {
            enableErrorMessage.value = true
            errorMessage.value = '해당 이메일로 가입된 정보가 없습니다.'
          }
        }, (err) => console.error(err))
      }
    }

    const sendVerifyMail = (resend = false) => {
      if (emailValidation.value && emailDuplicated.value && emailDuplicatedCheck.value) {
        if (resend === false) {
          alert('해당 이메일로 인증메일을 발송했습니다.')
        } else {
          clearTimeout(Timer)
          leftTime.value = 300
          alert('인증메일을 재발송했습니다.')
        }
        startTimer()
        sendMail.value = true
        inputCodeEnable.value = true
        axiosPost('/api/v1/emails', {
          email: email.value,
          username: username.value,
        }, () => {
          enableCodeErrorMessage.value = true
          codeErrorMessage.value = '이메일로 발송된 6자리의 인증번호를 입력해 주세요.'
        }, (err) => {
          alert('인증메일 발송에 실패했습니다.')
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
      if (emailValidation.value && emailDuplicated.value && emailDuplicatedCheck.value && inputCodeEnable.value && sendMail.value) {
        if (leftTime.value > 0) {
          axiosPost('/api/v1/validate', {
            email: email.value,
            code: authCode.value,
          }, () => {
            enableCodeErrorMessage.value = false
            codeErrorMessage.value = ''
            isComplete.value = true
            emit('step2', email.value)
          }, () => {
            enableCodeErrorMessage.value = true
            codeErrorMessage.value = '인증번호가 일치하지 않습니다.'
          })
        } else {
          alert('인증시간이 만료되었습니다. 다시 인증해주세요.')
        }
      }
    }

    const changeEmail = () => {
      email.value = ''
      emailValidation.value = false
      emailDuplicated.value = false
      emailDuplicatedCheck.value = false
      sendMail.value = false
      inputCodeEnable.value = false
      authCode.value = ''
      clearTimeout(Timer)
      leftTime.value = 300
      resetErrorMessage()
    }

    const test = () => { // api test code
      axiosPost('/api/v1/emails', {
            email: 'test@naver.com',
            username: 'testname',
          }, (res) => {
            console.log(res)
          }, (err) => {
            console.log('에러: ' + err)
          })
    }

    return {
      test,
      email,
      username,
      duplicateCheck,
      emailValidation,
      sendVerifyMail,
      emailDuplicated,
      sendMail,
      errorMessage,
      enableErrorMessage,
      checkEmail,
      authCode,
      leftTime,
      inputCodeEnable,
      emailDuplicatedCheck,
      checkAuthCode,
      changeEmail,
      enableCodeErrorMessage,
      codeErrorMessage,
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