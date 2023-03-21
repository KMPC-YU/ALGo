<template>
  <div>
    <carousel></carousel>
    <div class="container">
      <h2>식품정보 TOP 10</h2>
      <hr>
      <div class="row">
        <div class="col">
          <food-cards></food-cards>
        </div>
        <div class="col">
          <food-cards></food-cards>
        </div>
        <div class="col">
          <food-cards></food-cards>
        </div>
        <div class="col">
          <food-cards></food-cards>
        </div>
        <div class="col">
          <food-cards></food-cards>
        </div>
      </div>
      <recipe-card></recipe-card>
    </div>
  </div>
</template>

<script>
import Carousel from "@compo/MainComponents/Carousel.vue";
import FoodCards from "@compo/MainComponents/FoodCard.vue";
import RecipeCard from "@compo/MainComponents/RecipeCard.vue";

export default {
  components: {
    Carousel,
    FoodCards,
    RecipeCard,
  }
}
</script>

<style scoped>

</style>