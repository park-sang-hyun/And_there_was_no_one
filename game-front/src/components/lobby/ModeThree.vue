<template>
    <!-- 게임 로딩 화면 (No AI 모드) -->
    <div id="LoadingModeThree">
        <div class=flexContainer>
            <div>
                <button class="button" style="color: rgba(37, 37, 37, 0.788); background-color:white;" @click="closeRule(1)">1</button>
                <button class="button" style="color: rgba(37, 37, 37, 0.788); background-color:white;" @click="closeRule(2)">2</button>
                <button class="button" style="color: rgba(37, 37, 37, 0.788); background-color:white;" @click="closeRule(3)">3</button>
            </div>
            <button class="button" style="color: rgba(37, 37, 37, 0.788); background-color:white;" @click="closeRule(0)">X</button>
        </div>
        <!-- 중앙 정렬위한 빈 공간 -->
        <div id="floater"></div>

        <!-- 스토리 -->
        <div class="main__story">
            <h1>[공지] 지금 AI는 시스템 점검 중입니다.</h1>
            <p>지금 당신이 있는 방의 사람들은 모두 <span class="bold__part">용의자</span>로 의심받고 있습니다.</p>
            <strong style="margin: 40px; color: white;"><p>당신들 사이에 범인 Shadow가 숨어있습니다.</p></strong>
            <p class="bold__part" style="font-size: 1.8rem;">다른 사람들과 함께 Shadow를 찾아내 용의선상에서 벗어나세요.</p>
        </div>
        <div class="countdown"></div>
    </div>
</template>

<script>
export default {
    name: "ModeThree",

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

#LoadingModeThree {
    position: fixed;
    // 중앙정렬. 부모요소에서 50% 내린 다음 본인 크기 50%만큼 올림
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: var(--widthSize);
    height: var(--heightSize);
    min-width:1024px;
    background: repeating-linear-gradient(-45deg, rgb(33, 33, 33), rgb(33, 33, 33) 1px, rgb(10, 10, 10) 0, rgb(10, 10, 10) 10px);
    overflow: inherit; 
}


// 중앙 정렬 위한 floater
#floater {
    float: left; 
    height: 30%; 
    margin-bottom: -100px;
}

.flexContainer {
    padding: 30px 60px;
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
    color: rgb(76, 67, 202);
    margin: 50px auto;
    font-weight: bold;
}

.bold__part {
    // font-family: 'Averia Gruesa Libre', cursive, 'Averia Sans Libre', 'Open Sans', sans-serif;
    color: rgb(76, 67, 202);
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

</style>