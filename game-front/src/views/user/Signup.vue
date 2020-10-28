<template>
  <div class="user" id="signup">
    <div class="wrapC">
      <Logo :component="component" />
      <h5>Signup page</h5>
      <div class="input-with-label">
        <input
          v-model="username"
          type="text"
          id="username"
          placeholder="아이디를 입력하세요."
        />
        <label for="username">아이디</label>
      </div>

      <!-- 닉네임 중복체크하기  -->
      <div class="input-with-label">
        <input
          v-model="nickname"
          type="text"
          id="nickname"
          placeholder="닉네임을 입력하세요."
        />
        <label for="nickname">닉네임</label>
      </div>

      <div class="input-with-label">
        <input
          v-model="password1"
          type="password1"
          v-bind:class="{error : error.password1, complete:!error.password1&&password1.length!==0}"
          id="password1"
          @keyup="checkPasswordForm"
          placeholder="비밀번호를 입력하세요."
        />
        <label for="password1">비밀번호</label>
        <div class="error-text" v-if="error.password1">{{error.password1}}</div>
      </div>
      <!-- 비밀번호 맞는지 체크하기  -->
      <div class="input-with-label">
        <input
          v-model="password2"
          type="password2"
          id="password2"
          @keyup.enter="onSignup"
          placeholder="비밀번호를 다시 입력하세요."
        />
        <label for="password2">비밀번호확인</label>
      </div>

      <button
        class="btn btn--back btn--signup"
        @click="onSignup"
        :disabled="!isSubmit"
        :class="{disabled : !isSubmit}"
      >회원가입</button>

    </div>
  </div>
</template>

<script>
//import "../../components/css/user.scss";
import PV from "password-validator";
import Logo from "../../components/user/Logo.vue";
import http from "../../util/http-common.js";

const storage = window.sessionStorage;

export default {
  components: {
    Logo,
  },
  data: () => {
    return {
      username: "",
      nickname: "",
      password1: "",
      password2: "",
      passwordSchema: new PV(),
      error: {
        password1: false
      },
      isSubmit: false,
    };
  },
  created() {
    if (storage.NickName || storage.User) this.$router.replace({name: 'Main'})
    this.component = this;

    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  methods: {
    setInfo(status, token, info) {
      this.status = status
      this.token = token
      this.info = info
    },
    checkPasswordForm() {
       if (
        this.password1.length >= 0 &&
        !this.passwordSchema.validate(this.password1)
      ){
        this.error.password1 = "영문,숫자 포함 8 자리이상이어야 합니다.";
      } else {
        this.error.password1 = ""
      }
        
      
      this.isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) this.isSubmit = false;
      });
    },

    // 로그인 요청 - 토큰 받아서 local storage에 넣기
    onSignup() {
      if (this.isSubmit) {

        storage.setItem("token", "");
        storage.setItem("id", "");
        storage.setItem("alarmTab", 1);
        
        let msg = "";
        
        http
        .post("rest-auth/registration/", {
          username : this.username,
          nickname : this.nickname,
          password1 : this.password1,
          password2 : this.password2,
        },
        )
        .then((res) => {
          console.log(res)
          console.log(res.data.key)
          console.log(res.data.user)
          if(res.status) {
            console.log("enter")
            msg = "회원가입되었습니다.";
            storage.setItem("token", res.data.key)
            storage.setItem("id", res.data.user)
            console.log(storage)
          }
          alert(msg);
          this.moveFeed();
        })
        .catch((err) => {
          this.error.password1 = err;
        })
      
    }
  },

  moveFeed(){
        this.$router.push("/lobby");
  },
},
 
};
</script>