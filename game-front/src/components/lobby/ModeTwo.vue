<template>
    <!-- 게임 로딩 화면 (이어그리기 모드) -->
    <div id="LoadingModeTwo">

        <div class=flexContainer>
            <div>
                <button class="button" @click="closeRule(1)">1</button>
                <button class="button" @click="closeRule(2)">2</button>
                <button class="button" @click="closeRule(3)">3</button>
            </div>
            <button class="button" @click="closeRule(0)">X</button>
        </div>

        <!-- 안개 -->
        <div id="foglayer_01" class="fog">
            <div class="image01"></div>
            <div class="image02"></div>
        </div>
        <div id="foglayer_02" class="fog">
            <div class="image01"></div>
            <div class="image02"></div>
        </div>
        <div id="foglayer_03" class="fog">
            <div class="image01"></div>
            <div class="image02"></div>
        </div>
        
        <!-- 중앙 정렬위한 빈 공간 -->
        <div id="floater"></div>

        <!-- 스토리 -->
        <div class="main__story">
            <h1>[경고] 버그로 인해 AI가 폭주합니다.</h1>
            <p>개발자들 사이에 숨어있던 코드네임 <span class="bold__part">Shadow</span>가 AI에 버그를 심었습니다.</p>
            <strong style="margin: 40px; color: white;"><p>버그로 인해 Canvas가 초기화되지 않습니다.</p></strong>
            <p>Shadow는 현재 탐정들 사이에 숨어있습니다.</p>
            <p>다른 개발자들이 남긴 제시어를 이용해서 Shadow를 찾아내십시오.</p>
            <strong style="margin: 40px;"><p>AI가 제시어를 알아선 안됩니다.</p>
            <p style="color: white;">제시어를 알아낸 AI에 의해 사망할 수 있습니다.</p></strong>
            <p>Shadow는 현재 탐정들 사이에 숨어있습니다.</p>
            <p class="bold__part" style="font-size: 1.8rem;">Shadow를 찾아내고 AI를 피해 이 곳을 탈출하십시오.</p>
        </div>
        <div class="countdown"></div>
    </div>
</template>

<script>
export default {
    name: "ModeTwo",

    created() {
        // 보이는 화면 크기 확인
        window.addEventListener('resize', this.screenResize);
        this.screenResize();

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

        closeRule(page) {
            this.$emit('close', page);
        }


    },
}
</script>

<style lang="scss" scoped>
@import url('https://fonts.googleapis.com/css2?family=Averia+Gruesa+Libre&family=Averia+Sans+Libre&family=Open+Sans&display=swap');

:root {
    --widthSize: 400px;
    --heightSize: 400px;
}

#LoadingModeTwo {
    position: fixed;
    left: 0;
    top: 0;
    width: var(--widthSize);
    height: var(--heightSize);
    min-width:1024px;
    background: repeating-linear-gradient(-45deg, rgb(33, 33, 33), rgb(33, 33, 33) 1px, rgb(10, 10, 10) 0, rgb(10, 10, 10) 10px);
    overflow: inherit; 
}


// 중앙 정렬 위한 floater
#floater {
    float: left; 
    height: 20%; 
    margin-bottom: -120px;
}

.flexContainer {
    padding: 60px;
    width: 98%;
    display: flex; 
    justify-content:space-between;
}

.button {
    border: none;
    color: #FFF;
    background: #42b983;
    appearance: none;
    font: inherit;
    border-radius: .3em;
    cursor: pointer;
    padding: 5px 10px;
    margin: 10px 5px;
}

// story 부분
.main__story {
    clear: both; 
    position: sticky;
    color: rgb(165, 165, 165);
    font-size:20px;
    line-height:1.6em;
    font-family: 'Averia Gruesa Libre', cursive, 'Averia Sans Libre', 'Open Sans', sans-serif;
    // font-family:sans-serif;
    margin: 0;
    // attributes to accompany the animation
    opacity:0;
    position:relative;
    transform:translateY(1em);
    // animation stuff
    animation:page-in ease-out 3s;
    animation-fill-mode:forwards;
    text-align: center;
}

h1 {
    font-family: 'Averia Gruesa Libre', cursive, 'Averia Sans Libre', 'Open Sans', sans-serif;
    color: rgb(181, 12, 12);
    margin: 50px auto;
    font-weight: bold;
}

.bold__part {
    // font-family: 'Averia Gruesa Libre', cursive, 'Averia Sans Libre', 'Open Sans', sans-serif;
    color: rgb(181, 12, 12);
    font-weight: bold;
}

@keyframes page-in {
    from {
        transform:translateY(1em);
        opacity:0;
    }
    to {
        transform:translateY(0);
        opacity:1;
    }
}

/* ---------- Fog ---------- */
.fogwrapper {
  height: 100%;
  position: absolute;
  top: 0;
  width: 100%;
  -webkit-filter: blur(1px) grayscale(0.2) saturate(1.2) sepia(0.2);
  filter: blur(1px) grayscale(0.2) saturate(1.2) sepia(0.2);
}
#foglayer_01, #foglayer_02, #foglayer_03 {
  height: 100%;
  position: absolute;
  width: 200%;
}
#foglayer_01 .image01, #foglayer_01 .image02,
#foglayer_02 .image01, #foglayer_02 .image02,
#foglayer_03 .image01, #foglayer_03 .image02 {
  float: left;
  height: 100%;
  width: 50%;
}
#foglayer_01 {
  -webkit-animation: foglayer_01_opacity 10s linear infinite, foglayer_moveme 15s linear infinite;
  -moz-animation: foglayer_01_opacity 10s linear infinite, foglayer_moveme 15s linear infinite;
  animation: foglayer_01_opacity 10s linear infinite, foglayer_moveme 15s linear infinite;
}
#foglayer_02, #foglayer_03 {
  -webkit-animation: foglayer_02_opacity 21s linear infinite, foglayer_moveme 13s linear infinite;
  -moz-animation: foglayer_02_opacity 21s linear infinite, foglayer_moveme 13s linear infinite;
  animation: foglayer_02_opacity 21s linear infinite, foglayer_moveme 13s linear infinite;
}

/* ---------- Moving Fog ---------- */
/*
  'size: cover' || 'size: 100%'; results remain the same
  'attachment: scroll' can be added or removed; results remain the same
  'attachment: fixed' causing unexpected results in Chrome
  'repeat-x' || 'no-repeat'; results remain the same
*/ 
#foglayer_01 .image01, #foglayer_01 .image02 {
    background: url("https://raw.githubusercontent.com/danielstuart14/CSS_FOG_ANIMATION/master/fog1.png") center center/cover no-repeat transparent;
}
#foglayer_02 .image01, #foglayer_02 .image02,
#foglayer_03 .image01, #foglayer_03 .image02{
    background: url("https://raw.githubusercontent.com/danielstuart14/CSS_FOG_ANIMATION/master/fog2.png") center center/cover no-repeat transparent;
}

/* ---------- Keyframe Layer 1 ---------- */
@-webkit-keyframes foglayer_01_opacity {
    0% { opacity: .1; }
    22% { opacity: .5; }
    40% { opacity: .28; }
    58% { opacity: .4; }
    80% { opacity: .16; }
    100% { opacity: .1; }
}
@-moz-keyframes foglayer_01_opacity {
    0% { opacity: .1; }
    22% { opacity: .5; }
    40% { opacity: .28; }
    58% { opacity: .4; }
    80% { opacity: .16; }
    100% { opacity: .1; }
}
@-o-keyframes foglayer_01_opacity {
    0% { opacity: .1; }
    22% { opacity: .5; }
    40% { opacity: .28; }
    58% { opacity: .4; }
    80% { opacity: .16; }
    100% { opacity: .1; }
}
@keyframes foglayer_01_opacity {
    0% { opacity: .1; }
    22% { opacity: .5; }
    40% { opacity: .28; }
    58% { opacity: .4; }
    80% { opacity: .16; }
    100% { opacity: .1; }
}
/* ---------- Keyframe Layer 2 ---------- */
@-webkit-keyframes foglayer_02_opacity {
    0% { opacity: .5; }
    25% { opacity: .2; }
    50% { opacity: .1; }
    80% { opacity: .3; }
    100% { opacity: .5; }
}
@-moz-keyframes foglayer_02_opacity {
    0% { opacity: .5; }
    25% { opacity: .2; }
    50% { opacity: .1; }
    80% { opacity: .3; }
    100% { opacity: .5; }
}
@-o-keyframes foglayer_02_opacity {
    0% { opacity: .5; }
    25% { opacity: .2; }
    50% { opacity: .1; }
    80% { opacity: .3; }
    100% { opacity: .5; }
}
@keyframes foglayer_02_opacity {
    0% { opacity: .5; }
    25% { opacity: .2; }
    50% { opacity: .1; }
    80% { opacity: .3; }
    100% { opacity: .5; }
}
/* ---------- Keyframe Layer 3 ---------- */
@-webkit-keyframes foglayer_03_opacity {
    0% { opacity: .8 }
    27% { opacity: .2; }
    52% { opacity: .6; }
    68% { opacity: .3; }
    100% { opacity: .8; }
}
@-moz-keyframes foglayer_03_opacity {
    0% { opacity: .8 }
    27% { opacity: .2; }
    52% { opacity: .6; }
    68% { opacity: .3; }
    100% { opacity: .8; }
}
@-o-keyframes foglayer_03_opacity {
    0% { opacity: .8 }
    27% { opacity: .2; }
    52% { opacity: .6; }
    68% { opacity: .3; }
    100% { opacity: .8; }
}
@keyframes foglayer_03_opacity {
    0% { opacity: .8; }
    27% { opacity: .2; }
    52% { opacity: .6; }
    68% { opacity: .3; }
    100% { opacity: .8; }
}
/* ---------- Keyframe moveMe ---------- */
@-webkit-keyframes foglayer_moveme {
    0% { left: 0; }
    100% { left: -100%; }
}
@-moz-keyframes foglayer_moveme {
    0% { left: 0; }
    100% { left: -100%; }
}
@-o-keyframes foglayer_moveme {
    0% { left: 0; }
    100% { left: -100%; }
}
@keyframes foglayer_moveme {
    0% { left: 0; }
    100% { left: -100%; }
}

@media only screen
    and (min-width: 280px)
    and (max-width: 767px) {
        #foglayer_01 .image01, #foglayer_01 .image02,
        #foglayer_02 .image01, #foglayer_02 .image02,
        #foglayer_03 .image01, #foglayer_03 .image02 {
            width: 100%;
        }
    }
</style>