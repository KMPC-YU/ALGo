<template>
  <div class="container py-5">
<!--    <h2 class="mb-4">{{  }}</h2>-->
    <table class="table text-center">
      <thead class="table-secondary">
      <tr>
        <th scope="col" class="p-3" colspan="8">
          <span v-if="boardType === 'QUESTION'" class="point">100</span>
          {{ postDetailData.title }}
        </th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <th class="col-md-1">작성자</th>
        <td class="col-md-1 text-start" colspan="7">{{ postDetailData.author }}</td>
      </tr>
      <tr>
        <th class="col-md-1 ">등록일</th>
        <td class="col-md-7 text-start" colspan="3">{{ postDetailData.created_at }}</td>
        <th class="col-md-1 text-end">조회수</th>
        <td class="col-md-1">{{ postDetailData.view_count }}</td>
        <th class="col-md-1 text-end">추천수</th>
        <td class="col-md-1">{{ postDetailData.like_count }}</td>
      </tr>
      <tr v-if="boardType === 'QUESTION'">
        <th class="col-md-1">채택 포인트</th>
        <td class="col-md-1 text-start" colspan="7">100</td>
      </tr>
      </tbody>
    </table>
<!--    게시글 내용-->
    <div class="content px-4 py-2">
      <div v-dompurify-html="postDetailData.content"></div>
    </div>
<!--    게시글 내용-->
    <hr/>
<!--    버튼 영역-->
    <div class="d-flex mt-2">
      <div class="me-auto">
        <div class="btn" :class="[postDetailData.is_like ? 'btn-like' : 'btn-default' ]" @click="postLikeHandler">
          <i class="bi" :class="[postDetailData.is_like ? 'bi-hand-thumbs-up-fill' : 'bi-hand-thumbs-up']"></i>
          <span> 추천</span>
        </div>
      </div>
      <div>
        <div class="btn-group" role="group" aria-label="Basic example">
          <router-link type="button" class="btn btn-default" :to="{ name: 'PostModify', params: { board_id: boardID, post_id: postID }  }">
            <i class="fa-solid fa-pencil me-1"></i>
            수정
          </router-link>
          <button type="button" class="btn btn-default" @click="postDelete">
            <i class="fa-solid fa-trash me-1"></i>
            삭제
          </button>
          <button type="button" class="btn btn-default" @click="moveToPostListPage">
            <i class="fa-solid fa-bars"></i>
            목록으로
          </button>
        </div>
      </div>
    </div>
    <br/>
<!--    버튼 영역-->
    <div>
      <comment-write :boardID="boardID" :postID="postID"/>
    </div>
  </div>
</template>

<script>
import router from "@/router/router.js";
import { useRoute } from 'vue-router'
import * as PostAPI from '@/services/post.js'
import { ref, onMounted, computed } from "vue";
import Swal from "sweetalert2";
import CommentWrite from "@views/Comment/CommentWrite.vue";

export default {
  components: {
    CommentWrite,
  },
  setup() {
    const route = useRoute()
    const boardID = computed(() => {
      return route.params.board_id
    })

    const postID = computed(() => {
      return route.params.post_id
    })

    onMounted(() => {
      getPostDetail()
      getBoardInfo()
    })

    const moveToPostListPage = () => {
      router.go(-1)
    }

    const postDetailData = ref('')
    const boardType = ref('')

    const getPostDetail = () => {
      PostAPI.postDetail(boardID.value, postID.value).then((res) => {
        console.log(res.data)
        postDetailData.value = res.data
      }).catch((err) => {
        console.error(err)
      })
    }

    const getBoardInfo = () => {
      PostAPI.BoardInfo(boardID.value).then((res) => {
        boardType.value = res.data.board_type
      })
    }

    const postDelete = () => {
      Swal.fire({
        title: '게시글을 삭제하시겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '삭제',
        cancelButtonText: '취소',
      }).then((result) => {
        if (result.isConfirmed) {
          PostAPI.postDelete(boardID.value, postID.value).then(() => {
            router.push({ name: 'PostList', params: { board_id: boardID.value }, query: { page: 1 } })
          })
        }
      })
    }

    const postLikeHandler = () => {
      console.log(postDetailData.value.is_like)
      if (postDetailData.value.is_like) {
        PostAPI.postLikeDelete(boardID.value, postID.value).then(() => {
          alert('추천취소됨')
          getPostDetail()
        }).catch((err) => {
          console.error(err)
        })
      } else {
        PostAPI.postLikeCreate(boardID.value, postID.value).then(() => {
          alert('추천완료')
          getPostDetail()
        }).catch((err) => {
          console.error(err)
        })
      }
    }

    return {
      moveToPostListPage, postDetailData, boardID, postID, postDelete, postLikeHandler, boardType,
    }
  }
}
</script>

<style scoped>
.table {
  background : white;
}
.btn-default {
  color: #394263;
  background-color: #ffffff;
  border-color: #dbe1e8;
  font-weight: bold;
  letter-spacing: 0.05em;
}
.btn-default:hover,
.btn-default:active,
.btn-default:focus {
  background: #fafafa;
  //color: #ffffff;
  border-color: #000000;
}

.btn-like {
  color: #FFA500;
  background-color: #ffffff;
  border-color: #FFA500;
  font-weight: bold;
  letter-spacing: 0.05em;
}
.btn-like:hover,
.btn-like:active,
.btn-like:focus {
  background: #fafafa;
  color: #FFA500;
  border-color: #FFA500;
}

.point {
  background-color: #7f8fa4;
  border-radius: 2px;
  color: #FFFFFF;
  font-weight: bold;
  padding: 3px;
  margin-right: 5px;
  font-size: 15px;
}
</style>