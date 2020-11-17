<template>
  <div>
    <div class="popups__cont">
      <div class="popups__cont__overlay"></div>
        <div id="wrap">
          <div class="black">
              <div id="c"></div>
              <div class="text">
                <span class="text__span"></span>
                <span class="text__span">그</span>
                <span class="text__span">리</span>
                <span class="text__span">고 아</span>
                <span class="text__span">&nbsp;</span>
                <span class="text__span">&nbsp;</span>
                <span class="text__span">무</span>
                <span class="text__span">도 없</span>
                <span class="text__span">&nbsp;</span>
                <span class="text__span">&nbsp;</span>
                <span class="text__span">었</span>
                <span class="text__span">다</span>
                <span class="text__span"></span>
              </div>
          </div>
        </div>    

        <div class="pop__up">
          <div class="pop__up__pieces"></div>
          <div class="pop__up__content">
            <div class="pop__up__close" @click="closeHandler"></div>
            <Login v-if="isLogin" @login="login" />
            <Signup v-else @login="login" />
          </div>
        </div>
      </div>

    <button @click="clickImage" type="button" class="pop__up__btn">
      <div class="loginImgBox">
        <!-- <img class="profile" src="https://pbs.twimg.com/profile_images/1184827741884973057/V0F3blnl.jpg"> -->
        <img class="loginImg" src="../../assets/images/blink2.gif">
      </div>
    </button>
  </div>
</template>

<script>
import Login from "./Login.vue"
import Signup from "./Signup.vue"

var frag = document.createDocumentFragment();

export default {
    data() {
        return {
          isLogin: true,
        }
    },

    components: {
      Login,
      Signup
    },

    mounted() {
      for (var i = 1; i <= 36; i++) {
                var $piece = document.createElement('div');
                // console.log("before");
                // console.log($piece);
                $piece.classList.add('pop__up__piece');
                // console.log($piece);
                
                // 모달 안에 작은 조각상자들이 3조각으로 컷팅됨
                this.insertInnerPieces($piece, 3);
                frag.appendChild($piece);
            }

            // 깨짐효과 css추가 
            document.querySelector('.pop__up__pieces').appendChild(frag);
    },

    methods: {
        login(login) {
          this.isLogin = login;
        },

        insertInnerPieces($el, innerPieces) {
            for (var i = 0; i < innerPieces; i++) {
                var $inner = document.createElement('div');
                $inner.classList.add('pop__up__piece-inner');
                $el.appendChild($inner);
            }
        },

        clickImage() {
            this.buttonpush2();
            // numOfPieces 개수만큼 날라옴 6 * 6 조각
            // var frag = document.createDocumentFragment();
            for (var i = 1; i <= 36; i++) {
                var $piece = document.createElement('div');
                $piece.classList.add('pop__up__piece');
                
                // 모달 안에 작은 조각상자들이 3조각으로 컷팅됨
                this.insertInnerPieces($piece, 3);
                frag.appendChild($piece);
            }

            // 깨짐효과 css추가 
            document.querySelector('.pop__up__pieces').appendChild(frag);

            // .popups-cont, 이건 css에 정의된 class 이름
            var $popupsCont = document.querySelector('.popups__cont');
            var $popup = document.querySelector('.pop__up');

            $popupsCont.classList.add('s--pop__up-active');
            $popup.classList.add('s--active');
        },

        closeHandler() {
          this.buttonpush1();
          console.log("sdlkfjl")
            var $popupsCont = document.querySelector('.popups__cont');
            var $popup = document.querySelector('.pop__up');


            $popupsCont.classList.remove('s--pop__up-active');
            $popup.classList.remove('s--active');
            $popup.classList.add('s--closed');
            
            setTimeout(function() {
                $popup.classList.remove('s--closed');
            }, 900);
        },

         buttonpush1(){
          var bpush = new Audio(require('../../assets/musics/glass1.mp3'));
          bpush.play();
        },
        buttonpush2(){
          var bpush = new Audio(require('../../assets/musics/glass2.mp3'));
          bpush.play();
        },
    },

}
</script>

<style lang="scss">
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

@mixin shatteredpop__up($inRow: 6, $inCol: 6) {
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
    .pop__up__piece:nth-child(#{$i}) {
      $indexH: ceil($i / $inCol);
      $pieceH: map-get($heights, $indexH);
      height: percentage($pieceH / 100);

      $indexW: $i % $inRow + 1;
      $pieceW: map-get(map-get($heights, 'widths-#{$indexH}'), $indexW);

      width: percentage($pieceW / 100);

      $pieceX: percentage((30 + random(40)) / 100);

      @for $j from 1 through 3 {
        .pop__up__piece-inner:nth-child(#{$j}) {
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
    
    &.s--closed .pop__up__piece:nth-child(#{$i}) {
      $rndPieceY: (100 + random(50)) * 1vh;
      
      transform: translate3d(0,$rndPieceY,0);
      
      @for $j from 1 through 3 {
        .pop__up__piece-inner:nth-child(#{$j}) {
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

.popups__cont {
  background: black;
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
  
  &.s--pop__up-active {
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
    
    .popups__cont.s--pop__up-active & {
      opacity: 1;
      transition: opacity $pieceAT/2 $pieceAT/2;
    }
  }
}

.pop__up {
  z-index: 2;
  position: relative;
  margin-top:10%;
  width: 500px;
  height: 500px;
  transform-style: preserve-3d;
  @include shatteredpop__up;

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
    
    .pop__up.s--closed & {
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
      background: rgba(255,255,255,0.8);
      opacity: 0;
      transition: opacity $pieceAT/2 * 0.8 $pieceAT/2 + $contentFadeAT ease-in, transform $pieceAT $contentFadeAT/2 ease-out;

      .pop__up.s--active & {
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
    background: #fff;
    color: #000;
    transition: opacity $contentFadeAT;
    opacity: 0;

    .pop__up.s--active & {
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
      background: #000;
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

.pop__up__btn {
  position: absolute;
  left: 47%;
  top: 40%;
  // width: 200px;
  // height: 50px;
  margin-left: -100px;
  margin-top: -25px;
  background: transparent;
  // outline: none;
  font-size: 20px;
  text-transform: uppercase;
  font-weight: bold;
  color: #000;
  border: none;
  transition: all 0.3s;
  cursor: pointer;
  
  &:hover {
    // background-color: #000;
    color: #fff;
    letter-spacing: 2px;
  }
  
}
.loginImgBox {
  width: 300px;
  height: 300px; 
  border-radius: 70%;
  overflow: hidden;
  margin:auto;
  background: black;
  margin-top:10%;
}
.loginImg {
  position: absolute;
  left: -10px;
  width: 100%;
  height: 100%;
  object-fit: cover;
  background: black;
}

#wrap {
        position: absolute;
        top:0;
        left: 0;
        background: black;
        width: var(--widthSize);
        height: var(--heightSize);
        /* min-width: 1000px;
        min-height: 800px; */
        /* width: 1200px;
        height: 900px; */
        overflow: hidden;
        /* animation:page-in ease-out 3s;
        animation-fill-mode:forwards; */
}
     .text__span {
        font-size: 100px;
        display: inline-block;
        text-shadow: 0 0 0 whitesmoke;
    }

    .text{
        position: absolute;
        top: 5%;
        left: 0;
        right: 0;
        bottom: 0;
        margin: auto;
        text-align: center;
        color: transparent;
        font-family: 'East Sea Dokdo', cursive;
        /* overflow: hidden; */
    }

</style>