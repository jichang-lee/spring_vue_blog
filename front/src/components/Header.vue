<template>
  <el-header :key="headerKey" class="header">
    <el-menu mode="horizontal" router>
      <el-menu-item index="/">Home</el-menu-item>
      <el-menu-item v-if="isLoggedIn" index="/write">글 작성</el-menu-item>
      <el-menu-item v-if="isLoggedIn">{{userName}}</el-menu-item>
      <el-menu-item v-if="isLoggedIn" @click="logout">logout</el-menu-item>
      <el-menu-item v-else @click="goToLogin">Login</el-menu-item>
    </el-menu>
  </el-header>
</template>

<script setup>
import axios from "axios";
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";

const userName = ref();
const router = useRouter();
const isLoggedIn = ref(false);
const headerKey = ref(0);


const checkAuth = async () => {
  const token = localStorage.getItem("token");
  const refreshToken = localStorage.getItem("refreshToken");
  console.log("token ={}",token)
  if (token) {
    try {
      const response = await axios.post(
          "/api/auth/userInfo",
          {}, // Body는 빈 객체
          {
            headers: {
              Authorization: `Bearer ${token}`,
              'Refresh-Token': refreshToken
            }
          }
      );
      userName.value = response.data.name;
      isLoggedIn.value = true;
      headerKey.value++;
    } catch (error) {
      console.log("userInfo Error", error)
      isLoggedIn.value = false;
    }
  } else {
    isLoggedIn.value = false;
  }
};

const goToLogin = () => {
  router.push({name : "login"})
}

const logout = async () => {
  try {
    await axios.post("/api/auth/logout");
    localStorage.removeItem("token");
    localStorage.removeItem("refreshToken");
    isLoggedIn.value = false;
    userName.value = null;
    headerKey.value++;
  router.push({name : "home"})
  }catch (error) {
    console.log("logout Error",error)
  }
}

onMounted(()=>{
  checkAuth();
})


</script>


<style>
.header {
  padding: 0;
  height: 60px;
}
</style>
