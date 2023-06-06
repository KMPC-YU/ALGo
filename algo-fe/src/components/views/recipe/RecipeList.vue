<template>
  <div class="container py-5">
    <div class="text-center">
      <img
        src="https://biz.chosun.com/resizer/LvSzlb2_EA03pYYKYsm1Rn6B4r0=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/QTAMTTPWHWRL3RWFW5YOJG3FUQ.jpg"
        class="rounded-circle m-2"
        height="70"
        width="70"
        alt=""
        loading="lazy"
      />
      <img
        src="https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2-1076x807.jpg"
        class="rounded-circle m-2"
        height="70"
        width="70"
        alt=""
        loading="lazy"
      />
      <img
        src="http://th1.tmon.kr/thumbs/image/91a/c0c/72d/56a6b2501_700x700_95_FIT.jpg"
        class="rounded-circle m-2"
        height="70"
        width="70"
        alt=""
        loading="lazy"
      />
      <img
        src="https://doewxs707ovkc.cloudfront.net/v3/prod/image/item/mainpage/907/ad4474bef39c4167b84477eaa7a5052f20210708171733."
        class="rounded-circle m-2"
        height="70"
        width="70"
        alt=""
        loading="lazy"
      />
      <img
        src="https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000370/img/basic/a0000370_main.jpg?"
        class="rounded-circle m-2"
        height="70"
        width="70"
        alt=""
        loading="lazy"
      />
      <img
        src="http://th3.tmon.kr/thumbs/image/48c/025/b81/b8eaaaa7d_600x600_95_FIT.jpg"
        class="rounded-circle m-2"
        height="70"
        width="70"
        alt=""
        loading="lazy"
      />
      <img
        src="https://www.ccbk.co.kr/images/product/detail/product112.jpg"
        class="rounded-circle m-2"
        height="70"
        width="70"
        alt=""
        loading="lazy"
      />
      <img
        src="https://sitem.ssgcdn.com/37/63/81/item/1000034816337_i1_1100.jpg"
        class="rounded-circle m-2"
        height="70"
        width="70"
        alt=""
        loading="lazy"
      />
      <img
        src="https://cdn.emetro.co.kr/data2/content/image/2018/11/15/0540/20181115000163.jpg"
        class="rounded-circle m-2"
        height="70"
        width="70"
        alt=""
        loading="lazy"
      />
      <img
        src="http://image.auction.co.kr/itemimage/17/b0/8f/17b08f2a56.jpg"
        class="rounded-circle m-2"
        height="70"
        width="70"
        alt=""
        loading="lazy"
      />
    </div>
    <hr>
    <div class="row justify-content-center mb-4">
      <div class="col-auto">
        <div class="d-none d-md-block">
          <select v-model="selectedSearch" class="form-select">
            <option value="">최신순</option>
            <option value="">추천순</option>
          </select>
        </div>
      </div>
      <div class="col-8 col-md-6 col-lg-5 col-xl-4">
        <div class="input-group">
          <input type="search" class="form-control" v-model="searchText" @keyup.enter="searchRecipe" placeholder="검색">
          <button class="btn btn-primary" @click="searchRecipe">
            <i class="bi bi-search"></i>
          </button>
        </div>
      </div>
    </div>
    <div class="row row-cols-1 row-cols-lg-4 row-cols-md-2 g-4  mx-5 px-5">
      <div class="col">
        <FoodCard
            :img-src="'https://biz.chosun.com/resizer/LvSzlb2_EA03pYYKYsm1Rn6B4r0=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/QTAMTTPWHWRL3RWFW5YOJG3FUQ.jpg'"
            :img-alt="'pizza'"
            :name="'맛있는피자'"
            :author="'Jisoo'"
            :views="'15'"
            :likes="'200'"
            :rating="true">
        </FoodCard>
      </div>
      <div class="col">
        <FoodCard
            :img-src="'https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2-1076x807.jpg'"
            :img-alt="'chicken'"
            :name="'맛있는치킨'"
            :author="'Jisoo'"
            :views="'15'"
            :likes="'200'"
            :rating="true">
        </FoodCard>
      </div>
      <div class="col">
        <FoodCard
            :img-src="'http://th1.tmon.kr/thumbs/image/91a/c0c/72d/56a6b2501_700x700_95_FIT.jpg'"
            :img-alt="'bossam'"
            :name="'맛있는보쌈'"
            :author="'Jisoo'"
            :views="'15'"
            :likes="'200'"
            :rating="true">
        </FoodCard>
      </div>
      <div class="col">
        <FoodCard
            :img-src="'https://doewxs707ovkc.cloudfront.net/v3/prod/image/item/mainpage/907/ad4474bef39c4167b84477eaa7a5052f20210708171733.'"
            :img-alt="'ddeuk'"
            :name="'맛있는떡볶이'"
            :author="'게스트1'"
            :views="'15'"
            :likes="'200'"
            :rating="true">
        </FoodCard>
      </div>
      <div class="col">
        <FoodCard
            :img-src="'https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000370/img/basic/a0000370_main.jpg?'"
            :img-alt="'chobap'"
            :name="'맛있는초밥'"
            :author="'Jisoo'"
            :views="'15'"
            :likes="'200'"
            :rating="true">
        </FoodCard>
      </div>
      <div class="col">
        <FoodCard
            :img-src="'http://th3.tmon.kr/thumbs/image/48c/025/b81/b8eaaaa7d_600x600_95_FIT.jpg'"
            :img-alt="'coke'"
            :name="'맛있는콜라'"
            :author="'게스트1'"
            :views="'15'"
            :likes="'200'"
            :rating="true">
        </FoodCard>
      </div>
      <div class="col">
        <FoodCard
            :img-src="'https://www.ccbk.co.kr/images/product/detail/product112.jpg'"
            :img-alt="'sprite'"
            :name="'맛있는스프라이트'"
            :author="'게스트1'"
            :views="'15'"
            :likes="'200'"
            :rating="true">
        </FoodCard>
      </div>
      <div class="col">
        <FoodCard
            :img-src="'https://sitem.ssgcdn.com/37/63/81/item/1000034816337_i1_1100.jpg'"
            :img-alt="'welchi'"
            :name="'맛있는웰치스'"
            :author="'게스트1'"
            :views="'15'"
            :likes="'200'"
            :rating="true">
        </FoodCard>
      </div>
    </div>
    <nav class="mt-4" aria-label="Recipe page navigation">
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
  </div>
</template>

<script>
import FoodCard from '@compo/common/FoodCard.vue'

export default {
  components: {
    FoodCard,
  },
  setup() {

  }
}
</script>

<style>

</style>