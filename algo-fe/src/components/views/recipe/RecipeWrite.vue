<template>
  <div class="container p-5">
    <div class="row justify-content-center text-center">
      <!-- 기본 정보 -->
      <div class="col-8 card mb-4">
        <div class="card-body">
          <div class="row justify-content-center align-items-center m-3">
            <div class="col-8">
              <div class="row form-group">
                <div class="col-3">
                  <label class="fs-4 mt-1" for="title">이름</label>
                </div>
                <div class="col-9 mb-4">
                  <input type="text" class="form-control" id="title" placeholder="레시피 이름을 입력해주세요." maxlength="50" required>
                </div>
              </div>
              <div class="row form-group">
                <div class="col-3">
                  <label class="fs-4 mt-1" for="intro">소개</label>
                </div>
                <div class="col-9 mb-4">
                  <input type="text" class="form-control" id="intro" placeholder="레시피에 대한 간단한 설명을 입력하세요." maxlength="50" required>
                </div>
              </div>
              <div class="row">
                <div class="col-3">
                  <label class="fs-4 mt-1">카테고리</label>
                </div>
                <div class="col-9 mb-4">
                  <div class="row">
                    <div class="col-6">
                      <select v-model="selectType" class="form-select">
                        <option value="1">밑반찬</option>
                        <option value="2">메인반찬</option>
                        <option value="3">국/탕</option>
                        <option value="4">찌개</option>
                        <option value="5">디저트</option>
                        <option value="6">면/만두</option>
                        <option value="7">밥/죽/떡</option>
                        <option value="8">퓨전</option>
                        <option value="9">김치/젓갈/장류</option>
                        <option value="10">양념/소스/잼</option>
                        <option value="11">양식</option>
                        <option value="12">샐러드</option>
                        <option value="13">스프</option>
                        <option value="14">빵</option>
                        <option value="15">과자</option>
                        <option value="16">차/음료/술</option>
                        <option value="17">기타</option>
                      </select>
                    </div>
                    <div class="col-6">
                      <select v-model="selectCond" class="form-select">
                        <option value="1">일상</option>
                        <option value="2">초스피드</option>
                        <option value="3">손님접대</option>
                        <option value="4">술안주</option>
                        <option value="5">다이어트</option>
                        <option value="6">도시락</option>
                        <option value="7">영양식</option>
                        <option value="8">간식</option>
                        <option value="9">야식</option>
                        <option value="10">푸드스타일링</option>
                        <option value="11">해장</option>
                        <option value="12">명절</option>
                        <option value="13">이유식</option>
                        <option value="14">기타</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-3">
                  <label class="fs-4 mt-1">요리정보</label>
                </div>
                <div class="col-9">
                  <div class="row">
                    <div class="col-4">
                      <select v-model="selectAmount" class="form-select">
                        <option value="1">1인분</option>
                        <option value="2">2인분</option>
                        <option value="3">3인분</option>
                        <option value="4">4인분</option>
                        <option value="5">5인분</option>
                        <option value="6">6인분+</option>
                      </select>
                    </div>
                    <div class="col-4">
                      <select v-model="selectTime" class="form-select">
                        <option value="1">5분 이내</option>
                        <option value="2">10분 이내</option>
                        <option value="3">15분 이내</option>
                        <option value="4">20분 이내</option>
                        <option value="5">30분 이내</option>
                        <option value="6">45분 이내</option>
                        <option value="7">1시간 이내</option>
                        <option value="8">2시간 이내</option>
                        <option value="9">2시간 이상</option>
                      </select>
                    </div>
                    <div class="col-4">
                      <select v-model="selectLevel" class="form-select">
                        <option value="1">누구나</option>
                        <option value="2">초급</option>
                        <option value="3">중급</option>
                        <option value="4">고급</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-4">
              <div>
                <img
                  src="https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000370/img/basic/a0000370_main.jpg"
                  class="mb-3"
                  height="100"
                  alt="/ALGo_Logo.ico"
                  loading="lazy"
                />
              </div>
              <div>
                <button class="btn btn-primary mx-2">등록</button>
                <button class="btn btn-danger mx-2">삭제</button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 재료 -->
      <div class="col-8 card mb-4">
        <div class="card-body">
          <div class="row">
            <div class="col-2 ps-5">
              <label class="fs-4 mt-1">재료</label>
            </div>
            <div class="col-9">
              <div v-for="(item, idx) in ingredients" :key="item" class="row justify-content-center mb-2">
                <div class="col-5">
                  <input v-model="item.name" type="text" class="form-control" placeholder="예) 돼지고기" required>
                </div>
                <div class="col-3">
                  <input v-model="item.amount" type="text" class="form-control" placeholder="예) 150g">
                </div>
                <div class="col-3 ms-4">
                  <div class="btn-group" role="group">
                    <button type="button" class="btn btn-primary btn-sm mt-1">알레르기</button>
                    <button type="button" class="btn btn-danger btn-sm mt-1" @click="removeIngredient(idx)">삭제</button>
                  </div>
                </div>
              </div>
              <div>
                <button type="button" class="btn btn-success btn-sm mt-1" @click="addIngredient">추가</button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 요리 순서 -->
      <div class="col-8 card mb-4">
        <div class="card-body">
          <div class="row">
            <div class="col-2 ps-5">
              <label class="fs-4 mt-5">순서</label>
            </div>
            <div class="col-9">
              <div v-for="(step, idx) in steps" :key="step" class="row justify-content-center mb-2">
                <div class="col-7">
                  <textarea v-model="step.instruction" class="form-control" required style="resize: none; height: 150px"></textarea>
                </div>
                <div class="col-4 ms-4">
                  <div>
                    <img
                      src="https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000370/img/basic/a0000370_main.jpg"
                      class="mb-3"
                      height="100"
                      alt="/ALGo_Logo.ico"
                      loading="lazy"
                    />
                  </div>
                  <div class="text-center">
                    <button type="button" class="btn btn-warning btn-sm me-2" @click="removeStep(idx)">순서삭제</button>
                    <div class="btn-group text-center" role="group">
                      <button type="button" class="btn btn-primary btn-sm">등록</button>
                      <button type="button" class="btn btn-danger btn-sm">삭제</button>
                    </div>
                  </div>
                </div>
              </div>
              <div>
                <button type="button" class="btn btn-success btn-sm mt-1" @click="addStep">추가</button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 완성 사진, 요리 팁 -->
      <div class="col-8 card mb-4">
        <div class="card-body">
          <div class="row">
            <div class="col-6">
              <div class="row justify-content-center align-items-center">
                <div class="col-4">
                  <label class="fs-4">완성사진</label>
                </div>
                <div class="col-6">
                  <img
                    src="https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000370/img/basic/a0000370_main.jpg"
                    class="mb-3"
                    height="100"
                    alt="/ALGo_Logo.ico"
                    loading="lazy"
                  />
                  <div class="btn-group text-center" role="group">
                    <button type="button" class="btn btn-primary btn-sm">등록</button>
                    <button type="button" class="btn btn-danger btn-sm">삭제</button>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-6">
              <div class="row justify-content-center align-items-center">
                <div class="col-4">
                  <label class="fs-4">요리 팁</label>
                </div>
                <div class="col-6 ps-0">
                  <textarea class="form-control" placeholder="특별한 팁이 있으면 적어주세요." style="resize: none; height: 150px"></textarea>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 태그 -->
      <div class="col-8 card mb-4">
        <div class="card-body">
          <div class="row">
            <div class="col-2 ps-5">
              <label class="fs-4 mt-1">태그</label>
            </div>
            <div class="col-9 ps-4">
              <Vue3TagsInput v-model="tags" limit="5"/>
            </div>
          </div>
        </div>
      </div>
      <!-- 저장 -->
      <div class="col-8 card mb-4">
        <div class="card-body">
          <div class="text-center">
            <button class="btn btn-success mx-2" type="button">등록</button>
            <button class="btn btn-secondary mx-2" type="button" @click="gotoList">취소</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import router from "@/router/router.js"
import Vue3TagsInput from 'vue3-tags-input'
import { ref } from 'vue'

export default {
  components: {
    Vue3TagsInput,
  },
  setup() {
    const ingredients = ref([
      { name: '', amount: '' },
    ])
    const steps = ref([
      { instruction: '', photo: '' },
    ])
    const tags = ref([])

    const addIngredient = () => {
      ingredients.value.push({ name: '', amount: '' })
    }

    const removeIngredient = (idx) => {
      ingredients.value.splice(idx, 1)
    }

    const addStep = () => {
      steps.value.push({ instruction: '', photo: '' })
    }

    const removeStep = (idx) => {
      steps.value.splice(idx, 1)
    }

    const gotoList = () => {
      router.go(-1)
    }

    return {
      ingredients,
      steps,
      tags,
      addIngredient,
      removeIngredient,
      addStep,
      removeStep,
      gotoList,
    }
  }
}
</script>

<style scoped>
.thumbnail {
  width: 600px;
  height: 200px;
  object-fit: scale-down;
}

.order-img {
  width: 200px;
  height: 200px;
  object-fit: scale-down;
}

.card {
  border-radius: 0;
}
</style>