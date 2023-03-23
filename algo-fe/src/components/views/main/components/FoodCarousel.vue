<template>
  <h3 class="font-weight-bold mb-2">식품 BEST 10</h3>
  <hr>
  <div id="foodCarousel" class="carousel slide">
    <div class="carousel-inner">
      <div class="carousel-item active">
        <div class="row row-cols-1 row-cols-md-5 g-4">
          <div class="col">
            <food-card
                :img-src="'http://th3.tmon.kr/thumbs/image/48c/025/b81/b8eaaaa7d_600x600_95_FIT.jpg'"
                :img-alt="'coke'"
                :name="'맛있는콜라'"
                :views="'15'"
                :likes="'200'">
            </food-card>
          </div>
          <div class="col">
            <food-card
                :img-src="'https://www.ccbk.co.kr/images/product/detail/product112.jpg'"
                :img-alt="'sprite'"
                :name="'맛있는스프라이트'"
                :views="'15'"
                :likes="'200'">
            </food-card>
          </div>
          <div class="col">
            <food-card
                :img-src="'https://sitem.ssgcdn.com/37/63/81/item/1000034816337_i1_1100.jpg'"
                :img-alt="'welchi'"
                :name="'맛있는웰치스'"
                :views="'15'"
                :likes="'200'">
            </food-card>
          </div>
          <div class="col">
            <food-card
                :img-src="'https://cdn.emetro.co.kr/data2/content/image/2018/11/15/0540/20181115000163.jpg'"
                :img-alt="'pokari'"
                :name="'맛있는포카리'"
                :views="'15'"
                :likes="'200'">
            </food-card>
          </div>
          <div class="col">
            <food-card
                :img-src="'http://image.auction.co.kr/itemimage/17/b0/8f/17b08f2a56.jpg'"
                :img-alt="'garbe'"
                :name="'맛있는갈배'"
                :views="'15'"
                :likes="'200'">
            </food-card>
          </div>
        </div>
      </div>
      <div class="carousel-item">
        <div class="row row-cols-1 row-cols-md-5 g-4">
          <div class="col">
            <food-card
                :img-src="'https://biz.chosun.com/resizer/LvSzlb2_EA03pYYKYsm1Rn6B4r0=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/QTAMTTPWHWRL3RWFW5YOJG3FUQ.jpg'"
                :img-alt="'pizza'"
                :name="'맛있는피자'"
                :views="'15'"
                :likes="'200'">
            </food-card>
          </div>
          <div class="col">
            <food-card
                :img-src="'https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2-1076x807.jpg'"
                :img-alt="'chicken'"
                :name="'맛있는치킨'"
                :views="'15'"
                :likes="'200'">
            </food-card>
          </div>
          <div class="col">
            <food-card
                :img-src="'http://th1.tmon.kr/thumbs/image/91a/c0c/72d/56a6b2501_700x700_95_FIT.jpg'"
                :img-alt="'bossam'"
                :name="'맛있는보쌈'"
                :views="'15'"
                :likes="'200'">
            </food-card>
          </div>
          <div class="col">
            <food-card
                :img-src="'https://doewxs707ovkc.cloudfront.net/v3/prod/image/item/mainpage/907/ad4474bef39c4167b84477eaa7a5052f20210708171733.'"
                :img-alt="'ddeuk'"
                :name="'맛있는떡볶이'"
                :views="'15'"
                :likes="'200'">
            </food-card>
          </div>
          <div class="col">
            <food-card
                :img-src="'https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000370/img/basic/a0000370_main.jpg?'"
                :img-alt="'chobap'"
                :name="'맛있는초밥'"
                :views="'15'"
                :likes="'200'">
            </food-card>
          </div>
        </div>
      </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#foodCarousel" data-bs-slide="prev">
      <i class="bi bi-chevron-left text-black"></i>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#foodCarousel" data-bs-slide="next">
      <i class="bi bi-chevron-right text-black"></i>
    </button>
  </div>
</template>

<script>
import FoodCard from "@compo/common/FoodCard.vue";
export default {
  components: {
    FoodCard,
  }
}
</script>

<style scoped>
.carousel-inner {
  padding: 1em;
}
.carousel-control-prev,
.carousel-control-next {
  background-color: #FFFFFF;
  width: 5vh;
  height: 5vh;
  border-radius: 50%;
  top: 50%;
  transform: translateY(-50%);
  opacity: 100%;
  box-shadow: 2px 6px 8px 0 rgba(22, 22, 26, 0.18);
}
</style>