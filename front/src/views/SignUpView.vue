<template>

<div class="main">
 
    </div>
  <div class="mx-auto max-w-lg space-y-6 container">
    <div class="space-y-2 text-center">
      <h1 class="text-3xl font-bold">Admin User Management</h1>
      <p class="text-zinc-500 dark:text-zinc-400">Enter user details to create a new user</p>
    </div>
    <div class="space-y-4">
      <el-form :model="form" label-position="top">
        <el-form-item label="Name">
          <el-input v-model="name"></el-input>
        </el-form-item>
        <el-form-item label="Email">
          <el-input v-model="email" type="email"></el-input>
        </el-form-item>
        <el-form-item label="Password">
          <el-input v-model="password" type="password"></el-input>
        </el-form-item>
        <!-- 
          <el-form-item label="Role">
            <el-select v-model="form.role" placeholder="Select a role">
              <el-option label="Role 1" value="role1"></el-option>
              <el-option label="Role 2" value="role2"></el-option>
            </el-select>
          </el-form-item>
        -->
        <el-form-item>
          <el-button type="primary" @click="submitForm">Create User</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
  
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from "vue-router";


const router = useRouter()

const name = ref()
const email = ref()
const password = ref()
const profile = ref();

const beforeAvatarUpload = (file) => {
    const isJPG = file.type === 'image/jpeg';
    const isPNG = file.type === 'image/png';
    const isLt2M = file.size / 1024 / 1024 < 2;

    if (!isJPG && !isPNG) {
        this.$message.error('Image must be JPG or PNG format!');
    }
    if (!isLt2M) {
        this.$message.error('Image size must be less than 2MB!');
    }

    return (isJPG || isPNG) && isLt2M;
};

const handleUploadSuccess = (response, file) => {
    if (response.code === 200) {
        profile.value = response.data.url; // Assuming the server returns the image URL
    } else {
        this.$message.error('Failed to upload profile image');
    }
};


const submitForm = () => {
    axios.post("/api/user/signup",{
        name : name.value,
        email : email.value,
        password : password.value
    }).then(()=>{
        router.replace({name:"home"})
    })
};
</script>

<style>
/* .main{
    display: flex;
    justify-content: center;
    align-items: center;
} */
.container {
  max-width: 600px;
  margin: 0 auto;
  /* height: 100vh;
  padding-top: 100px; */
}
.avatar {
    width: 100px;
    height: 100px;
    display: block;
}
.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    line-height: 100px;
    text-align: center;
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
}
</style>
