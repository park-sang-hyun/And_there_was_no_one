<template>
    <div class="container">
      <form>
        <h1>Login page</h1>
        <div class="form-item">
          <!-- <label for="username"><span class="entypo-user"></span></label> -->
          <input
            v-model="username"
            type="text"
            id="username"
            placeholder="아이디를 입력하세요."
          />
        </div>
        
        <div class="form-item">
          <!-- <label for="password"><span class="entypo-lock"></span></label> -->
          <input
              v-model="password"
              type="password"
              v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
              id="password"
              @keyup="checkPasswordForm"
              @keyup.enter="onLogin"
              placeholder="비밀번호를 입력하세요."
            />
        </div>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
        <button
          type="button"
          class="button"
          @click="onLogin"
          :disabled="!isSubmit"
          :class="{disabled : !isSubmit}"
        >Login</button>
        
        <div class="add-option mt-4">
          <!-- <div class="wrap">
            <p>비밀번호를 잊으셨나요?</p>
            <router-link to="/find/password" class="btn--text">비밀번호 찾기</router-link>
          </div> -->
          <div class="wrap">
            <router-link to="/signup/" class="btn--text">Signup</router-link>
          </div>
        </div>
      </form>

    </div>
</template>

<script>
//import "../../components/css/user.scss";
import PV from "password-validator";
// import Logo from "../../components/user/Logo.vue";
import http from "../../util/http-common.js";

const storage = window.sessionStorage;

export default {
  components: {
    // Logo,
  },
  data: () => {
    return {
      username: "",
      password: "",
      passwordSchema: new PV(),
      error: {
        password: false
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
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      ){
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      } else {
        this.error.password = ""
      }
        
      
      this.isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) this.isSubmit = false;
      });
    },

    // 로그인 요청 - 토큰 받아서 local storage에 넣기
    onLogin() {
      if (this.isSubmit) {

        storage.setItem("token", "");
        storage.setItem("id", "");
        storage.setItem("alarmTab", 1);
        
        let msg = "";
        
        http
        .post("rest-auth/login/", {
          username : this.username,
          password : this.password,
        },
        )
        .then((res) => {
          console.log(res)
          console.log(res.data.key)
          console.log(res.data.user)
          if(res.status) {
            console.log("enter")
            msg = "로그인되었습니다.";
            storage.setItem("token", res.data.key)
            storage.setItem("id", res.data.user)
            console.log(storage)
          }
          alert(msg);
          this.moveFeed();
        })
        .catch((err) => {
          this.error.password = "로그인 정보가 일치하지 않습니다. 다시 입력하세요.";
        })
      
    }
  },

  moveFeed(){
        this.$router.push("/lobby");
  },
},
 
};
</script>
<style scoped>
  @import url(http://weloveiconfonts.com/api/?family=entypo);

  /* entypo */
  [class*="entypo-"]:before {
    font-family: 'entypo', sans-serif;
  }

  * { 
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
  }

  body {
    /* background: black;
    font: 16px "Helvetica Neue";
    font-weight: 200;
    letter-spacing: 1.5px; */
  }

  .container {
    width: 360px;
    background: ivory;
    background-color: #fff;
    margin: 40px auto auto;
    padding: 8px 0 20px 0;
    border-radius: 4px;
    -webkit-box-shadow:  1px 1px 2px 0px rgba(155, 155, 155, .75);      
    box-shadow:  1px 1px 2px 0px rgba(155, 155, 155, .75);
  }

  form {
    width: 80%;
    margin: auto;
  }

  .form-item {
    margin: 18px auto;
    border-bottom: 1px solid #000;
    overflow: hidden;
  }

  label {
    float: left;
    width: 20px;
    margin-right: 10px;
    text-align: center;
  }

  input {
    float: left;
    width: 89%;
    padding: 0 0 6px 0;
    background-color: #fff;
    color: #000;
    font: 16px "Helvetica Neue";
    font-weight: 200;
    letter-spacing: 1.5px;
    outline: none;
    border: none;
  }

  input::-webkit-input-placeholder {
    padding: 0;
    color: #999999;
    font-weight: 200;
  }
  input:-moz-placeholder {
    color: #cdcdcd;
    font-weight: 200;
  }
  input:-ms-input-placeholder {
    color: #cdcdcd;
    font-weight: 200;
  }

  .button {
    background-color: #fff;
    color: #000;
    width: 100%;
    margin: 30px auto auto;
    padding: 8px;
    border: 1px solid #000;
    border-radius: 4px;
    -webkit-transition: all 150ms cubic-bezier(0.420, 0.000, 1.000, 1.000); 
    -moz-transition: all 150ms cubic-bezier(0.420, 0.000, 1.000, 1.000); 
    -ms-transition: all 150ms cubic-bezier(0.420, 0.000, 1.000, 1.000); 
    -o-transition: all 150ms cubic-bezier(0.420, 0.000, 1.000, 1.000); 
    transition: all 150ms cubic-bezier(0.420, 0.000, 1.000, 1.000); /* ease-in */
  }

  .button:hover {
    background-color: #000;
    color: #fff;
  }

  .btn-title {
    margin: auto;
    text-align: center;
    text-transform: uppercase;
  }

  h1 {
    font-weight: 300;
  }

  a {
    text-decoration: none;
  }
</style>