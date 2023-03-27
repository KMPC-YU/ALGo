<template>
  <div class="container">
    <div class="card d-flex justify-content-center align-items-center">
      <div class="logo text-center">
        <img src="/public/ALGo_Logo.ico" alt="" width="50">
        <span class="fs-1 align-middle"> ALGO</span>
      </div>
      <div class="card-body">
        <div class="mb-4">
          <div class="fs-4 fw-bold">
            <i class="bi bi-check-lg me-1"></i>
            <label for="condition&terms1" class="form-label">이용약관</label>
          </div>
          <textarea class="form-control shadow-sm" id="condition&terms1" cols="70" rows="10" readonly></textarea>
          <div class="form-check d-flex justify-content-end mt-2">
            <input class="form-check-input me-2" type="checkbox" value="" id="termsCheckBox1">
            <label class="form-check-label" for="termsCheckBox1">
              ALGo 이용약관에 동의합니다
            </label>
          </div>
        </div>
        <div class="mb-4">
          <div class="fs-4 fw-bold">
            <i class="bi bi-check-lg me-1"></i>
            <label for="condition&terms1" class="form-label">개인정보 수집 및 이용</label>
          </div>
          <textarea class="form-control shadow-sm" id="condition&terms1" cols="70" rows="10" readonly></textarea>
          <div class="form-check d-flex justify-content-end mt-2">
            <input class="form-check-input me-2" type="checkbox" value="" id="termsCheckBox2">
            <label class="form-check-label" for="termsCheckBox2">
              개인정보 수집 및 이용에 동의합니다
            </label>
          </div>
        </div>
        <div class="row mt-5">
          <div class="col">
            <button class="btn btn-secondary btn-lg me-3 form-control">취소</button>
          </div>
          <div class="col">
            <button class="btn btn-primary btn-lg form-control">다음</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {

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
.form-check-input {
  cursor: pointer;
}
.form-check-label {
  cursor: pointer;
}
</style>