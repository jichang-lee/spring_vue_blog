<template>
  <div class="login-container">
    <h1 class="text-3xl text-center font-bold mb-6">Login</h1>
    <el-form :model="form" label-position="top">
      <el-form-item label="Email">
        <el-input v-model="email" placeholder="Enter your email"></el-input>
      </el-form-item>
      <el-form-item label="Password">
        <el-input v-model="password" type="password" placeholder="Enter your password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleLogin">Login</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();
const email = ref();
const password = ref();
// const userId = ref(1);

const handleLogin = () => {
  // 로그인 처리 로직 구현 필요

  axios.post('/api/auth/login',{
    // userId : userId.value,
    email : email.value,
    password : password.value
  }).then(response => {

    const token = response.data;
    const accessToken = token.accessToken;
    const refreshToken = token.refreshToken;

    localStorage.setItem('token', accessToken);
    localStorage.setItem('refreshToken', refreshToken);

    console.log('Login successful:', response);
    router.push({ name: 'home' });
  }).catch(error => {
    console.error('Login failed:', error);
  });
};
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 100px auto;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
}

.el-form-item {
  margin-bottom: 24px;
}
</style>
