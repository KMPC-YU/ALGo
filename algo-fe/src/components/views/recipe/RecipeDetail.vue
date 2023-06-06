<template>
  <div class="container p-5">
    <div class="text-center mb-4">
      <h1>맛있는 초밥</h1>
      <h5>집에서도 요리할 수 있는 초밥 레시피</h5>
    </div>
    <div class="text-center mb-4">
      <img
        class="thumbnail"
        src="https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000370/img/basic/a0000370_main.jpg?"
        alt=""
      >
    </div>
    <div class="row justify-content-center text-center mb-5">
      <div class="col-2">
        <h3><i class="bi bi-people-fill text-secondary"></i></h3>
        <h5>3인분</h5>
      </div>
      <div class="col-2">
        <h3><i class="bi bi-stopwatch-fill text-success"></i></h3>
        <h5>30분 이내</h5>
      </div>
      <div class="col-2">
        <h3><i class="bi bi-speedometer text-danger"></i></h3>
        <h5>누구나</h5>
      </div>
      <div class="col-2">
        <h3><i class="bi bi-star-fill text-warning"></i></h3>
        <h5>500</h5>
      </div>
    </div>
    <div class="text-center">
      <div class="row justify-content-center mb-4">
        <div class="col-4">
          <h5>재료</h5>
          <div class="col card">
            <div class="card-body">
              <div class="row mb-2">
                <div class="col">생선회</div>
                <div class="col">3인분</div>
              </div>
              <div class="row">
                <div class="col mb">쌀</div>
                <div class="col">100g</div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-4">
          <h5>재료 알레르기 분류</h5>
          <div class="col card">
            <div class="card-body">
              <div class="mb-2">생선</div>
              <div>&nbsp;</div>
            </div>
          </div>
        </div>
      </div>
      <div class="row justify-content-center mb-4">
        <div class="col-8">
          <h5>요리 순서</h5>
          <div class="col card">
            <div class="card-body">
              <div class="row justify-content-around mb-2">
                <div class="col-4 text-center align-self-center">1. 생선회를 씻는다</div>
                <div class="col-4">
                  <img
                    class="order-img"
                    src="https://recipe1.ezmember.co.kr/cache/recipe/2020/12/02/1112a6de65342f4d10e6bc72f1ffa2741.jpg"
                    alt=""
                  >
                </div>
              </div>
              <div class="row justify-content-around mb-2">
                <div class="col-4 text-center align-self-center">2. 밥을 짓는다</div>
                <div class="col-4">
                  <img
                    class="order-img"
                    src="https://recipe1.ezmember.co.kr/cache/recipe/2020/12/02/5bf0453b7a7db5f609bcd1dacef28c0f1.jpg"
                    alt=""
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row justify-content-center mb-4">
        <div class="col-4">
          <h5>완성 사진</h5>
          <div class="col card">
            <div class="card-body">
              <img
                class="order-img"
                src="https://recipe1.ezmember.co.kr/cache/recipe/2020/12/02/296a1504dbb0a4be0d218a0c2555207e1.jpg"
                alt=""
              >
            </div>
          </div>
        </div>
        <div class="col-4">
          <h5>요리 팁</h5>
          <div class="col card">
            <div class="card-body align-self-center">
              쉽게 따라할 수 있어요!
            </div>
          </div>
        </div>
      </div>
    </div>
    <div>
      <div class="row justify-content-center mb-4">
        <div class="col-4">
          <i class="bi bi-tag-fill text-primary"></i>
          <a href="#">#생선</a>
          <span>&nbsp;</span>
          <a href="#">#쌀밥</a>
        </div>
        <div class="col-4">
          <!-- 허위 구역 -->
        </div>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col-8">
        <CommentWrite/>
      </div>
    </div>
  </div>
</template>

<script>
import CommentWrite from '@views/Comment/CommentWrite.vue'

export default {
  components: {
    CommentWrite,
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