<template>
  <header class="p-3 border-bottom">
    <div class="container">
      <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
        <a href="/" class="d-flex align-items-center mb-3 mb-lg-0 text-dark text-decoration-none">
         <img src="/public/ALGo_Logo.ico" alt="ALGo Logo" width="40"/>
        </a>

        <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
          <li><a href="/" class="nav-link px-2 link-secondary active">ALGo</a></li>
          <li><a href="/foods" class="nav-link px-2 link-dark">식품정보</a></li>
          <li><a href="/recipes" class="nav-link px-2 link-dark">레시피</a></li>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle link-dark" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              커뮤니티
            </a>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" href="#">Action</a></li>
              <li><a class="dropdown-item" href="#">Another action</a></li>
              <li><hr class="dropdown-divider"></li>
              <li><a class="dropdown-item" href="#">Something else here</a></li>
            </ul>
          </li>
        </ul>

<!--        Todo: 로그인 전-->
        <div>
          <router-link to="/login" class="btn btn-primary me-2">로그인</router-link>
          <router-link to="/signup" class="btn btn-primary">회원가입</router-link>
        </div>

<!--        <div class="dropdown text-end">-->
<!--          <a href="#" class="d-block link-dark text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">-->
<!--            <img src="https://github.com/mdo.png" alt="mdo" width="32" height="32" class="rounded-circle">-->
<!--          </a>-->
<!--          <ul class="dropdown-menu text-small">-->
<!--            <li><a class="dropdown-item" href="#">New project...</a></li>-->
<!--            <li><a class="dropdown-item" href="#">Settings</a></li>-->
<!--            <li><a class="dropdown-item" href="#">Profile</a></li>-->
<!--            <li><hr class="dropdown-divider"></li>-->
<!--            <li><a class="dropdown-item" href="#">Sign out</a></li>-->
<!--          </ul>-->
<!--        </div>-->
      </div>
    </div>
  </header>
</template>

<script>
export default {

}
</script>

<style scoped>

</style>