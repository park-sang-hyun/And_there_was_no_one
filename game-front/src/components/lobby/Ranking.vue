<template>
    <!-- 랭킹 페이지 -->
    <div id="LoadingModeRank">
        <div class=flexContainer>
            <h2 style="float:left; color:white; margin-left:8%">랭 킹</h2> 
            <button class="button" style="margin-left:80%; color: rgba(37, 37, 37, 0.788); background-color:white;" @click="closeRule(0)">X</button>
        </div>       

        <!-- 중앙 정렬위한 빈 공간 -->
        <div id="floater"></div>
        
        <!-- 스토리 -->
        <div class="main__story">
            <table style="margin-left:5%;">
                <tr>
                    <th style="font-size:20px; color:white; width:20%; text-align:center;">순 서 </th> 
                    <th style="font-size:20px; color:white; width:25%; text-align:center;">닉네임 </th>
                    <th style="font-size:20px; color:white; width:35%; text-align:center;">게임 수 ( Win / Lose ) </th> 
                    <th style="font-size:20px; color:white; width:15%; text-align:center;">랭 크</th>
                    <th style="font-size:20px; color:white; width:15%; text-align:center;">점 수</th>
               </tr>
                <tr v-for="(rank, index) in rankList" :key="(rank.no, index) + 'rankkey'">
                    <td v-if="rank.no !='none'" style="font-size:20px; color:white; text-align:center;">{{ rank.no }} </td> 
                    <td v-if="rank.no !='none'" style="font-size:20px; color:white; text-align:center;">{{ rank.nickname }} </td>
                    <td v-if="rank.no !='none'" style="font-size:20px; color:white; text-align:center;">{{ rank.playcount }} ( {{rank.wincount}} / {{(rank.playcount - rank.wincount)}} )</td> 
                    <td v-if="rank.no !='none'" style="font-size:20px; color:white; text-align:center;">{{ rank.rank }}</td>
                    <td v-if="rank.no !='none'" style="font-size:20px; color:white; text-align:center;">{{ rank.score }}</td>
                    <td v-else style="height:49px; color:white;"></td>
                </tr>
            </table>

        <!-- 페이지네이션 -->
        <ul class="roompagination" id="menu">
            <li class="roompage-item"><a class="roompage-link" @click="prevPage">Prev</a></li>
            <li class="roompage-item"><a class="roompage-link" >{{ pageNow }} of {{ pageLen }}</a></li>
            <li class="roompage-item"><a class="roompage-link" @click="nextPage" >Next</a></li>
        </ul>
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
            rankList:[
                {no: 0, nickname: 0, playcount: 0, wincount : 0,  rank: "F", score:10},
                {no: 0, nickname: 0, playcount: 0, wincount : 0,  rank: "F", score:10},
                {no: 0, nickname: 0, playcount: 0, wincount : 0,  rank: "F", score:10},
                {no: 0, nickname: 0, playcount: 0, wincount : 0,  rank: "F", score:10},
                {no: 0, nickname: 0, playcount: 0, wincount : 0,  rank: "F", score:10},
                {no: 0, nickname: 0, playcount: 0, wincount : 0,  rank: "F", score:10},
                {no: 0, nickname: 0, playcount: 0, wincount : 0,  rank: "F", score:10},
                {no: 0, nickname: 0, playcount: 0, wincount : 0,  rank: "F", score:10},
                {no: 0, nickname: 0, playcount: 0, wincount : 0,  rank: "F", score:10},
                {no: 0, nickname: 0, playcount: 0, wincount : 0,  rank: "F", score:10},
            ],
            totalRank: 1,
            pageLen: 0,
            // 현재 페이지
            pageNow: 1,
            // 방만들기 모달 활성화
        }
    },    
    created() {
        console.log("sdfasjdkfhxc kjsdhzxcusehdiwejef");
        // 보이는 화면 크기 확인
        this.getRankCount();
        this.getRankList();

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
        },

        getRankCount(){
            http
            .get("user/rank/listcount")
            .then((res) => {
                this.totalRank = res.data;
                console.log(this.totalRank);
                this.pageLen = Math.ceil(this.totalRank / 10);
            })
            .catch(err => {
            console.log(err)
            })
        },
        getRankList(){
            http
            .get("user/rank/list/"+this.pageNow)
            .then((res) => {
                console.log("rank 길이 : " + res.data.length);

                for(let i=0; i < 10; i++){
                    // console.log(i + "번째--------- ");
                    // console.log("id : " + res.data[i].id);
                    // console.log("nickname : " + res.data[i].nickname);
                    // console.log("playcount : " + res.data[i].playcount);
                    // console.log("wincount : " + res.data[i].wincount);
                    // console.log("rank : " + res.data[i].rank);
                    // console.log("score : " + res.data[i].score);
                    // console.log("pagenow : " + this.pageNow);

                    if (i < res.data.length) {
                        this.rankList[i].no = (this.pageNow - 1) * 10 + i + 1;
                        this.rankList[i].nickname = res.data[i].nickname;
                        this.rankList[i].playcount = res.data[i].playcount;
                        this.rankList[i].wincount = res.data[i].wincount;
                        this.rankList[i].rank = res.data[i].rank;
                        this.rankList[i].score = res.data[i].score;
                    } else {
                        this.rankList[i].no = 'none';
                    }
                }
            })
            .catch(err => {
            console.log(err)
            })
        },
        nextPage() {
        this.pageNow = ((this.pageNow + 1) > this.pageLen) ? this.pageNow : (this.pageNow + 1)
        console.log("pageNow: "+this.pageNow);
        this.getRankList();
        },
        prevPage() {
            this.pageNow = ((this.pageNow - 1) < 1) ? 1 : (this.pageNow - 1)
            console.log("pageNow: "+this.pageNow);
            this.getRankList();
        },
    },
    
}
</script>

<style lang="scss" scoped>
@import url('https://fonts.googleapis.com/css2?family=Open+Sans&family=Oswald&display=swap');

:root {
    --widthSize: 400px;
    --heightSize: 400px;
}

#LoadingModeRank {
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


  @import url(https://fonts.googleapis.com/css?family=PT+Sans);
* {box-sizing: border-box}
html {background: #88bfd4; text-align: center}

#menu {
  //position: absolute;
  bottom: -45px;
	list-style: none;
	padding: 0px; 
  margin: 0;
	background: #5c8a9700;
  margin-left: 41%;
	display: inline-block;
	height: 50px;
	overflow: hidden;
}

#menu li {
	margin-left: 10px;
	display: inline-block;
	position: relative;
	bottom: 0px;
}

#menu li:first-child {margin: 0}

#menu li a {
	background:rgba(255, 254, 254, 0.6);
	display: block;
	border-radius: 3px;
	padding: 0 12px;
	color: white;
	position: relative;
	text-decoration: none;
	height: 27px;
	font: 12px / 27px "PT Sans", Arial, sans-serif;
	box-shadow: 0px 3px rgba(255, 254, 254, 0.6), 0px 4px 5px rgba(0, 0, 0, 0.11);
	transition: all 0.3s ease;
}

#menu li a:hover {background: rgba(255, 254, 254, 0.9)}
#menu li a:active {
	background:rgba(255, 254, 254, 0.9);

	bottom: -3px;
	box-shadow: 0px 0px rgba(255, 254, 254, 0.9), 0px 1px 3px rgba(0, 0, 0, 0.11);
}

// story 부분

// .main__story {
//     clear: both; 
//     position: sticky;
//     margin: auto;
//     color: rgb(165, 165, 165);
//     font-size:20px;
//     line-height:1.6em;
//     font-family: 'Open Sans', sans-serif, 'Oswald', sans-serif;
//     // font-family:sans-serif;
//     margin: 0;
//     // attributes to accompany the animation
//     opacity:0;
//     position:relative;
//     transform:translateY(1em);
//     // animation stuff
//     animation:page-in ease-out 3s;
//     animation-fill-mode:forwards;
//     text-align: center;
// }

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

th, td{
    padding: 10px;
}

</style>