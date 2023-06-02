<template>
  <div class="row card-body mt-2">
    <div class="input-group mb-2">
      <textarea class="form-control" v-model="comment" placeholder="댓글을 남겨보세요" @keydown="resize" @keyup="resize" style="resize:none; overflow: hidden"/>
      <button class="btn btn-default" type="button" @click="addComment">등록</button>
    </div>
    <span class="fw-bold">댓글
        <span class="text-primary">{{ commentCnt }}</span>
    </span>
  </div>
  <!-- Comment with nested comments-->
  <div class="mb-4">
    <div :class="comment.parent === null ? 'mt-2 ms-3' : 'mt-2 ms-5'" v-for="comment in commentList" :key="comment.id">
      <div class="row justify-content-end mb-4">
        <div class="col">
          <div v-if="!comment.is_deleted">
                  <span class="fw-bold me-1">
                    <a class="text-link text-body" :href="`/profile/${comment.author}`">{{ comment.author }}</a>
                  </span>
            <i class="bi bi-dot me-1"></i>
            <code class="align-text-bottom">{{ comment.created_at === comment.modified_at ? comment.created_at : comment.modified_at + ' (수정됨)' }}</code>
            <div>{{ comment.content }}</div>
          </div>
          <div v-else>
            <span class="invisible fw-bold me-3">deleted</span>
            <code class="invisible">deleted</code>
            <div>[ 삭제된 댓글입니다. ]</div>
          </div>
        </div>
        <div v-if="!comment.is_deleted && !(comment.author !== nickname && comment.parent !== null)" class="col-1">
          <button class="btn btn-default" data-bs-toggle="dropdown">
            <i class="bi bi-three-dots-vertical"></i>
          </button>
          <ul class="dropdown-menu dropdown-menu-end text-center" style="min-width: 1px">
            <li v-if="comment.author === nickname" class="dropdown-item" @click="modifyId = comment.comment_id; newComment = comment.content">수정</li>
            <li v-if="comment.author === nickname" class="dropdown-item" @click="deleteComment(comment.comment_id)">삭제</li>
            <li v-if="comment.parent === null" class="dropdown-item" @click="subCommId = comment.comment_id; subComment = ''">답글</li>
          </ul>
        </div>
      </div>
      <!-- modify comment form -->
      <form v-if="comment.comment_id === modifyId" class="sub-comment-form">
        <textarea class="form-control mb-3" v-model="newComment" @keydown="resize" @keyup="resize" style="resize:none; overflow: hidden"/>
        <div class="text-end">
          <button class="btn btn-primary me-2" type="button" @click="modifyComment(comment.comment_id)">수정</button>
          <button class="btn btn-secondary" type="button" @click="modifyId = -1; newComment = ''">취소</button>
        </div>
      </form>
      <!-- add sub-comment form -->
      <form v-if="comment.comment_id === subCommId" class="sub-comment-form">
        <textarea class="form-control mb-3" v-model="subComment" @keydown="resize" @keyup="resize" style="resize:none; overflow: hidden"/>
        <div class="text-end">
          <button class="btn btn-primary me-2" type="button" @click="addSubComment(comment.comment_id)">등록</button>
          <button class="btn btn-secondary" type="button" @click="subCommId = -1; subComment = ''">취소</button>
        </div>
      </form>
    </div>
  </div>
  <!-- Pagination -->
  <div class="d-flex justify-content-center">
    <nav aria-label="Comment list page navigation">
      <ul class="pagination">
        <li v-if="currCommPage !== 1" class="page-item">
          <a style="cursor: pointer" class="page-link" @click="getCommentsList(currCommPage - 1)">이전</a>
        </li>
        <li v-for="page in numOfCommPage" :key="page" class="page-item" :class="currCommPage === page ? 'active' : ''">
          <a style="cursor: pointer" class="page-link" @click="getCommentsList(page)">{{ page }}</a>
        </li>
        <li v-if="currCommPage !== numOfCommPage" class="page-item">
          <a style="cursor: pointer" class="page-link" @click="getCommentsList(currCommPage + 1)">다음</a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import * as CommentAPI from '@/services/comment.js'
import store from '@/store/index.js'
import Swal from "sweetalert2";
import * as PostAPI from "@/services/post.js";
import router from "@/router/router.js";
export default {
  props: {
    boardID: {
      required: true
    },
    postID: {
      required: true
    },
  },
  setup(props) {
    onMounted(() => {
      getCommentsList(1)
    })

    const commentList = ref('') // 댓글 목록
    const comment = ref('') // 댓글 내용
    const currCommPage = ref(1) // 현재 댓글 페이지
    const numOfCommPage = ref(1)  // 총 댓글 페이지 수

    // modify variables
    const modifyId = ref(-1)
    const newComment = ref('')  // modify comment string variable

    // sub-comment variables
    const subCommId = ref(-1)
    const subComment = ref('')

    const nickname = store.getters.nickname

    const commentCnt = ref('')

    const getCommentsList = (page = currCommPage.value) => {
      currCommPage.value = page
      CommentAPI.getCommentsList(props.boardID, props.postID, page)
          .then((res) => {
            console.log(res.data)
            if (res.data.page_num === 0) {
              numOfCommPage.value = 1
            } else {
              numOfCommPage.value = res.data.page_num
            }
            commentList.value = res.data.commentListDto
            commentCnt.value = res.data.comment_num
          })
          .catch((err) => {
            console.error(err)
          })
    }
    const addComment = () => {
      if (comment.value.length < 1) {
        alert('댓글 내용을 입력하세요')
      } else {
        CommentAPI.commentWrite(props.boardID, props.postID, comment.value, null)
            .then((res) => {
              comment.value = ''
              getCommentsList(1)
              console.log(res)
            })
            .catch((err) => {
              alert('등록실패')
              console.error(err)
            })
      }
    }

    const addSubComment = (parentCommId) => {
      if (subComment.value.length < 1) {
        alert('댓글 내용을 입력하세요')
      } else {
        CommentAPI.commentWrite(props.boardID, props.postID, subComment.value, parentCommId)
            .then((res) => {
              subComment.value = ''
              getCommentsList(currCommPage.value)
              subCommId.value = -1
              console.log(res)
            })
            .catch((err) => {
              alert('등록실패')
              console.error(err)
            })
      }
    }

    const modifyComment = (commentId) => {
      if (newComment.value.length < 1) {
        alert('댓글 내용을 입력하세요')
      } else {
        CommentAPI.commentModify(props.boardID, props.postID, commentId, newComment.value)
            .then((res) => {
              newComment.value = ''
              getCommentsList(currCommPage.value)
              modifyId.value = -1
              console.log(res)
            })
            .catch((err) => {
              alert('등록실패')
              console.error(err)
            })
      }
    }

    const deleteComment = (commentId) => {
      Swal.fire({
        title: '댓글을 삭제하시겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '삭제',
        cancelButtonText: '취소',
      }).then((result) => {
        if (result.isConfirmed) {
          CommentAPI.commentDelete(props.boardID, props.postID, commentId).then(() => {
            getCommentsList(currCommPage.value)
          }).catch((err) => {
            console.error(err)
          })
        }
      })
    }

    const resize = (evt) => {
      evt.target.style.height = '1px'
      evt.target.style.height = (12 + evt.target.scrollHeight) + 'px'
    }

    return {
      comment, addComment, resize, commentList, getCommentsList, currCommPage, numOfCommPage,
      modifyId, newComment, subCommId, subComment, nickname, addSubComment, modifyComment, deleteComment,
      commentCnt
    }
  }
}
</script>

<style lang="scss" scoped>
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

.text-link {
  text-decoration: none;
}

.text-link:hover {
  text-decoration: underline;
}

.dropdown-menu .dropdown-item {
  cursor: pointer;
}

.sub-comment-form {
  padding: 0 50px 0 0;
}
</style>