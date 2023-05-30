<template>
  <div class="container py-5">
<!--    <h2 class="mb-4">{{  }}</h2>-->
    <table class="table text-center">
      <thead class="table-secondary">
      <tr>
        <th scope="col" class="p-3" colspan="8">{{ postDetailData.title }}</th>
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
        <div class="btn btn-default">
          <i class="fa-solid fa-thumbs-up"></i>
          추천
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

<!--    댓글 영역-->
    <div class="row card-body mt-2">
      <div class="input-group mb-2">
        <textarea class="form-control" placeholder="댓글을 남겨보세요" @keydown="resize" @keyup="resize" style="resize:none; overflow: hidden"/>
        <button class="btn btn-default" type="button" @click="addComment">등록</button>
      </div>
      <span class="fw-bold">댓글
        <span class="text-primary">0</span>
      </span>
    </div>
<!--    댓글 영역-->
  </div>
</template>

<script>
import router from "@/router/router.js";
import { useRoute } from 'vue-router'
import * as PostAPI from '@/services/post.js'
import { ref, onMounted, computed } from "vue";
import {postDetail} from "@/services/post.js";
import Swal from "sweetalert2";

export default {
  methods: {postDetail},
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
    })

    const moveToPostListPage = () => {
      router.go(-1)
    }

    const postDetailData = ref('')

    const getPostDetail = () => {
      PostAPI.postDetail(boardID.value, postID.value).then((res) => {
        console.log(res.data)
        postDetailData.value = res.data
      }).catch((err) => {
        console.error(err)
      })
    }

    const postDelete = () => {
      PostAPI.postDelete(boardID.value, postID.value).then((res) => {
        router.push(`/boards/${boardID.value}`)
        Swal.fire({
          title: '게시글이 삭제되었습니다.',
          icon: 'success',
          showConfirmButton: false,
          timer: 1500,
        }).catch(() => {
          Swal.fire({
            title: '게시글이 삭제에 실패했습니다',
            icon: 'error',
            showConfirmButton: false,
            timer: 1500,
          })
        })
      })
    }

    return {
      moveToPostListPage, postDetailData, boardID, postID, postDelete
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
</style>