<template>
  <div class="container">
    <div class="card d-flex justify-content-center align-items-center">
      <div class="logo text-center">
        <img src="/public/ALGo_Logo.ico" alt="" width="50">
        <span class="fs-1 align-middle"> ALGO</span>
      </div>
      <div class="card-body">
        <form @submit.prevent="signup">
          <div class="mb-3 ">
            <label for="username" class="form-label fw-bold">아이디</label>
            <input type="text" v-model="username" id="username" maxlength="20" @blur="usernameValidCheck"
                   class="form-control form-control-lg"
                   :class="{'': usernameValid === 1, 'is-valid': usernameValid === 2, 'is-invalid': usernameValid === 3}">
            <div class="invalid-feedback">
              {{ usernameMessage }}
            </div>
          </div>
          <div class="mb-3">
            <label for="password" class="form-label fw-bold">비밀번호</label>
            <input type="password" v-model="password" id="password" maxlength="20" @blur="passwordValidCheck"
                   class="form-control form-control-lg"
                   :class="{'': passwordValid === 1, 'is-valid': passwordValid === 2, 'is-invalid': passwordValid === 3}">
            <div class="invalid-feedback">
              {{ passwordMessage }}
            </div>
          </div>
          <div class="mb-3">
            <label for="passwordCheck" class="form-label fw-bold">비밀번호 재확인</label>
            <input type="password" v-model="password2" id="passwordCheck" maxlength="20" @blur="password2ValidCheck"
                   class="form-control form-control-lg"
                   :class="{'': password2Valid === 1, 'is-valid': password2Valid === 2, 'is-invalid': password2Valid === 3}">
            <div class="invalid-feedback">
              {{ password2Message }}
            </div>
          </div>
          <div class="mb-3">
            <label for="email" class="form-label fw-bold">이메일</label>
            <input type="text" v-model="email" id="email" maxlength="40" @blur="emailValidCheck"
                   class="form-control form-control-lg"
                   :class="{'': emailValid === 1, 'is-valid': emailValid === 2, 'is-invalid': emailValid === 3}">
            <div class="invalid-feedback">
              {{ emailMessage }}
            </div>
          </div>
          <div class="mb-5">
            <label for="nickname" class="form-label fw-bold">닉네임</label>
            <input type="text" v-model="nickname" id="nickname" maxlength="20" @blur="nicknameValidCheck"
                   class="form-control form-control-lg"
                   :class="{'': nicknameValid === 1, 'is-valid': nicknameValid === 2, 'is-invalid': nicknameValid === 3}">
            <div class="invalid-feedback">
              {{ nicknameMessage }}
            </div>
          </div>
          <div class="mb-3">
            <label for="allergy" class="form-label fw-bold mb-3">알레르기 정보 <span class="text-secondary fw-normal small">(선택)</span></label>
            <div class="row text-center">
              <div v-for="allergy in allergies" :key="allergy.name" class="col-3 col-sm-2">
                <input type="checkbox" :id="allergy.name" v-model="selectedAllergies" :value="allergy.name" style="display: none">
                <label :for="allergy.name" class="allergy-icon">
                  <img :src="allergy.src" :alt="allergy.name" width="50">
                  <p class="pt-2">{{ allergy.mean }}</p>
                </label>
              </div>
            </div>
          </div>
          <div>
            <button type="submit" class="btn btn-primary btn-lg form-control">가입하기</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import Swal from 'sweetalert2'
export default {
  setup() {
    const router = useRouter()

    const username = ref('')
    const password = ref('')
    const password2 = ref('')
    const email = ref('')
    const nickname = ref('')

    const usernameValid = ref(1)
    const passwordValid = ref(1)
    const password2Valid = ref(1)
    const emailValid = ref(1)
    const nicknameValid = ref(1)

    const usernameMessage = ref('')
    const passwordMessage = ref('')
    const password2Message = ref('')
    const emailMessage = ref('')
    const nicknameMessage = ref('')

    const selectedAllergies = ref([])

    const allergies = reactive([
      { name: "beef", mean: "소고기", src: "/src/assets/allergy_icon/beef.png", selected: false },
      { name: "buckwheat", mean: "메밀", src: "/src/assets/allergy_icon/buckwheat.png", selected: false },
      { name: "chicken", mean: "닭", src: "/src/assets/allergy_icon/chicken.png", selected: false },
      { name: "clams", mean: "조개류", src: "/src/assets/allergy_icon/clams.png", selected: false },
      { name: "crab", mean: "게", src: "/src/assets/allergy_icon/crab.png", selected: false },
      { name: "eggs", mean: "난류", src: "/src/assets/allergy_icon/eggs.png", selected: false },
      { name: "fish", mean: "생선", src: "/src/assets/allergy_icon/fish.png", selected: false },
      { name: "fruit", mean: "과일", src: "/src/assets/allergy_icon/fruit.png", selected: false },
      { name: "garlic", mean: "마늘", src: "/src/assets/allergy_icon/garlic.png", selected: false },
      { name: "milk", mean: "우유", src: "/src/assets/allergy_icon/milk.png", selected: false },
      { name: "nuts", mean: "견과류", src: "/src/assets/allergy_icon/nuts.png", selected: false },
      { name: "pork", mean: "돼지고기", src: "/src/assets/allergy_icon/pork.png", selected: false },
      { name: "sesame", mean: "참깨", src: "/src/assets/allergy_icon/sesame.png", selected: false },
      { name: "shrimp", mean: "새우", src: "/src/assets/allergy_icon/shrimp.png", selected: false },
      { name: "soybean", mean: "대두", src: "/src/assets/allergy_icon/soybean.png", selected: false },
      { name: "squid", mean: "오징어", src: "/src/assets/allergy_icon/squid.png", selected: false },
      { name: "sulphite", mean: "아황산류", src: "/src/assets/allergy_icon/sulphite.png", selected: false },
      { name: "tomato", mean: "토마토", src: "/src/assets/allergy_icon/tomato.png", selected: false },
      { name: "vegetables", mean: "채소", src: "/src/assets/allergy_icon/vegetables.png", selected: false },
      { name: "wheat", mean: "밀", src: "/src/assets/allergy_icon/wheat.png", selected: false },
    ]);

    const usernameValidCheck = () => {
      // Todo : 아이디 중복확인 API 추가
      if (username.value.length === 0) {
        usernameValid.value = 3
        usernameMessage.value = '아이디를 입력하세요.'
      } else if (!/^[a-zA-Z0-9_-]+$/.test(username.value)) {
        usernameValid.value = 3
        usernameMessage.value = '5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.'
      } else {
        usernameValid.value = 2
      }
    }

    const passwordValidCheck = () => {
      if (password.value.length === 0) {
        passwordValid.value = 3
        passwordMessage.value = '비밀번호를 입력하세요.'
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

    const emailValidCheck = () => {
      // Todo : 이메일 중복확인 API 추가
      if (email.value.length === 0) {
        emailValid.value = 3
        emailMessage.value = '이메일을 입력하세요.'
      } else if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email.value)) {
        emailValid.value = 3
        emailMessage.value = '올바른 형식의 이메일을 입력해주세요.'
      } else {
        emailValid.value = 2
      }
    }

    const nicknameValidCheck = () => {
      // Todo : 닉네임 중복확인 로직 추가
      if (nickname.value.length === 0) {
        nicknameValid.value = 3
        nicknameMessage.value = '닉네임을 입력하세요.'
      } else if (!/^[가-힣a-zA-Z0-9_-]{2,20}$/.test(nickname.value)) {
        nicknameValid.value = 3
        nicknameMessage.value = '닉네임은 최소 2자 이상, 최대 20자 이하의 알파벳 대소문자, 숫자, 한글만 사용할 수 있습니다.'
      } else {
        nicknameValid.value = 2
      }
    }

    const signup = () => {
      usernameValidCheck()
      passwordValidCheck()
      password2ValidCheck()
      emailValidCheck()
      nicknameValidCheck()
      Swal.fire({
        title: '회원가입 완료',
        text: "ALGo의 회원이 되신것 환영합니다!",
        icon: 'success',
        showConfirmButton: false,
        timer: 2000,
      }).then(() => {
        router.push({ name: 'Login'})
      });
      if (usernameValid.value === 2 && passwordValid.value === 2
          && password2Valid.value === 2 && emailValid.value === 2 && nicknameValid.value === 2) {
        // Todo : 회원가입 API 추가
      } else {

      }
    }

    return {
      allergies, selectedAllergies, username, password, password2, email, nickname,
      usernameValid, passwordValid, password2Valid, emailValid, nicknameValid,
      usernameMessage, passwordMessage, password2Message, emailMessage, nicknameMessage,
      signup, usernameValidCheck, passwordValidCheck, password2ValidCheck, emailValidCheck, nicknameValidCheck,
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
.card-body {
  max-width: 500px;
}
.allergy-icon {
  opacity:0.3;
  cursor: pointer;
  transition:all .3s ease;
}
.allergy-icon:hover {
  opacity:1.0;
  transform:scale(1.15);
}

input[type="checkbox"]:checked + .allergy-icon {
  opacity: 1.0;
  transform:scale(1.15);
  font-weight: bold;
}
</style>