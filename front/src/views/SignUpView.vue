<template>
<div class="main">
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
const profile = ref()


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
.main{
    
}
.container {
  max-width: 600px;
  margin: 0 auto;
  
}
</style>
