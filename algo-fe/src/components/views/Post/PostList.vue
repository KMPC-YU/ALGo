<template>
    <div class="container py-5">
      <h2 class="mb-4">{{ boardName }}</h2>
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
        <tr>
          <th scope="col" class="col-md-1 d-none d-md-table-cell">번호</th>
          <th scope="col" class="col-9 col-md-6">제목</th>
          <th scope="col" class="col-md-1">작성자</th>
          <th scope="col" class="col-md-2 d-none d-md-table-cell">등록일</th>
          <th scope="col" class="col-md-1 d-none d-md-table-cell">조회수</th>
          <th scope="col" v-if="boardType !== 'QUESTION'" class="col-md-1 d-none d-md-table-cell">추천수</th>
          <th scope="col" v-else class="col-md-1 d-none d-md-table-cell">답변수</th>
        </tr>
        </thead>
        <tbody v-if="postData.length > 0">
          <tr v-for="post in postData">
            <td class="d-none d-md-table-cell" v-if="post.notice">공지</td>
            <td class="d-none d-md-table-cell" v-else>{{ post.id }}</td>
            <td>
              <span v-if="boardType === 'QUESTION'" class="point">100</span>
              <router-link :to="{ name: 'PostDetail', params: { board_id: boardID, post_id: post.id } }" class="text-decoration-none text-black post-title">
                {{ post.title }}
              </router-link>
            </td>
            <td>{{ post.author }}</td>
            <td class="d-none d-md-table-cell">{{ post.created_at }}</td>
            <td class="d-none d-md-table-cell">{{ post.view_count }}</td>
            <td v-if="boardType !== 'QUESTION'" class="d-none d-md-table-cell">{{ post.like_count }}</td>
            <td v-else class="d-none d-md-table-cell">0</td>
          </tr>
        </tbody>
        <tbody v-else>
          <tr>
            <td colspan="6">
              <span>작성된 글이 없습니다.</span>
            </td>
          </tr>
        </tbody>
      </table>
<!--      게시글 목록 테이블-->

<!--      글쓰기 버튼 및 페이지네이션-->
      <div class="text-end">
        <router-link class="btn btn-primary" :to="{ name: 'PostWrite', params: { board_id: boardID } }"><i class="fa-solid fa-pencil me-2"></i>글쓰기</router-link>
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
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import * as PostAPI from '@/services/post.js'
export default {
  setup() {
    const route = useRoute()
    const boardID = computed(() => {
      return route.params.board_id
    })
    const boardName = ref('')
    const boardType = ref('')

    watch(boardID, () => {
      getPostsList(boardID.value, 1)
      getBoardInfo()
    })

    onMounted(() => {
      getPostsList(boardID.value, 1)
      getBoardInfo()
    })

    const getBoardInfo = () => {
      PostAPI.BoardInfo(boardID.value).then((res) => {
        boardType.value = res.data.board_type
      })
    }

    const postData = ref('')
    const getPostsList = (boardID, page) => {
      PostAPI.getPostsList(boardID, page).then((res) => {
        console.log(res.data)
        boardName.value = res.data.board_name
        postData.value = res.data.postListDto
      }).catch((err) => {
        console.error(err)
      })
    }

    const selectedSearch = ref('TITLE')
    const searchText = ref('')  // 검색어
    const selectedSort = ref('createdAt,DESC') // 정렬

    const searchPost = () => {

    }

    return { selectedSearch, searchText, selectedSort, searchPost, boardID, postData, boardName, boardType }
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
.point {
  background-color: #7f8fa4;
  border-radius: 2px;
  color: #FFFFFF;
  font-weight: bold;
  padding: 3px;
  margin-right: 5px;
  font-size: 12px;
}
</style>