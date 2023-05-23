<template>
  <div class="modal" v-if="show">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h3 class="modal-title">이미지 추가</h3>
          <button type="button" class="btn-close" @click="closeModal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <input class="form-control" type="file" accept=".JPG, .jpg, .jpeg, ,JPEG, .gif, .png" @change="fileChange" ref="oldFile"/>
        </div>
        <div class="modal-footer">
          <button @click="closeModal" class="btn btn-outline-secondary">
            닫기
          </button>
          <button class="btn btn-success" @click="requestAuth" :disabled="!validImage">
            추가
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed } from 'vue'
import useAxios from '@/modules/axios'
import { axios } from '@bundled-es-modules/axios'

export default {
  setup(props, context) {
    const { axiosPost } = useAxios()
    const imageSrc = ref('')
    const show = ref(false)
    const oldFile = ref(null)
    const newFile = ref(null)
    const fileName = ref('')
    let imgUrl

    const validImage = computed(() => {
      return fileName.value.match(/\.(jpg|JPG|jpeg|JPEG|gif|png)$/) !== null
    })

    const showModal = () => {
      show.value = true
    }

    const closeModal = () => {
      imageSrc.value = ''
      show.value = false
      oldFile.value = null
    }

    const fileChange = () => {
      const blobURL = URL.createObjectURL(oldFile.value.files[0]);
      const img = new Image();
      img.src = blobURL;
      img.onload = function () {
        imageSizeChange(img,oldFile.value.files[0].size)
        let list = new DataTransfer();
        list.items.add(new File([dataURItoBlob(imgUrl)], oldFile.value.files[0].name,{
          type: oldFile.value.files[0].type,
        }));
        newFile.value = list.files
      }
      fileName.value = oldFile.value.files[0].name
    }

    const imageSizeChange = (image,size) => {
      let canvas = document.createElement("canvas"),
          max_size = 600,
          width = image.width,
          height = image.height;
      if (width > height) {
        // 가로가 길 경우
        if (width > max_size) {
          height *= max_size / width;
          width = max_size;
        }
      } else {
        // 세로가 길 경우
        if (height > max_size) {
          width *= max_size / height;
          height = max_size;
        }
      }
      canvas.width = width;
      canvas.height = height;
      canvas.getContext("2d").drawImage(image, 0, 0, width, height);
      if(size>3*1024*1024){
        imgUrl = canvas.toDataURL("image/jpeg", 0.5);
      }
      else{
        imgUrl = canvas.toDataURL("image/jpeg", 1);
      }
    }

    function dataURItoBlob(dataURI) {
      let byteString = atob(dataURI.split(',')[1]);
      let mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
      let ab = new ArrayBuffer(byteString.length);
      let ia = new Uint8Array(ab);
      for (let i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
      }
      return new Blob([ab], {type: mimeString});
    }

    const filePath = ref('')

    const requestAuth = () => { // 이미지 업로드 권한 취득
      axiosPost('http://be.algo.r-e.kr:8088/api/v1/posts/images', {
        file_name: fileName.value,
        image_request_type: "POST",
      }, (response) => {
        filePath.value = response.data.substring(response.data.indexOf('post_image') + 11, response.data.indexOf('?'))  // 이미지이름.확장자
        uploadImage(response.data)
      }, (err) => {
        console.error(err)
      })
    }

    const uploadImage = (awsURL) => { // 백엔드 서버에 URL 요청
      axios.put(awsURL, newFile.value[0], {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Access-Control-Allow-Origin': '*',
        },
        withCredentials: true,
      }).then((res) => {
        insertImage(filePath.value)
      }).catch((err) => {
        console.error(err)
      })
    }

    const insertImage = (imageUrl) => { // 이미지 글쓰기 본문에 삽입
      axiosPost('http://be.algo.r-e.kr:8088/api/v1/posts/images', {
        file_name: imageUrl,  // 이미지이름.확장자
        image_request_type: "GET",
      }, (res) => {
        imageSrc.value = res.data.substring(0, res.data.indexOf('?'))
        context.emit('onConfirm', {
          src: imageSrc.value
        })
        closeModal()
      }, (err) => {
        console.error(err)
      })
    }

    return {
      imageSrc,
      show,
      oldFile,
      validImage,
      showModal,
      closeModal,
      fileChange,
      uploadImage,
      requestAuth,
    }
  },
}
</script>

<style scoped>
.modal {
  display: flex;
  align-items: center;
  justify-content: center;
  position: fixed;
  background-color: rgba(0, 0, 0, 0.5);
}
</style>