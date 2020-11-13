<template>
    <div id="wrap">
        <div id="c"></div>
        <div class="text">
            <span></span>
            <span>그</span>
            <span>리</span>
            <span>고 아</span>
            <span>&nbsp;</span>
            <span>&nbsp;</span>
            <span>무</span>
            <span>도 없</span>
            <span>&nbsp;</span>
            <span>&nbsp;</span>
            <span>었</span>
            <span>다</span>
            <span></span>
        </div>
    </div>
  
</template>

<script>
export default {
    data() {
        return {
            window: {
                width: 0,
                height: 0,
            },
        }
    },

    created() {
        // 보이는 화면 크기 확인
        window.addEventListener('resize', this.screenResize);
        this.screenResize();
    },

    // computed: {
    //     windowScreen() {
    //         return this.window
    //     },
    // },

    watch: {
        // 보이는 화면 크기 변화 감지
        window() {
            window.removeEventListener('resize', this.screenResize);
        },
    },

    methods: {
        // 현재 보이는 화면 크기 계산
        screenResize() {
            this.window.width = window.innerWidth;
            this.window.height = window.innerHeight;
            this.layoutCal();
        },

        // 넓이 계산해서 style 안에 계산해서 넣어주기
        layoutCal() {
            let suffix = 'px';
            document.documentElement.style.setProperty('--widthSize', this.window.width + suffix);
            document.documentElement.style.setProperty('--heightSize', this.window.height + suffix);
        },

    }
}
</script>

<style lang="scss" scoped>
    /* scoped 적용하면 html, body에 있는게 안됨 그래서 app.vue에 임시로 해둠 테스트용*/
    @import url('https://fonts.googleapis.com/css2?family=East+Sea+Dokdo&display=swap');
    * {
    margin: 0;
    padding: 0;
    outline: 0;
    }

    :root {
        --widthSize: 1200px;
        --heightSize: 900px;
    }

    #wrap {
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

    span {
        font-size: 100px;
        display: inline-block;
        text-shadow: 0 0 0 whitesmoke;
        -webkit-animation: smoky 5s 3s both;
                animation: smoky 5s 3s both;
    }

    .text{
        position: absolute;
        top: 40%;
        left: 0;
        right: 0;
        bottom: 0;
        margin: auto;
        text-align: center;
        color: transparent;
        -webkit-backface-visibility: hidden;
                backface-visibility: hidden;
        font-family: 'East Sea Dokdo', cursive;
        /* overflow: hidden; */
    }

    span:nth-child(even) {
    -webkit-animation-name: smoky-mirror;
            animation-name: smoky-mirror;
    }

    @-webkit-keyframes smoky {
    60% {
        text-shadow: 0 0 40px whitesmoke;
    }
    to {
        -webkit-transform: translate3d(15rem, -8rem, 0) rotate(-40deg) skewX(70deg) scale(1.5);
                transform: translate3d(15rem, -8rem, 0) rotate(-40deg) skewX(70deg) scale(1.5);
        text-shadow: 0 0 20px whitesmoke;
        opacity: 0;
    }
    }
    @-webkit-keyframes smoky-mirror {
    60% {
        text-shadow: 0 0 40px whitesmoke;
    }
    to {
        -webkit-transform: translate3d(18rem, -8rem, 0) rotate(-40deg) skewX(-70deg) scale(2);
                transform: translate3d(18rem, -8rem, 0) rotate(-40deg) skewX(-70deg) scale(2);
        text-shadow: 0 0 20px whitesmoke;
        opacity: 0;
    }
    }
    @keyframes smoky-mirror {
    60% {
        text-shadow: 0 0 40px whitesmoke;
    }
    to {
        -webkit-transform: translate3d(18rem, -8rem, 0) rotate(-40deg) skewX(-70deg) scale(2);
                transform: translate3d(18rem, -8rem, 0) rotate(-40deg) skewX(-70deg) scale(2);
        text-shadow: 0 0 20px whitesmoke;
        opacity: 0;
    }
    }
    span:nth-of-type(1) {
    -webkit-animation-delay: 2.1s;
            animation-delay: 2.1s;
    }

    span:nth-of-type(2) {
    -webkit-animation-delay: 2.2s;
            animation-delay: 2.2s;
    }

    span:nth-of-type(3) {
    -webkit-animation-delay: 2.3s;
            animation-delay: 2.3s;
    }

    span:nth-of-type(4) {
    -webkit-animation-delay: 2.4s;
            animation-delay: 2.4s;
    }

    span:nth-of-type(5) {
    -webkit-animation-delay: 2.5s;
            animation-delay: 2.5s;
    }

    span:nth-of-type(6) {
    -webkit-animation-delay: 2.6s;
            animation-delay: 2.6s;
    }

    span:nth-of-type(7) {
    -webkit-animation-delay: 2.7s;
            animation-delay: 2.7s;
    }

    span:nth-of-type(8) {
    -webkit-animation-delay: 2.8s;
            animation-delay: 2.8s;
    }

    span:nth-of-type(9) {
    -webkit-animation-delay: 2.9s;
            animation-delay: 2.9s;
    }

    span:nth-of-type(10) {
    -webkit-animation-delay: 3s;
            animation-delay: 3s;
    }

    span:nth-of-type(11) {
    -webkit-animation-delay: 3.1s;
            animation-delay: 3.1s;
    }

    span:nth-of-type(12) {
    -webkit-animation-delay: 3.2s;
            animation-delay: 3.2s;
    }

    #c {
        width: 100%;
        height: 400px;  /* 이미지 세로 크기가 400px */
        background: url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/62476/smoke.png) no-repeat;
        -webkit-animation: 40s FOG infinite;
        background-size: cover;
        position: absolute;
        /* top: 35%; */
        bottom: 0px;
        left: 0;
        right: 0;
    }

    @keyframes FOG {
        0% {background-position:left;}
        30% {opacity:.5;}
        80% {opacity: .8;}
        100%{background-position:right; opacity:1;}
    }
    
    @-moz-keyframes FOG {
        0% {background-position:left;}
        30% {opacity:.5;}
        80% {opacity: .8;}
        100%{background-position:right; opacity:1;}
    }
    
    @-webkit-keyframes FOG {
        0% {background-position:left;}
        30% {opacity:.5;}
        80% {opacity: .8;}
        100%{background-position:right; opacity:1;}
    }



    /* <div class="dots">
    </div>

    @-webkit-keyframes dot-move {
        from {
            background-position: 0 0
        }
        to { 
            background-position: 0 -100px
        }
    }

    .dots {
        height: 250px;
        width: 100%;
        position:absolute;
        bottom: 0px;
        left: 0px;
        right: 0px;
        background-position: 0% 0%;
    
        -webkit-animation-name: dot-move;
        -webkit-animation-duration: 3s;
        -webkit-animation-timing-function: linear;
        -webkit-animation-iteration-count: 1; 
    
    background: url(data:image/png;base64,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); 

    } */
</style>








