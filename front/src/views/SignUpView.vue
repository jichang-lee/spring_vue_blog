<template>
    <div>
        <el-input 
        v-model="name"
        placeholder="이름을 입력해주세요"/>
    </div>
    <div>
        <el-input 
        v-model="email"
        placeholder="이메일을 입력해주세요"/>
    </div>
    <div>
        <el-input 
        v-model="password"
        placeholder="비밀번호을 입력해주세요"/>
    </div>
    
    <div class="mt-2">
        <div class="d-flex justify-content-end">
        <el-button 
        @click="write"
        type="primary"> 회원가입</el-button>
        </div>
    </div>
    

</template>

<script setup lang="ts">
import { ref } from "vue";
import axios from 'axios';
import { useRouter } from "vue-router";


const name = ref("")
const email = ref("")
const password = ref("")
const router = useRouter()


const write = ()=>{
    axios.post("/api/user/signup",{
     name: name.value,
     email : email.value,
     password : password.value   
    })
    // .then(()=>{
    //     router.replace({name:"home"}) //push 사용시 뒤로가기 할 때 작성form 다시나옴
    // })

}

</script>

<style>

</style>