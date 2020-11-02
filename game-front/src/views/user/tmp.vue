<template>
    <div id="wrap">
        <canvas id="myCanvas"></canvas>
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

}
</script>

<style scoped>
    /* scoped 적용하면 html, body에 있는게 안됨 그래서 app.vue에 임시로 해둠 테스트용*/
    @import url('https://fonts.googleapis.com/css2?family=East+Sea+Dokdo&display=swap');
    * {
    margin: 0;
    padding: 0;
    outline: 0;
    }
    html, body {
    
    
    
    }

    #myCanvas {
    z-index: -1;
    }

    #wrap {
        background: black;
        width: 1200px;
        height: 900px;
        overflow: hidden;
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
    height: 600px;
    background: url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/62476/smoke.png) no-repeat;
    -webkit-animation: 40s FOG infinite;



    /* 아래 내용은 붙여넣음 원래 bottom 0px만 있었음 */
    position: absolute;
    top: 55%;
    left: 0;
    right: 0;
    bottom: 0;
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
</style>