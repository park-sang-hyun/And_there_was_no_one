<template>
  <div>
    <div class="friends-wrapper">
      
      <div class="container" style="justify-content: space-between">
        <p style="margin-left:20px">친구 목록</p>
        <!-- 친구 추가버튼  -->
        <button @click="showModal = true" class="button" style="margin:10px">+</button>
      </div>

      <!-- 친구추가 버튼을 눌렀을 때 생기는 모달 -->
      <transition name="pop" appear>
        <div class="modal" 
            role="dialog" 
            v-if="showModal"
            >
          <div class="container">
            <button @click="showModal=false, friendReqStatus='요청전'" class="button">X</button>
          </div>
          
          <h1>친구 추가</h1>
          <!-- div를 form 태그로 하면 input 창에서 enter 치거나 버튼 눌렀을 때 새로고침됨 주의-->
          <!-- 친구 추가 요청 보내기 전 상태 -->
          <div v-if="friendReqStatus==='요청전'">
            <p>추가할 친구의 닉네임을 입력하세요</p>
            <input 
              v-model="friendName"
              type="text"
              @keyup.enter="addFriend"
              placeholder="닉네임"
            >
            <button @click="addFriend" class="button" style="margin-left:10px">요청하기</button>
          </div>

          <!-- 친구 추가 요청 후 상대방의 응답을 기다리는 상태 -->
          <div v-else-if="friendReqStatus==='응답기다림'">
              <p>친구의 응답을 기다리는 중입니다. </p>
              <button @click="getSockMsg()">임시 버튼</button>
          </div>

          <!-- 친구 추가에 대한 응답 메시지를 받은 상태 -->
          <div v-else>
            <p>{{ friendReqStatus }}</p>
            <button @click="showModal=false, friendReqStatus='요청전'" class="button">닫기</button>
          </div>
        
        </div>
      </transition>

      <!-- 친구 추가 요청을 받았을 때 popup되는 모달  -->
      <transition name="pop" appear>
        <div class="modal" 
            role="dialog" 
            v-if="getFriendReqModal"
          >

          <div class="container">
            <button @click="getFriendReqModal = false" class="button">X</button>
          </div>
          <!-- 아래 div를 form 태그로 하면 input 창에서 enter 치거나 버튼 눌렀을 때 새로고침됨 -->
          <div>
            <h1>친구 추가 요청</h1>
            <p>친구 추가 요청을 수락하시겠습니까?</p>
            <button @click="friendAns('ok')" class="button" style="margin: 5px">수락</button>
            <button @click="friendAns('no')" class="button" style="margin: 5px">거절</button>
          </div>
        </div>
      </transition>


      <!-- 친구 리스트 -->
      <!-- 친구 리스트에 스크롤바 두니까 아래로 안움직임 -->
      <!-- scrollbar-box가 한 페이지에 두개라서 발생한 문제로 보임 뒤에 2를 붙여서 해결 -->
      <div class="scrollbar-box2" id="style-1" >
        <div class="force-overflow" >
          <div v-for="friend in friends" :key="friend + 'key'" class="friend">
            <p style="margin: 5px">{{ friend }}: "friend name"</p>
          </div>
        </div> 
      </div>

    </div>
  </div>
</template>







<script>
import http from "../../util/http-common.js";

export default {
  props: {
    websocket: Object,
  }, 
  data: () =>{
        return{
          friends: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15],
          // 친구 추가 요청을 보낼 때 보이는 모달을 보여줄 것인지
          showModal: false,
          friendReqStatus: "요청전",
          friendName: "",
          // 소켓 getMessage 결과에 따라 아래 변수를 true로 하면 모달이 나옴
          getFriendReqModal: true,
        }
  },
  created(){
      // 처음에 lobby 서버에서 http로 친구목록 데이터를 받아옴
      this.getFriendsList();      
  },
  methods: {
    getFriendsList() {
      // lobby 서버에서 내 친구 목록 가져오기.
      // 친구가 로그인되어있는지 정보도 들어있음




      // http
      // .get("/accounts/getFriendsList/")
      // .then((res) => {
      //   // 아래 내용은 서버 기능 만들고 수정하기 
      //   this.friends = res.data;
      // })
    },

    // 소켓 메시지를 받아옴
    getSockMsg() {
      this.friendReqStatus='상대방이 요청을 수락했습니다.'
    },

    // 친구 추가 요청을 보낼 때
    addFriend() {
      
      this.friendReqStatus = '응답기다림'
      // 소켓으로 친구 닉네임 넘겨서 찾기 
      // console.log("friend Name:"+this.friendName)

      // get Massage함수 만들어서 메시지가 친구 요청 관련 내용일 때, 
      // 요청 결과 출력 하고 응답받은 메시지를 모달에 출력한 후 this.delayModal(); 호출하기

      // 일단 메시지 안받아도 자동으로 꺼지도록 여기서 처리해줌
      // this.getFriendAns = true;
      // this.showModal = false;
      // this.delayModal();
      this.friendName = "";
    },

    // 친구 추가 요청을 받았을 때 
    friendAns(ans) {
      console.log("Ans: "+ans)

      // 서버로 친구 추가 요청 응답(ans 매개변수) 보내기 -> 로비로 할거야? Auth로 할거야?
      // 어차피 Auth로 해도 lobby로 응답했다는 결과는 보내줘야하나? 
      // 상대방이 수락했는지 거절했는지 다시 보내주려면 로비서버로 보내야지 
      // 그럼 로비서버에서 user 테이블 안에 있는 친구 정보 수정하든지해야할듯
      // 친구가 로그아웃이거나 게임중이면 바로 친구추가 불가 메시지를 요청자가 받아야함

      // 이 부분은 로비 서버에 소켓으로 요청 보내는게 맞을 것 같음 
      
      
      this.getFriendReqModal = false;
    },


    // 딜레이 함수. 이거 쓰면 그냥 폴링방식으로 계속 기다림 주의
    // delayModal() {
    //   // 1.5초 뒤에 변수를 바꿔서 응답 메시지 확인히 가능하게
    //   var gap = 1500;
    //   var then,now; 
    //   then=new Date().getTime(); 
    //   now=then; 
    //   while((now-then)<gap){ 
    //     now=new Date().getTime();  // 현재시간을 읽어 함수를 불러들인 시간과의 차를 이용하여 처리 
    //   } 
    // }, 
  }
}
</script>









<style scoped>

  .friends-wrapper {
    border-radius: 20px;
    background-color: rgba(255, 255, 255, 0.6);
    height: 80%;
    width: 80%;
    margin: 0px 20px;
    padding: 20px;
  }
  
  .scrollbar-box2
  {
    height: 250px;
    width: 100%;
    background-color: rgba(0, 41, 0, 0);
    overflow-y: scroll;
    position : relative; 
    bottom: 0px;
  }

  .force-overflow
  {
    /* 스크롤바 내부의 글자가 누적되는 창 크기
    스크롤바 height 보다 min-height가 커야 우측 스크롤바가 생김 */
    min-height: 251px;
    margin: 15px 10px;
  }

  .friend {
    margin: 10px;
    padding: 5px;
    background: cornsilk;
    border-radius: 10px;
  }

  /*scrollbar STYLE 1*/
  #style-1::-webkit-scrollbar-track
  {
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
    border-radius: 10px;
    background-color: #F5F5F5;
  }

  #style-1::-webkit-scrollbar
  {
    width: 12px;
    background-color: #F5F5F500;
  }

  #style-1::-webkit-scrollbar-thumb
  {
    border-radius: 10px;
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
    background-color: #555;
  }
  

  /* 우측정렬용 컨테이너 */
  .container {
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
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
  }

  .modal {
    position: absolute;
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    margin: auto;
    text-align: center;
    width: fit-content;
    height: fit-content;
    max-width: 50em;
    padding: 2rem;
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
    background: #2c3e50;
    opacity: 0.6;
    cursor: pointer;
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
</style>