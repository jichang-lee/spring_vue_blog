<template>
  <div class="container mt-5">
    <h2 class="mb-3">메인페이지-1</h2>
    <ul class="list-group">
      <li v-for="item in itemList" :key="item.id" class="list-group-item">
        {{ item.title }}
      </li>
    </ul>
    <nav aria-label="Page navigation example" class="mt-4">
      <ul class="pagination justify-content-center">
        <li class="page-item" :class="{ 'disabled': currentPage === 1 }">
          <a class="page-link" href="#" @click="goToPage(currentPage - 1)">이전</a>
        </li>
        <li class="page-item" v-for="page in totalPages" :key="page" :class="{ 'active': page === currentPage }">
          <a class="page-link" href="#" @click="goToPage(page)">{{ page }}</a>
        </li>
        <li class="page-item" :class="{ 'disabled': currentPage === totalPages }">
          <a class="page-link" href="#" @click="goToPage(currentPage + 1)">다음</a>
        </li>
      </ul>
    </nav>
  </div>
</template>


<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';

const itemList = ref([]);
const currentPage = ref(0);
const totalPages = ref(0);
const pageSize = 5;  // 페이지당 데이터 개수

// 페이지 이동 처리
const goToPage = async (page: number) => {
  if (page < 1 || page > totalPages.value) return;
  currentPage.value = page;
  await fetchData();
};

// 데이터를 가져오는 비동기 함수
const fetchData = async () => {
  try {
    const response = await axios.get(`/api/post/list?page=${currentPage.value}&size=${pageSize}`);
    console.log('response=',response)
    itemList.value = response.data;
    totalPages.value = response.headers['x-total-pages'];
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

// 페이지가 로드될 때 초기 데이터를 가져오도록 설정
onMounted(() => {
  fetchData();
});
</script>



<style scoped lang="scss">
.container {
  max-width: 800px;
  margin: 40px auto;
  padding: 20px;
  background: #f8f9fa;  // 연한 회색 배경
  border-radius: 8px;   // 테두리 둥글게
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1); // 그림자 효과
}

.pagination .page-item.disabled .page-link,
.pagination .page-item .page-link {
  cursor: pointer;
}

.pagination .page-item.active .page-link {
  background-color: #007bff; // 활성 페이지 색상 변경
  border-color: #007bff;
}
</style>

