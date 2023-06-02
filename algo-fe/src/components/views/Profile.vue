<template>
  <div class="container py-5">
    <div class="mb-5">
      <div class="card">
        <div class="card-body">
          <div class="row">
            <div class="col-lg-4 text-center">
              <div>
                <img
                  src="https://mblogthumb-phinf.pstatic.net/MjAxOTA0MjNfMTkz/MDAxNTU2MDE2ODQzNzY1.XIP77rvqSgGZPbY7vp_vhX_hGVWm1VBOlVaGe4ovrOYg.fav5jNf3PsvyBU2qVvZMBpJmevXZWRZAF3fa6LY1_tYg.JPEG.exia9133/g.jpg?type=w800"
                  class="rounded-circle mb-3"
                  height="150"
                  alt="Black and White Portrait of a Man"
                  loading="lazy"
                />
              </div>
              <div>
                <button class="btn btn-primary me-2">프로필 수정</button>
                <router-link class="btn btn-primary" :to="{ name: 'Chat' }">채팅</router-link>
              </div>
            </div>
            <div class="col-lg-8">
              <div class="row justify-content-between m-3">
                <div class="col-lg-6">
                  <h2 class="mb-3">Jisoo</h2>
                  <h5 class="text-secondary">hihijisoo@algo.com</h5>
                </div>
                <div class="col-lg-2 text-center">
                  <h2 class="mb-3">포인트</h2>
                  <h5>1972</h5>
                </div>
              </div>
              <hr>
              <div class="row justify-content-center text-center m-3">
                <div class="col-lg-3">
                  <h4 class="mb-3">게시글 작성 수</h4>
                  <div class="fw-bold">37</div>
                </div>
                <div class="col-lg-3">
                  <h4 class="mb-3">지식IN 답변 수</h4>
                  <div class="fw-bold">5</div>
                </div>
                <div class="col-lg-3">
                  <h4 class="mb-3">레시피 작성 수</h4>
                  <div class="fw-bold">0</div>
                </div>
                <div class="col-lg-3">
                  <h4 class="mb-3">친구 수</h4>
                  <div class="fw-bold">0</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row text-center">
      <div class="col-lg-4 mb-3">
        <h5>알레르기 정보</h5>
        <div class="card">
          <div class="card-body">
            달걀, 우유, 메밀, 땅콩
          </div>
        </div>
      </div>
      <div class="col-lg-4 mb-5">
        <h5>작성한 게시글</h5>
        <div class="card">
          <div class="card-body">
            <div class="d-flex justify-content-around">
              <a class="text-decoration-none text-black post-title" href="/boards/1/posts/1">작성한 게시글 샘플 1</a>
              <code class="">2023-06-01 22:31:44</code>
            </div>
          </div>
        </div>
      </div>
      <div class="col-lg-4 mb-5">
        <h5>작성한 댓글</h5>
        <div class="card">
          <div class="card-body">
            <div class="d-flex justify-content-around">
              <a class="text-decoration-none text-black post-title" href="/boards/1/posts/1">안녕안녕~ 나는 지수라고 해~</a>
              <code class="">2023-06-01 22:41:37</code>
            </div>
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
.card {
  border-radius: 0;
}

.post-title {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 200px;
}

.post-title:hover {
  font-weight: bold;
}
</style>