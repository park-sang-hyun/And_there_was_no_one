<template>
  <div>
    <div class="room-wrapper">
      <div class="container" style="justify-content: space-between;">
        게임방 목록
        <div>
          <button type="button" class="button" @click="showCreateModal = true">방 만들기</button>
          <button type="button" class="button" @click="random">빠른 입장</button>  
          <!-- <button>게임 설명</button> -->
        </div>
      </div>
    

      <transition name="fade" appear>
        <div v-if="showCreateModal" @click="showCreateModal = false" class="modal-overlay"></div>
      </transition>
      <!-- 방 만들기 버튼 눌렀을 때 생기는 모달 -->
      <transition name="pop" appear>
        <div class="modal" 
            role="dialog" 
            v-if="showCreateModal"
          >
          <div class="container" style="width:100%">
            <button @click="showCreateModal = false" class="button">X</button>
          </div>
          
          <!-- 아래 div를 form 태그로 하면 input 창에서 enter 치거나 버튼 눌렀을 때 새로고침됨 -->
          <div class="inmodal">
            <h1>게임 방 만들기</h1>
            <p>방 이름을 입력하세요</p>
            <input 
              v-model="roomName"
              type="text"
              placeholder="방 이름"
              @keyup.enter="createRoom"
            >

            <p>{{ roomCreateErr }}</p>
            <div >
              <div>
                <label for="mode">Mode</label>
              </div>
              <div class="number-input-container">
                <button
                  type="button"
                  class="button-decrement"
                  @click="setMode('down')"
                ></button>
                <div class="number-input" style="text-align: center">
                  {{ mode }}
                </div>
                <button
                  type="button"
                  class="button-increment"
                  @click="setMode('up')"
                ></button>
              </div>
            </div>

            <div>
              <div>
                <label for="difficulty">Difficulty</label>
              </div>
              <div class="number-input-container">
                <button
                  type="button"
                  class="button-decrement"
                  @click="setDifficulty('down')"
                ></button>
                <div class="number-input">
                  {{ difficulty }}
                </div>
                <button
                  type="button"
                  class="button-increment"
                  @click="setDifficulty('up')"
                ></button>
              </div>
            </div>

            <div>
              <div>
                <label for="people">Number of People</label>
              </div>
              <div class="number-input-container">
                <button
                  type="button"
                  class="button-decrement"
                  @click="setPeople('down')"
                ></button>
                <div class="number-input">
                  {{ people }}
                </div>
                <button
                  type="button"
                  class="button-increment"
                  @click="setPeople('up')"
                ></button>
              </div>
            </div>
            

            <button type="button" @click="createRoom" class="button">방 생성하기</button>
          </div>
        
        </div>
      </transition>




      <!-- 게임방 목록 -->
      <div class="cards">
        <div class="card" v-for="room in roomList" :key="room.no + 'key'">
          <div class="card__inner" @click="pickRoom(room.no)">
            <span>{{ room.no }} Card</span>
          </div>
        </div>
      </div>

      <!-- 페이지네이션 -->
      <ul class="pagination" id="menu">
        <li class="page-item"><a class="page-link" @click="prevPage">Prev</a></li>
        <li class="page-item"><a class="page-link" href="#">{{ pageNow }} of {{ pageLen }}</a></li>
        <li class="page-item"><a class="page-link" @click="nextPage" >Next</a></li>
      </ul>
      
    </div>
  </div>

</template>

<script>
// http axios 요청 주소 lobby 서버로 바꿔야함 
// js 파일 하나 더 만들든지 해서 아래 부분 처리하고 넘어가기
import http from "../../util/http-common.js";

// import mode from '@/components/room/modeSetting.vue';
// import difficulty from '@/components/room/difficultySetting.vue';


export default {
  
    name: 'Roomlist',

    // components: {
    //     difficulty,
    //     mode,
    // },

    data: () =>{
      return {
        // 받아온 방 정보 8개 객체를 받아옴
        roomList: [
            {no: 0, roomname: 0, mode: 0, cur_people : 0, max_people: 1, difficulty: 1, start: false},
            {no: 1, roomname: 1, mode: 1, cur_people : 0, max_people: 1, difficulty: 1, start: false},
            {no: 2, roomname: 2, mode: 2, cur_people : 0, max_people: 1, difficulty: 1, start: false},
            {no: 3, roomname: 3, mode: 3, cur_people : 0, max_people: 1, difficulty: 1, start: false},
            {no: 4, roomname: 4, mode: 4, cur_people : 0, max_people: 1, difficulty: 1, start: false},
            {no: 5, roomname: 5, mode: 5, cur_people : 0, max_people: 1, difficulty: 1, start: false},
            {no: 6, roomname: 6, mode: 6, cur_people : 0, max_people: 1, difficulty: 1, start: false},
            {no: 7, roomname: 7, mode: 7, cur_people : 0, max_people: 1, difficulty: 1, start: false},
          ],
        // totalRoom은 생성된 룸 개수
        totalRoom: 12,
        // 페이지 총 길이
        pageLen: 0,
        // 현재 페이지
        pageNow: 1,
        // 방만들기 모달 활성화
        showCreateModal: false,
        roomName: "",
        // 게임방 만들기 버튼 활성화
        isRoomSubmit: false,
        // 방 이름 없이 방 만들 경우 에러 메시지
        roomCreateErr: "",


        // Room 설정값
        mode: 1,
        difficulty: 2,
        people: 5,
      }
    },
    created(){
      // 처음에 totalRoom 개수를 받아옴
      this.getRoomInfo();      
      // 현재 페이지의 방 정보를 받아옴
      this.getRoomList();
    },
    methods: {
      // 룸 개수 받아오는 메서드
      getRoomInfo() {
        // Room Read lobby 서버에 요청하기 룸 개수
         http
        .get("room/listcount/")
        .then((res) => {
          //console.log("listcount : " + res.data);
          this.totalRoom = res.data;
        })
        .catch(err => {
          console.log(err)
        })

        this.pageLen = Math.ceil(this.totalRoom / 8);  // 소수점 올림
        this.pageLen = (this.pageLen < 6) ? this.pageLen : 6; // 보여주는 페이지 최대크기는 6
        console.log("pageLen: "+this.pageLen);
        console.log("Enter getRoomInfo");
      },

      // 현재 페이지의 룸 정보 받아오는 메서드
      getRoomList() {
        // Room Read lobby서버에 요청하기 현재 페이지의 룸 정보
        // pageNow 변수에 현재 페이지가 들어있음


        http
        .get("room/list/"+this.pageNow)
        .then((res) => {
          // 받아온 데이터 출력해보고 아래 수정하기

          //받아온 데이터 roomList에 집어넣기
          for(let i=0; i<8; i++){
            this.roomList[i].no = res.data[i].id;
            this.roomList[i].roomname = res.data[i].title;
            this.roomList[i].mode = res.data[i].mode;
            this.roomList[i].cur_people = res.data[i].cur_count;
            this.roomList[i].max_people = res.data[i].max_count;
            this.roomList[i].difficulty = res.data[i].difficulty;
            this.roomList[i].start = res.data[i].start;
          }

          // 결과 찍어보기
          //  for(let i=0; i<8; i++){
          //   console.log(i + "번째 no : " + this.roomList[i].no);
          //   console.log(i + "번째 roomname : " + this.roomList[i].roomname);
          //   console.log(i + "번째 mode : " + this.roomList[i].mode);
          //   console.log(i + "번째 cur_people : " + this.roomList[i].cur_people);
          //   console.log(i + "번째 max_people : " + this.roomList[i].max_people);
          //   console.log(i + "번째 difficulty : " + this.roomList[i].difficulty);
          //   console.log(i + "번째 start : " + this.roomList[i].start);
          //  }

        })
        .catch(err => {
          console.log(err)
        })

        console.log("Enter getRoomList");
      },

      nextPage() {
        this.pageNow = ((this.pageNow + 1) > this.pageLen) ? this.pageNow : (this.pageNow + 1)
        console.log("pageNow: "+this.pageNow);
        this.getRoomList();
      },

      prevPage() {
        this.pageNow = ((this.pageNow - 1) < 1) ? 1 : (this.pageNow - 1)
        console.log("pageNow: "+this.pageNow);
        this.getRoomList();
      },

      createRoom() {
        console.log("Enter createRoom");

        // this.roomName = "parksanghyun";
        // 방 이름이 기입되면 Room Create 요청 보내기
        if (this.roomName) {
          console.log("roomname: "+this.roomName)
          
          //임의로 정의
          let username = sessionStorage.getItem("id");
          let title=this.roomName;
          let cur_count = 1;
          let max_count = this.people;
          let mode = this.mode;
          let difficulty = this.difficulty;
          let start = false;

          //formData안에 값 넣기
          let formData = new FormData();
          formData.append("title", title);
          formData.append("cur_count", cur_count);
          formData.append("max_count", max_count);
          formData.append("mode", mode);
          formData.append("difficulty", difficulty);
          formData.append("start", start);
          formData.append("username", username);

          http
          .post("room/create", formData)
          .then((res) => {
            //Create요청 후 받은 방 번호로 페이지 이동
            console.log(res)  
            console.log(res.data)  
            console.log("방생성 완료");
            this.enterRoom(res.data.room_id);
          })
          .catch(err => {
            console.log(err)
          })


          // 임시로 1번방으로 들어가는 느낌으로 해놓음
          this.enterRoom(1)

          this.showCreateModal = false;
          this.roomName = "";
          this.mode = 1;
          this.difficulty = 2;
          this.people = 5;
        }
        else {
          this.roomCreateErr="방 이름을 기입하세요."
        }
      },

      setMode(input) {
        if (input === 'down') {
          this.mode = ((this.mode - 1) < 1) ? 1 : (this.mode - 1);
        }
        else if (input === 'up') {
          this.mode = (this.mode >= 2) ? 3 : (this.mode + 1);
        }

        console.log("modemode:     "  + this.mode);
        
      },

      setDifficulty(input) {
        if (input === 'down') {
          this.difficulty = ((this.difficulty - 1) < 1) ? 1 : (this.difficulty - 1);
        }
        else if (input === 'up') {
          this.difficulty = (this.difficulty >= 2) ? 3 : (this.difficulty + 1);
        }

        console.log("difficultydifficulty:     "  + this.difficulty);
      },

      setPeople(input) {
        if (input === 'down') {
          this.people = ((this.people - 1) < 3) ? 3 : (this.people - 1);
        }
        else if (input === 'up') {
          this.people = (this.people >= 7) ? 8 : (this.people + 1);
        }

        console.log("peoplepeople:     "  + this.people);
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
        // this.$router.push("/room????");
        console.log("Enter enterRoom: " + roomNo);
        this.$router.replace({ name: 'WaitRoom' , params: { roomId: roomNo }});
      },
    },
}

</script>

<style scoped lang="scss">


/* 게임방 리스트 */
/* 
  * {
    box-sizing: border-box;
  } */

  .room-wrapper {
    padding-top: 20px;
    padding-left: 20px;
    position: relative;
    min-width: 900px;
    min-height: 580px;

    background-color: rgba(221, 250, 193, 0.3);
    border-radius: 20px;
    box-shadow: 0 0 5px 0 rgba(155, 150, 85, 0.06);
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




/* 페이지네이션 */

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


/* 우측정렬용 컨테이너 */
.container {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  width: 96%;
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
  margin-bottom: 10px;
  margin-left:10px;
}

.modal {
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  margin: auto;
  /* text-align: center; */
  width: fit-content;
  height: fit-content;
  /* max-width: 200em; */
  padding: 40px;
  border-radius: 1rem;
  box-shadow: 0 5px 5px rgba(0, 0, 0, 0.2);
  background: #FFF;
  z-index: 999;
  transform: none;
}
.modal h1 {
  margin: 0 0 1rem;
}

.modal-overlay {
  content: '';
  position: absolute;
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: 998;
  background: #414141;
  opacity: 0.6;
  cursor: pointer;
}

.inmodal {
  width: 400px;
  height: fit-content;
  text-align: center;
}
/* ---------------------------------- */
.fade-enter-active,
.fade-leave-active {
  transition: opacity .4s linear;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.pop-enter-active,
.pop-leave-active {
  transition: transform 0.4s cubic-bezier(0.5, 0, 0.5, 1), opacity 0.4s linear;
}

.pop-enter,
.pop-leave-to {
  opacity: 0;
  transform: scale(0.3) translateY(-50%);
}


  .number-input-container {
      width: 120px;
      margin: 20px;
      display: grid;
      grid-template-columns: 48px auto 48px;
      margin-left: 37%;
  }

  .number-input {
      width: 100%;
      text-align: center;
  }

  button {
      position: relative;

      height: 100%;

      margin-top: 32px;
      padding: 12px 16px;

      background-color: var(--color-mustard-extra-light);

      border: 1px solid var(--color-mustard-light);
      border-radius: none;

      transition: all 0.1s ease-out;

      cursor: pointer;

      -webkit-appearance: none;

      -webkit-transform: scale(1);
      transform: scale(1);
  }

  button {
      margin: 0;

      color: var(--color-black);
  }

  button:active,
  button:focus {
      outline: none;
  }

  button::after {
      content: "";

      position: absolute;

      opacity: 1;

      top: 0;
      left: 0;
      bottom: 0;
      right: 0;

      transition: inherit;

      background-position: center;
      background-repeat: no-repeat;
  }


  .button-decrement::after {
      background-image: url("data:image/svg+xml,%3Csvg width='24' height='24' viewBox='0 0 24 24' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M17 12H7' stroke='%23112C34' stroke-width='2' stroke-miterlimit='10' stroke-linecap='round' stroke-linejoin='round'/%3E%3C/svg%3E%0A");
  }

  .button-increment::after {
      background-image: url("data:image/svg+xml,%3Csvg width='24' height='24' viewBox='0 0 24 24' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M12 7V17' stroke='%23112C34' stroke-width='2' stroke-miterlimit='10' stroke-linecap='round' stroke-linejoin='round'/%3E%3Cpath d='M17 12H7' stroke='%23112C34' stroke-width='2' stroke-miterlimit='10' stroke-linecap='round' stroke-linejoin='round'/%3E%3C/svg%3E%0A");
  }




</style>