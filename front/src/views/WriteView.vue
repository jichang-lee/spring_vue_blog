<template>
  <div>
    <el-input
        v-model="title"
        placeholder="제목을 입력해주세요"/>
  </div>

  <div class="mt-2">
    <el-input
        v-model="content"
        type="textarea"
        rows="15"/>
  </div>

  <!-- 파일 입력 필드 -->
  <div class="mt-2">
    <input type="file" @change="handleFileChange" multiple accept="image/*">
    <!-- 이미지 미리보기 -->
    <div v-if="imagePreviewUrl">
      <img :src="imagePreviewUrl" alt="Image preview" style="width: 300px; height: auto;"/>
    </div>
  </div>

  <div class="mt-2">
    <div class="d-flex justify-content-end">
      <el-button
          @click="write"
          type="primary"> 글 작성완료</el-button>
    </div>
  </div>
</template>


<script setup lang="ts">
import { ref } from "vue";
import axios from 'axios';
import { useRouter } from "vue-router";

const title = ref("")
const content = ref("")
const files = ref([]) // 파일 저장
const imagePreviewUrl = ref(null) // 이미지 미리보기 URL
const router = useRouter()

const handleFileChange = (event:any) => {
  files.value = event.target.files;
  if (files.value.length > 0) {

    const fileReader = new FileReader();

    fileReader.onload = (e:any) => {
      imagePreviewUrl.value = e.target.result;
    };
    fileReader.readAsDataURL(files.value[0]);
  }
};

const write = () => {
  const formData = new FormData();
  formData.append('postData', JSON.stringify({
    title: title.value,
    content: content.value
  }));

  for (let i = 0; i < files.value.length; i++) {
    formData.append('files', files.value[i]);
  }

  axios.post("/api/post/write", formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
      .then(() => {
        router.replace({ name: "home" }) // 성공 후 홈으로 이동
      })
      .catch((error) => {
        console.error("Error uploading post and files:", error);
      });
}
</script>


<style>

</style>