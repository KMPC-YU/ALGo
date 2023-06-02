<template>
  <div class="container py-5">
    <h2 class="mb-4">포인트 내역</h2>

    <!-- 포인트 목록 및 랭킹 테이블 -->
    <div class="row px-5">
      <div class="col-7">
        <div class="text-center fw-bold mb-2">사용 내역</div>
        <table class="table text-center">
          <thead class="table-dark">
            <th scope="col" class="col-md-3 d-none d-md-table-cell">포인트</th>
            <th scope="col" class="col-9 col-md-6">내용</th>
            <th scope="col" class="col-md-3 d-none d-md-table-cell">날짜</th>
          </thead>
          <tbody>
            <tr>
              <td class="d-none d-md-table-cell fw-bold text-primary">+ 10</td>
              <td>로그인 보상</td>
              <td class="d-none d-md-table-cell">2023-05-31 16:54</td>
            </tr>
            <tr>
              <td class="d-none d-md-table-cell fw-bold text-secondary">- 30</td>
              <td>지식IN 포인트 사용</td>
              <td class="d-none d-md-table-cell">2023-05-21 13:06</td>
            </tr>
          </tbody>
        </table>
        <!-- 사용 내역 페이지네이션 -->
        <nav aria-label="Point page navigation">
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
        <!-- 사용 내역 페이지네이션 -->
      </div>
      <div class="col-5">
        <div class="text-center fw-bold mb-2">랭킹 TOP 50</div>
        <table class="table text-center">
          <thead class="table-dark">
            <th scope="col" class="col-md-3 d-none d-md-table-cell">순위</th>
            <th scope="col" class="col-9 col-md-6">닉네임</th>
            <th scope="col" class="col-md-3 d-none d-md-table-cell">점수</th>
          </thead>
          <tbody>
            <tr>
              <td class="d-none d-md-table-cell fw-bold">1</td>
              <td>Player</td>
              <td class="d-none d-md-table-cell">1972</td>
            </tr>
            <tr>
              <td class="d-none d-md-table-cell fw-bold">2</td>
              <td>User1</td>
              <td class="d-none d-md-table-cell">1121</td>
            </tr>
          </tbody>
        </table>
        <!-- 랭킹 페이지네이션 -->
        <nav aria-label="Rank page navigation">
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
        <!-- 랭킹 페이지네이션 -->
      </div>
    </div>
    <!-- 포인트 목록 및 랭킹 테이블 -->
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>