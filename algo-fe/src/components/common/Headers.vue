<template>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <!-- Container wrapper -->
        <div class="container">
            <!-- Navbar brand -->
            <a class="navbar-brand me-2" href="/">
                <img src="/ALGo_Logo.ico" alt="ALGo Logo" width="40"/>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>

            <!-- Collapsible wrapper -->
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <!-- Left links -->
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="#">식품정보</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">레시피</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            커뮤니티
                        </a>
                        <ul class="dropdown-menu">
                            <li><router-link class="dropdown-item" to="/boards/1">공지사항</router-link></li>
<!--                            <li><a class="dropdown-item" href="#">자유게시판</a></li>-->
<!--                            <li><a class="dropdown-item" href="#">익명게시판</a></li>-->
<!--                            <li><a class="dropdown-item" href="#">지식IN</a></li>-->
<!--                            <li><hr class="dropdown-divider"></li>-->
<!--                            <li><a class="dropdown-item" href="#">포인트</a></li>-->
                        </ul>
                    </li>
                </ul>
                <!-- Left links -->

                <div v-if="!isLoggedIn" class="d-flex align-items-center">
                    <router-link class="btn btn-light px-3 me-2" to="/login">
                        로그인
                    </router-link>
                    <router-link type="button" class="btn btn-light me-3" to="/signup">
                        회원가입
                    </router-link>
                </div>

                <div v-else class="d-flex align-items-center">
                    <!-- Icon -->
                    <a class="link-secondary me-4" href="#">
                        <i class="fa-solid fa-comments fa-2xl"></i>
                        <span class="position-absolute top-40 start-20 translate-middle badge rounded-pill bg-danger">
                            99+
                            <span class="visually-hidden">unread messages</span>
                        </span>
                    </a>

                    <!-- 아바타 -->
                    <div class="dropdown">
                        <a
                            class="link-secondary me-3 dropdown-toggle hidden-arrow"
                            href="#"
                            id="navbarDropdownMenuLink"
                            role="button"
                            data-bs-toggle="dropdown"
                            aria-expanded="false"
                        >
                            <img
                                src="https://mblogthumb-phinf.pstatic.net/MjAxOTA0MjNfMTkz/MDAxNTU2MDE2ODQzNzY1.XIP77rvqSgGZPbY7vp_vhX_hGVWm1VBOlVaGe4ovrOYg.fav5jNf3PsvyBU2qVvZMBpJmevXZWRZAF3fa6LY1_tYg.JPEG.exia9133/g.jpg?type=w800"
                                class="rounded-circle"
                                height="40"
                                alt="Black and White Portrait of a Man"
                                loading="lazy"
                            />
                        </a>
                        <ul
                            class="dropdown-menu dropdown-menu-end"
                            aria-labelledby="navbarDropdownMenuLink"
                        >
                            <li>
                                <a class="dropdown-item" href="#">프로필</a>
                            </li>
                            <li>
                                <button class="dropdown-item" @click="logout">로그아웃</button>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- Collapsible wrapper -->
        </div>
        <!-- Container wrapper -->
    </nav>
    <!-- Navbar -->
</template>

<script>
import { computed } from 'vue'
import { useStore } from 'vuex'

export default {
    setup() {
        const store = useStore()
        const isLoggedIn = computed(() => {
            return store.getters.isLoggedIn
        })

        const logout = () => {
            store.dispatch('logout')
        }

        return {
            isLoggedIn, logout,
        }
    }
}
</script>

<style lang="scss" scoped>

</style>