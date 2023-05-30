<template>
  <div class="container py-5">
    <h1>게시판 생성</h1>
    <select class="form-select" v-model="type">
      <option selected="">게시판 타입</option>
      <option value="NOTICE">공지게시판</option>
      <option value="GENERAL">일반게시판</option>
      <option value="ANONYMOUS">익명게시판</option>
      <option value="QUESTION ">질문게시판</option>
    </select>
    <input class="form-control" type="text" placeholder="게시판 이름" v-model="name">
    <button class="btn btn-primary" @click="createBoard">추가</button>
    <p>생성할 게시판 타입은 {{ type }} 이고, 게시판 이름은 {{ name }} 입니다.</p>
  </div>
</template>

<script>
import { ref } from 'vue'
import * as AdminAPI from '@/services/admin.js'
import Swal from "sweetalert2";
export default {
  setup() {
    const type = ref('')
    const name = ref('')

    const createBoard = () => {
      if (type.value === "") {
        alert('게시판 타입을 선택하세요')
      } else if (name.value === "") {
        alert('게시판 이름을 입력하세요')
      } else {
        AdminAPI.createBoard({
          board_type : type.value,
          board_name : name.value,
        }).then((res) => {
          console.log(res)
          Swal.fire({
            title: '게시판 생성 완료',
            icon: 'success',
            showConfirmButton: false,
            timer: 1500,
          })
        }).catch((err) => {
          console.error(err)
          Swal.fire({
            title: '게시판 생성 실패',
            icon: 'error',
            showConfirmButton: false,
            timer: 1500,
          })
        })
      }
    }

    return {
      type, name, createBoard
    }
  }
}
</script>

<style scoped>

</style>