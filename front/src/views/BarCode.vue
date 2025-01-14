<template>
  <div>
    <span>바코드 !!</span>
    <br>
    <el-image :src="barcodeImg" alt="Barcode" style="width: 200px; height: auto;" />
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import axios from 'axios';

const barcode = ref('B-3123'); // 바코드 코드
const barcodeImg = ref<string>(''); // Base64로 변환된 바코드 이미지

// 바코드 읽기 API 호출 함수
const barCodeReadAPI = async () => {
  try {
    axios.post(`/api/barcode/read?code=${barcode.value}`)
        .then(response => {
          console.log('res ==', response.data);
          barcodeImg.value = `data:image/png;base64,${response.data.barcodeByteBase64}`;
        })
        .catch(error => {
          console.error('API 호출 중 오류:', error);
        });
  } catch (error) {
    console.error('API 호출 중 오류:', error);
  }
};

// 컴포넌트가 마운트되면 API 호출
onMounted(() => {
  barCodeReadAPI();
});
</script>

<style scoped>
/* 추가적인 스타일 정의 가능 */
</style>
