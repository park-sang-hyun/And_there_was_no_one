<template>
  <div class="wrapper">
    <div>
      <h2>게임방 목록</h2>
      <div>
        <button type="button" class="button" @click="createRoom">방 만들기</button>
        <button type="button" class="button" @click="random">빠른 입장</button>  
        <!-- <button>게임 설명</button> -->
      </div>
      
    </div>
    
    <div class="cards">
      <div class="card" v-for="room in roomList" :key="room.no + 'key'">
        <div class="card__inner" @click="pickRoom(room.no)">
          <span>{{ room.no }} Card</span>
        </div>
      </div>
    </div>

    <ul class="pagination" id="menu">
      <li class="page-item"><a class="page-link" @click="prevPage">Prev</a></li>
      <li class="page-item"><a class="page-link" href="#">{{ pageNow }} of {{ pageLen }}</a></li>
      <li class="page-item"><a class="page-link" @click="nextPage" >Next</a></li>
    </ul>
    
  </div>
</template>

<script>
// http axios 요청 주소 lobby 서버로 바꿔야함 
// js 파일 하나 더 만들든지 해서 아래 부분 처리하고 넘어가기
import http from "../../util/http-common.js";

export default {
    data: () =>{
      return {
<<<<<<< HEAD
        // 받아온 방 정보 8개 객체를 받아옴
        roomList: [
            {no: 0, roomname: 0, mode: 0, people: 0},
            {no: 1, roomname: 1, mode: 1, people: 1},
            {no: 2, roomname: 2, mode: 2, people: 2},
            {no: 3, roomname: 3, mode: 3, people: 3},
            {no: 4, roomname: 4, mode: 4, people: 4},
            {no: 5, roomname: 5, mode: 5, people: 5},
            {no: 6, roomname: 6, mode: 6, people: 6},
            {no: 7, roomname: 7, mode: 7, people: 7},
          ],
        // totalRoom은 생성된 룸 개수
        totalRoom: 12,
        // 페이지 총 길이
=======
        list: [0, 1, 2, 3, 4, 5, 6, 7],
>>>>>>> 8d1ff45bd179905b134e20ef0ee1f22d6eb8cf4d
        pageLen: 0,
        // 현재 페이지
        pageNow: 1,
      }
    },
    created(){
      // 처음에 totalRoom 데이터를 받아옴
      this.getRoomInfo();      
      // 해당 페이지의 방 정보를 받아옴
      this.getRoomList();
    },
    methods: {
      getRoomInfo() {
        // Room Read lobby 서버에 요청하기 룸 개수


        // http
        // .get("")
        // .then((res) => {
        //   // 받아온 데이터 출력해보고 아래 수정하기
        //   this.total.Room = res.data 
        // })


        this.pageLen = Math.ceil(this.totalRoom / 8);  // 소수점 올림
        this.pageLen = (this.pageLen < 6) ? this.pageLen : 6; // 보여주는 페이지 최대크기는 6
        console.log("pageLen: "+this.pageLen);
        console.log("Enter getRoomInfo");
      },

      getRoomList() {
        // Room Read lobby서버에 요청하기 현재 페이지의 룸 정보
        // pageNow 변수에 현재 페이지가 들어있음


        // http
        // .get("")
        // .then((res) => {
        //   // 받아온 데이터 출력해보고 아래 수정하기
        //   // 받아온 데이터에 따라 위 template 출력 내용도 수정하기
        //   this.roomList = res.data
        // })



        console.log("Enter getRoomList");
      },

      nextPage() {
        this.pageNow = ((this.pageNow + 1) > this.pageLen) ? this.pageNow : (this.pageNow + 1)
        console.log("pageNow: "+this.pageNow);
        this.getRoomList();
      },

      prevPage() {
        this.pageNow = ((this.pageNow - 1) < 1) ? 1 : (this.pageNow - 1)
<<<<<<< HEAD
        console.log("pageNow: "+this.pageNow);
        this.getRoomList();
      },

      createRoom() {
        // Room Create 요청 보내기


        // http
        // .post("", {

        // },
        // )
        // .then((res) => {

        // })


        console.log("Enter createRoom");
      },

      random() {
        var roomNo = Math.floor(Math.random() * (this.totalRoom + 1));
        console.log("Enter random: " + roomNo);
        this.enterRoom(roomNo);
      },

      pickRoom(roomNo) {
        console.log("Enter pickRoom: " + roomNo);
        this.enterRoom(roomNo);
      },

      enterRoom(roomNo) {
        // Room 들어가기 요청 보내기
        console.log("Enter enterRoom: " + roomNo);
      },
=======
        console.log(this.pageLen)
      }
>>>>>>> 8d1ff45bd179905b134e20ef0ee1f22d6eb8cf4d
    },
}


</script>

<style scoped lang="scss">

  * {
    box-sizing: border-box;
  }

  .wrapper {
    position: relative;
    min-width: 900px;
    min-height: 610px;

    background-color: #fff;
    box-shadow: 0 0 5px 0 rgba(0, 0, 0, 0.06);
  }

  .cards {
    padding: 10px;
    display: flex;
    flex-flow: row wrap;
  }
  /* //Cards */
  .card {
    margin: 10px; 
    width: 47%;
    transition: all 0.2s ease-in-out;
    
    &:hover {
      .card__inner {
        background-color: #9c642f;
        transform: scale(1.05);
        border-radius: 20px;
      }
    }

    &__inner {
      width: 100%;
      padding: 30px;
      min-width: 80px;
      min-height: 60px;
      position: relative;
      cursor: pointer;
      border-radius: 20px;
      
      background-color: #eceef127;
      color: #eceef127;
      font-size: 1.5em;
      text-transform: uppercase;
      text-align: center;

      transition: all 0.2s ease-in-out;
      
      &:after {
        transition: all 0.3s ease-in-out;
      }
    }
  }

  @import url(https://fonts.googleapis.com/css?family=PT+Sans);
* {box-sizing: border-box}
html {background: #88bfd4; text-align: center}

#menu {
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
	background: #a1d0dd;
	display: block;
	border-radius: 3px;
	padding: 0 12px;
	color: white;
	position: relative;
	text-decoration: none;
	height: 27px;
	font: 12px / 27px "PT Sans", Arial, sans-serif;
	box-shadow: 0px 3px #7fafbc, 0px 4px 5px rgba(0, 0, 0, 0.3);
	transition: all 0.3s ease;
}


#menu li a:hover {background: #bae0ea}
#menu li a:active {
	background: #bae0ea;

	bottom: -3px;
	box-shadow: 0px 0px #7fafbc, 0px 1px 3px rgba(0, 0, 0, 0.3);
}



</style>