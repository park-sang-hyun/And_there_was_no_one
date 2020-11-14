<template>
    <!-- 게임 로딩 화면 (자유그리기 모드) -->
    <div id="LoadingModeOne">
        <div class=flexContainer>
            <button class="button" style="margin-left:100%" @click="closeRule(0)">X</button>
        </div>
        
        
        <!-- 중앙 정렬위한 빈 공간 -->
        <div id="floater"></div>
        
        <!-- 스토리 -->
        <div class="main__story">
            
        </div>
        
        <!-- 매트릭스 배경 -->
        <canvas id="c"></canvas>
    </div>
</template>

<script>
import http from "../../util/http-lobby.js";

export default {
    name: "ModeOne",

    data: () =>{
        return {
            userList:[
                {no: 0, nickname: 0, playcount: 0, wincount : 0,  rank: "F", score:10},
            ]
        }
    },    
    created() {
        console.log("sdfasjdkfhxc kjsdhzxcusehdiwejef");
        // 보이는 화면 크기 확인
        window.addEventListener('resize', this.screenResize);
        this.screenResize();

        this.getRank();
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
        },

        getRank(){
            http
            .get("room/list/"+this.pageNow)
            .then((res) => {

            for(let i=0; i<8; i++){
                this.roomList[i].no = res.data[i].id;
                this.roomList[i].roomname = res.data[i].title;
                this.roomList[i].mode = res.data[i].mode;
                this.roomList[i].cur_people = res.data[i].cur_count;
                this.roomList[i].max_people = res.data[i].max_count;
                this.roomList[i].difficulty = res.data[i].difficulty;
                this.roomList[i].start = res.data[i].start;
            }
            })
            .catch(err => {
            console.log(err)
            })
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
    left: 0;
    top: 0;
    width: var(--widthSize);
    height: var(--heightSize);
    min-width:1024px;
    background: repeating-linear-gradient(-45deg, rgb(33, 33, 33), rgb(33, 33, 33) 1px, rgb(10, 10, 10) 0, rgb(10, 10, 10) 10px);
    background: url('../../assets/images/background.jpg') no-repeat center center fixed;
      -webkit-background-size: cover;
      -moz-background-size: cover;
      -o-background-size: cover;
      background-size: cover;
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