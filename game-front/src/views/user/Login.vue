<template>
  <div id="backGrd">
    <transition v-if="goLobby" name="fade" appear>
      <enterLobby style="z-index: 1;"/>
    </transition>
    <div v-else class="container">
      <form>
        <h1>Login page</h1>
        <div class="form-item">
          <!-- 아래는 아이콘을 출력하는 부분인데 지금은 출력 안돼서 주석처리함 -->
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
          <div class="wrap">
            <router-link to="/signup/" class="btn--text">Signup</router-link>
          </div>
        </div>
      </form>



      <!-- <div class="popups-cont">
        <div class="popups-cont__overlay" @click="closeHandler"></div>
        <div class="popup">
          <div class="popup__pieces"></div>
          <div class="popup__content">
            <div class="popup__close" @click="closeHandler"></div>
            <h3 class="popup__heading">Shattered Popup</h3>
            <p class="popup__text">Works with any popup width and height (auto). Whole animation and triangulation made purely with css (SCSS). Javascript used only to trigger classes (I could do it css only with checkbox, but I'm tired of this crap). Requires browser with clip-path: polygon support.</p>
          </div>
        </div>
      </div>

      <button type="button" class="popup-btn">Show Popup</button>  -->

    </div>
  </div>
</template>

<script>
import PV from "password-validator";
import enterLobby from './enterLobby.vue'

import http from "../../util/http-common.js";

const storage = window.sessionStorage;

export default {
  components: {
    enterLobby,
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

      goLobby: false,
    };
  },
  created() {

    // 화면 크기 확인
    window.addEventListener('resize', this.screenResize);
    this.screenResize();

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

  watch: {
        // 보이는 화면 크기 변화 감지
        window() {
            window.removeEventListener('resize', this.screenResize);
        },
  },

  methods: {
    // 현재 보이는 화면 크기 계산
    screenResize() {
        var width = (window.innerWidth < 1024) ? 1024 : window.innerWidth;
        var height = window.innerHeight;
        let suffix = 'px';
        document.documentElement.style.setProperty('--widthSize', width + suffix);
        document.documentElement.style.setProperty('--heightSize', height + suffix);
    },

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
            storage.setItem("token", res.data.key)
            storage.setItem("id", res.data.user)
            console.log(storage)
          }
          this.moveFeed();
        })
        .catch((err) => {
          this.error.password = "로그인 정보가 일치하지 않습니다. 다시 입력하세요.";
        })
      
    }
  },

  lobbyNext() {
    this.$router.push("/lobby");
  },

  moveFeed(){
    this.goLobby = true;
    var go = setTimeout( this.lobbyNext , 6000);
  },



},

};




</script>
<style lang="scss" scoped>
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

  :root {
    --widthSize: 400px;
    --heightSize: 400px;
  }

  body {
    /* background: black;
    font: 16px "Helvetica Neue";
    font-weight: 200;
    letter-spacing: 1.5px; */
  }

  #backGrd{
    background: black;
    width: var(--widthSize);
    height: var(--heightSize);
    padding-top: 18%;
    transform:translateY(1em);
    // animation stuff
    animation:page-in ease-out 3s;
    animation-fill-mode:forwards;
  }

  .container {

    width: 360px;
    background-color: #fff;
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

  *, *:before, *:after {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}


$pieceAT: 0.7s;
$contentFadeAT: 0.2s;

@function map-set($map, $key, $value) {
  $new: ($key: $value);
  @return map-merge($map, $new);
}

@mixin shatteredPopup($inRow: 6, $inCol: 6) {
  $pieces: $inRow * $inCol;
  $heights: ();
  $hTotal: 100;

  @for $i from 1 through $inCol {
    $h: 100 / $inCol;
    $hVal: $h * 0.7 + floor(random($h*0.6));

    @if $i == $inCol {
      $hVal: $hTotal;
    }

    $hTotal: $hTotal - $hVal;
    $heights: map-set($heights, $i, $hVal);
    $widths: ();

    $wTotal: 100;

    @for $j from 1 through $inRow {
      $w: 100 / $inRow;
      $wVal: $w * 0.7 + floor(random($w*0.6));

      @if $j == $inRow {
        $wVal: $wTotal;
      }

      $wTotal: $wTotal - $wVal;
      $widths: map-set($widths, $j, $wVal);
    }

    $heights: map-set($heights, 'widths-#{$i}', $widths);
  }

  $k: 4;

  @for $i from 1 through $pieces {
    .popup__piece:nth-child(#{$i}) {
      $indexH: ceil($i / $inCol);
      $pieceH: map-get($heights, $indexH);
      height: percentage($pieceH / 100);

      $indexW: $i % $inRow + 1;
      $pieceW: map-get(map-get($heights, 'widths-#{$indexH}'), $indexW);

      width: percentage($pieceW / 100);

      $pieceX: percentage((30 + random(40)) / 100);

      @for $j from 1 through 3 {
        .popup__piece-inner:nth-child(#{$j}) {
          $x: (-60 + random(120)) * 1vw;
          $y: (-60 + random(120)) * 1vh;
          $z: (900 - random(1800)) * 1px;
          $rtX: 120deg + random(360deg);
          $rtY: 120deg + random(360deg);
          
          transform: translate3d($x, $y, $z) rotateX($rtX) rotateY($rtY);
          
          @if $j == 1 {
            clip-path: polygon(0 0, 0 100%, $pieceX 100%);
          }
          
          @if $j == 2 {
            clip-path: polygon(0 0, $pieceX 100%, 100% 0);
          }
          
          @if $j == 3 {
            clip-path: polygon(100% 0, 100% 100%, $pieceX 100%);
          }
        }
      }
    }
    
    &.s--closed .popup__piece:nth-child(#{$i}) {
      $rndPieceY: (100 + random(50)) * 1vh;
      
      transform: translate3d(0,$rndPieceY,0);
      
      @for $j from 1 through 3 {
        .popup__piece-inner:nth-child(#{$j}) {
          $x: (-80 + random(160)) * 1vw;
          $z: (900 - random(1800)) * 1px;
          $rtX: 120deg + random(360deg);
          $rtY: 120deg + random(360deg);
          
          transform: translate3d($x, 0, 0) rotateX($rtX) rotateY($rtY);
        }
      }
    }
  }

}

.popups-cont {
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: -10;
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100vh;
  perspective: 1000px;
  pointer-events: none;
  transition: z-index 0s $contentFadeAT/2 + $pieceAT;
  
  &.s--popup-active {
    z-index: 1000;
    pointer-events: auto;
    transition: z-index 0s 0s;
  }
  
  &__overlay {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    //background: rgba(0,0,0,0.2);
    opacity: 0;
    transition: opacity $pieceAT/2;
    
    .popups-cont.s--popup-active & {
      opacity: 1;
      transition: opacity $pieceAT/2 $pieceAT/2;
    }
  }
}

.popup {
  z-index: 2;
  position: relative;
  width: 500px;
  height: 500px;
  transform-style: preserve-3d;
  @include shatteredPopup;

  &__pieces {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
  }

  &__piece {
    float: left;
    position: relative;
    transform-style: preserve-3d;
    transition: transform 0s 0s;
    
    .popup.s--closed & {
      transition: transform $pieceAT $contentFadeAT/2 cubic-bezier(.69,-0.47,.97,1.04);
    }

    &:after {
      content: "";
      display: table;
      clear: both;
    }

    &-inner {
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      background: rgba(0,0,0,0.8);
      opacity: 0;
      transition: opacity $pieceAT/2 * 0.8 $pieceAT/2 + $contentFadeAT ease-in, transform $pieceAT $contentFadeAT/2 ease-out;

      .popup.s--active & {
        transition: opacity $pieceAT/2, transform $pieceAT ease-in-out;
        transform: translate3d(0,0,0) rotateX(0) rotateY(0) !important;
        opacity: 1;
      }
      
    }
  }

  &__content {
    position: relative;
    min-height: 500px;
    padding: 30px;
    background: #000;
    color: #fff;
    transition: opacity $contentFadeAT;
    opacity: 0;

    .popup.s--active & {
      transition-delay: $pieceAT - $contentFadeAT/2;
      opacity: 1;
    }
  }
  
  &__close {
    position: absolute;
    right: 30px;
    top: 30px;
    width: 30px;
    height: 30px;
    cursor: pointer;
    
    &:before,
    &:after {
      content: '';
      position: absolute;
      left: 0;
      top: 14px;
      width: 100%;
      height: 2px;
      background: #fff;
    }
    
    &:before {
      transform: rotate(45deg);
    }
    
    &:after {
      transform: rotate(-45deg);
    }
  }
  
  &__heading {
    margin-bottom: 20px;
    font-size: 30px;
    letter-spacing: 1px;
    text-transform: uppercase;
  }
  
  &__text {
    font-size: 18px;
    line-height: 1.5;
  }

}

.popup-btn {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 200px;
  height: 50px;
  margin-left: -100px;
  margin-top: -25px;
  background: transparent;
  outline: none;
  font-size: 20px;
  text-transform: uppercase;
  font-weight: bold;
  color: #000;
  border: 2px solid #000;
  transition: all 0.3s;
  cursor: pointer;
  
  &:hover {
    background-color: #000;
    color: #fff;
    letter-spacing: 2px;
  }
}

</style>