<template>
  <div>
    <Headers v-if="currentRoute !== '/admin'"></Headers>
    <div>
      <router-view></router-view>
    </div>
    <Footer></Footer>
  </div>
  <chat-bot class="chat-bot" v-if="chatbotVisible" @showChatbot="showChatbot"></chat-bot>
  <div class="btn-chat-bot" v-if="!chatbotVisible">
    <p @click="showChatbot">
      <img src="https://public-common-sdk.s3.ap-northeast-2.amazonaws.com/image/icon_chatbot_v2.png">
    </p>
  </div>
</template>

<script>
import Headers from "./components/common/Headers.vue";
import Footer from "./components/common/Footer.vue";
import router from "@/router/router.js";
import chatBot from "@views/ChatBot.vue";
import { ref, computed } from 'vue'
import ChatBot from "@views/ChatBot.vue";

export default {
  components: {
    ChatBot,
    Headers,
    Footer,
  },
  setup() {
    const currentRoute = computed(() => {
      return router.currentRoute.value.path
    })

    const chatbotVisible = ref(false)

    const showChatbot = () => {
      chatbotVisible.value = !chatbotVisible.value
    }

    return {
      currentRoute, chatbotVisible, showChatbot
    }
  }
}
</script>
<style>
.btn-chat-bot {
  box-sizing: border-box;
  position: fixed;
  bottom: 25px;
  right: 25px;
  z-index: 20;
  display: block;
  cursor: pointer;
}

.chat-bot {
  position: fixed;
  bottom: 25px;
  right: 25px;
  z-index: 20;
  display: block;
  cursor: pointer;
}
</style>