<template>
    <!-- 게임 로딩 화면 (자유그리기 모드) -->
    <div id="LoadingModeOne">
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
            <h1>[주의] 지금부터 당신은 AI와 연결됩니다.</h1>
            <p>당신들 중 하나는 제시어를 알지 못하는 Shadow로 임명됩니다.</p>
            <strong style="margin: 40px; color: white;"><p>Shadow를 찾으면 AI와의 연결을 해제할 수 있습니다.</p></strong>
            <p>Shadow는 당신들 사이에 숨어있습니다.</p>
            <p>제시어를 사용해서 Shadow를 찾아내세요.</p>
            <strong style="margin: 40px;"><p>AI가 제시어를 알게 되면 불이익이 있습니다.</p>
            <p style="color: white;">Shadow에게 Hint를 주지 않도록 조심하세요.</p></strong>
            <p>Shadow가 제시어를 알게되면 Shadow를 제외한 사람들이 패배합니다.</p>
            <p class="bold__part" style="font-size: 1.8rem;">동료들과 함께 Shadow를 찾아 접속을 해제하세요.</p>
        </div>
        
        <!-- 매트릭스 배경 -->
        <canvas id="c"></canvas>
    </div>
</template>

<script>
export default {
    name: "ModeOne",

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
@import url('https://fonts.googleapis.com/css2?family=Open+Sans&family=Oswald&display=swap');

:root {
    --widthSize: 400px;
    --heightSize: 400px;
}

#LoadingModeOne {
    position: fixed;
    // 중앙정렬. 부모요소에서 50% 내린 다음 본인 크기 50%만큼 올림
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: var(--widthSize);
    height: var(--heightSize);
    min-width:1024px;
    background: repeating-linear-gradient(-45deg, rgb(33, 33, 33), rgb(33, 33, 33) 1px, rgb(10, 10, 10) 0, rgb(10, 10, 10) 10px);
    background: black;
    overflow: inherit; 
}

// 중앙 정렬 위한 floater
#floater {
    float: left; 
    height: 20%; 
    margin-bottom: -140px;
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
    margin: auto;
    color: rgb(165, 165, 165);
    font-size:20px;
    line-height:1.6em;
    font-family: 'Open Sans', sans-serif, 'Oswald', sans-serif;
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
    font-family: sans-serif;
    color: rgb(54, 181, 12);
    font-weight: bold;
}

.bold__part {
    // font-family: 'Averia Gruesa Libre', cursive, 'Averia Sans Libre', 'Open Sans', sans-serif;
    color: rgb(54, 181, 12);
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


// 매트릭스 배경
canvas {
    display: block;
}

</style>