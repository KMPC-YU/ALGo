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
  }
}
</script>

<style scoped>
.card {
  -webkit-transition: all 0.2s ease-in;
  -moz-transition: all 0.2s ease-in;
  -ms-transition: all 0.2s ease-in;
  -o-transition: all 0.2s ease-in;
  transition: all 0.2s ease-in;
  /*cursor: pointer;*/
  box-shadow: 2px 6px 8px 0 rgba(22, 22, 26, 0.18);
  border: none;
  border-radius: 0;
}
.card:hover {
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
  -webkit-transition: all 0.1s;
  transition: all 0.1s;
}
.image-box {
  background: #ffffff;
  overflow: hidden;
  /*box-shadow: 0px 2px 15px rgba(0, 0, 0, 0.50);*/
  /*border-radius: 5px;*/
}
.image-box img {
  -webkit-transition:all .9s ease;
  -moz-transition:all .9s ease;
  -o-transition:all .9s ease;
  -ms-transition:all .9s ease;
  width: 100%;
  height: 200px;
}
.image-box:hover img {
  opacity: 0.7;
  -webkit-transform:scale(1.15);
  -moz-transform:scale(1.15);
  -ms-transform:scale(1.15);
  -o-transform:scale(1.15);
  transform:scale(1.15);
}
</style>