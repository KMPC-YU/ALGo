<template>
    <div class="container py-5">
      <h2 class="mb-4">공지사항</h2>
<!--      검색 및 정렬 영역-->
      <div class="row mb-4">
        <div class="col-auto">
          <select v-model="selectedSort" class="form-select">
            <option value="createdAt,DESC">작성일</option>
            <option value="viewCount,DESC">조회수</option>
            <option value="likeCount,DESC">추천수</option>
          </select>
        </div>
        <div class="col-auto ms-auto">
          <div class="d-none d-md-block">
            <select v-model="selectedSearch" class="form-select">
              <option value="TITLE">제목</option>
              <option value="AUTHOR">작성자</option>
              <option value="TITAUTH">제목 + 작성자</option>
            </select>
          </div>
        </div>
        <div class="col-7 col-md-5 col-lg-4 col-xl-3">
          <div class="input-group">
            <input type="search" class="form-control" v-model="searchText" @keyup.enter="searchPost" placeholder="검색"/>
            <button class="btn btn-primary" @click="searchPost">
              <i class="bi bi-search"></i>
            </button>
          </div>
        </div>
      </div>
<!--      검색 및 정렬 영역-->

<!--      게시글 목록 테이블-->
      <table class="table text-center">
        <thead class="table-dark">
          <th scope="col" class="col-md-1 d-none d-md-table-cell">번호</th>
          <th scope="col" class="col-9 col-md-6">제목</th>
          <th scope="col" class="col-md-1">작성자</th>
          <th scope="col" class="col-md-2 d-none d-md-table-cell">등록일</th>
          <th scope="col" class="col-md-1 d-none d-md-table-cell">조회수</th>
          <th scope="col" class="col-md-1 d-none d-md-table-cell">추천수</th>
        </thead>
        <tbody>
          <tr>
            <td class="d-none d-md-table-cell">2</td>
            <td>
              <a href="/boards/1/posts/1" class="text-decoration-none text-black post-title">
                식품알레르기정보, 레시피 공유 웹 서비스 ALGo 오픈 안내
              </a>
            </td>
            <td>관리자</td>
            <td class="d-none d-md-table-cell">2023-05-21 13:32</td>
            <td class="d-none d-md-table-cell">12,345</td>
            <td class="d-none d-md-table-cell">4</td>
          </tr>
          <tr>
            <td class="d-none d-md-table-cell">1</td>
            <td>BoB 12기 교육생 모집 공고</td>
            <td>관리자</td>
            <td class="d-none d-md-table-cell">2023-05-21 13:06</td>
            <td class="d-none d-md-table-cell">6,622</td>
            <td class="d-none d-md-table-cell">15</td>
          </tr>
        </tbody>
      </table>
<!--      게시글 목록 테이블-->

<!--      글쓰기 버튼 및 페이지네이션-->
      <div class="text-end">
        <router-link class="btn btn-primary" to="/boards/1/write"><i class="fa-solid fa-pencil me-2"></i>글쓰기</router-link>
      </div>
      <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
          <li class="page-item disabled">
            <a class="page-link" href="#" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
            </a>
            </li>
          <li class="page-item active"><a class="page-link" href="#">1</a></li>
          <li class="page-item disabled">
            <a class="page-link" href="#" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>
<!--      글쓰기 버튼 및 페이지네이션-->
    </div>
</template>

<script>
import { ref } from 'vue'
export default {
  setup() {
    const selectedSearch = ref('TITLE')
    const searchText = ref('')  // 검색어
    const selectedSort = ref('createdAt,DESC') // 정렬

    const searchPost = () => {

    }

    return { selectedSearch, searchText, selectedSort, searchPost }
  }
}

</script>

<style scoped>
.table td {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 100px;
}
.post-title:hover {

  font-weight: bold;
}
</style>