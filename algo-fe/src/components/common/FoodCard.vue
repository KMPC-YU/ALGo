<template>
  <div class="card">
    <div class="image-box">
      <img :src=imgSrc class="card-img-top" :alt=imgAlt />
      <a href="#"><i class="bi bi-heart"></i></a>
    </div>
    <div class="card-body">
      <a href="#">
        <h5 class="card-title fw-bold text-center">{{ name }}</h5>
      </a>
      <div v-if="author" class="fw-bold text-center mb-2">
        {{ author }}
      </div>
      <div v-if="rating" class="rating mb-2 text-center">
        <i class="bi bi-star-fill text-warning"></i>
        <i class="bi bi-star-fill text-warning"></i>
        <i class="bi bi-star-fill text-warning"></i>
        <i class="bi bi-star-fill text-warning"></i>
        <i class="bi bi-star-fill text-warning"></i>
        <span class="m-1">(255)</span>
      </div>
      <div class="row justify-content-center">
        <div class="col-4">
          <i class="bi bi-eye"></i>
          {{ views }}
        </div>
        <div class="col-4">
          <i class="bi bi-heart-fill text-danger"></i>
          {{ likes }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    imgSrc: String,
    imgAlt: String,
    name: String,
    views: String,
    likes: String,
    rating: {
      type: Boolean,
      default: false,
    },
    author: {
      type: String,
      default: false,
    }
  }
}
</script>

<style scoped>
.card {
  transition: all 0.2s ease-in;
  box-shadow: 2px 6px 8px 0 rgba(22, 22, 26, 0.18);
  border: none;
  border-radius: 0;
}
.card:hover {
  transform: scale(1.02);
  box-shadow: 2px 6px 8px 0 rgba(22, 22, 26, 0.50);
}
.card .card-body a {
  color: black;
  text-decoration: none;
}
.image-box i {
  position: absolute;
  top: 15px;
  right: 15px;
  color: #fff;
  font-size: 25px;
}
.image-box {
  overflow: hidden;
}
.image-box img {
  transition:all .9s ease;
  width: 100%;
  height: 200px;
}
.image-box:hover img {
  opacity: 0.7;
  transform:scale(1.15);
}
</style>