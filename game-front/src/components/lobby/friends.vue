<template>
  <div>
    <div class="friends-wrapper">
      
      <div class="container" style="justify-content: space-between">
        <p style="margin-left:20px">친구 목록</p>
        <!-- 친구 추가버튼  -->
        <button @click="showModal = true" class="button" style="margin:10px">+</button>
      </div>


      <!-- 모달 주변을 클릭하면 모달이 사라지는 효과 -->
      <transition name="fade" appear>
        <div v-if="showModal" @click="cancel" class="modal-overlay"></div>
      </transition>
      <!-- 친구추가 버튼을 눌렀을 때 생기는 모달 -->
      <transition name="pop" appear>
        <div class="modal" 
            role="dialog" 
            v-if="showModal"
            >
          <div class="container">
            <button @click="cancel" class="button">X</button>
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
          <!-- 이 상태에서 x 버튼을 누르면 요청 취소라고 상대방한테도 전달해야함... -->
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



      <!-- 모달 주변을 클릭하면 모달이 사라지는 효과 -->
      <transition name="fade" appear>
        <div v-if="getFriendReqModal" @click="getFriendReqModal = false, friendAns('no')" class="modal-overlay"></div>
      </transition>
      <!-- 친구 추가 요청을 받았을 때 popup되는 모달  -->
      <transition name="pop" appear>
        <div class="modal" 
            role="dialog" 
            v-if="getFriendReqModal"
          >

          <div class="container">
            <button @click="getFriendReqModal = false, friendAns('no')" class="button">X</button>
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
          <div v-for="friend in friends" :key="friend.no + 'key'" class="friend">
            <p style="margin: 5px">
              {{ friend.isLogin }}: {{ friend.nickname }}
            </p>
          </div>
        </div> 
      </div>

    </div>
  </div>
</template>

<script>
import http from "../../util/http-lobby.js";

const storage = window.sessionStorage;

export default {
  data: () =>{
        return{
          friends: [
            {no: 1, isLogin: true, nickname:"1 번 친구"},
            {no: 2, isLogin: true, nickname:"2 번 친구"},
            {no: 3, isLogin: true, nickname:"3 번 친구"},
            {no: 4, isLogin: true, nickname:"4 번 친구"},
            {no: 5, isLogin: true, nickname:"5 번 친구"},
            {no: 6, isLogin: true, nickname:"6 번 친구"},
            {no: 7, isLogin: true, nickname:"7 번 친구"},
            {no: 8, isLogin: true, nickname:"8 번 친구"},
            {no: 9, isLogin: true, nickname:"9 번 친구"},
            {no: 10, isLogin: true, nickname:"10 번 친구"},
            {no: 11, isLogin: true, nickname:"11 번 친구"},
            {no: 12, isLogin: true, nickname:"12 번 친구"},
            {no: 13, isLogin: true, nickname:"13 번 친구"},
            {no: 14, isLogin: true, nickname:"14 번 친구"},
            {no: 15, isLogin: true, nickname:"15 번 친구"},
          ],
          // 친구 추가 요청을 보낼 때 보이는 모달을 보여줄 것인지
          showModal: false,
          friendReqStatus: "요청전",
          friendName: "",
          // 소켓 getMessage 결과에 따라 아래 변수를 true로 하면 모달이 나옴
          getFriendReqModal: true,
          websock: null,
        }
  },

  created(){

    // sessionStorage에 들어있는 사용자 id 받아오는 부분! 확인용으로 나중에 삭제 예정
    console.log("id: ");
    console.log("id: ");
    console.log("id: ");
    console.log("id: ");
    console.log(sessionStorage);
    console.log("id: " + sessionStorage.getItem("id"));
      // 처음에 lobby 서버에서 http로 친구목록 데이터를 받아옴
      this.getFriendsList(); 
      // this.initWebScoket();
  },

  mounted(){
    
  },

  methods: {
    getFriendsList() {
      // lobby 서버에서 http로? 혹은 소켓으로 내 친구 목록 가져오기.
      // 대흠님이 소켓으로 가능하면 다 보내보고 안되면 http랑 섞을께요라고함
      // 친구가 로그인되어있는지 정보도 들어있어야함

      // http
      // .get("/accounts/getFriendsList/")
      // .then((res) => {
      //   // 아래 내용은 서버 기능 만들고 수정하기 
      //   // this.friends 변수에 친구 목록 데이터 넣기
      //   this.friends = res.data;
      // })







    },

    // 소켓 메시지를 받아옴
    getSockMsg() {
      // 소켓으로 메시지 받아오는 기능 여기에 구현하기







      // 소켓으로 친구 로그인 상태 변화 응답을 받았을 때.
      // this.friends 안의 친구 정보 isLogin부분 수정
      // 서버에서 리스트로 보낼거면 내가 가진 친구 리스트에서 몇번째 친구인지도 알려주거나
      // 아니면 친구 목록 통째로 새로 넘겨주거나 둘 중 하나로 구현하면 될듯 
      // 아니면 key:value 형태로...하면 for문 가능한가?



      // 소켓으로 친구 추가 요청을 받았을 때
      // 친구요청응답 매개변수로 담아서 friendAns()함수 호출하기



      // 소켓으로 친구 추가 요청에 대한 응답을 받았을 때 수락 or 거절
      this.friendReqStatus='상대방이 요청을 수락했습니다.'
      // this.friendReqStatus='상대방이 요청을 거절했습니다.'


      
      // 친구 추가 요청자와 응답자 모두 서버에서 상대 친구 정보를 받아서
      // this.friends 리스트에 데이터 추가하기 



      // 요청 당사자가 친구 추가 요청을 취소했을 때
      // this.getFriendReqModal = false;



    },

    // 친구 추가 요청을 보낼 때
    addFriend() {
      this.friendReqStatus = '응답기다림'
      // console.log("friend Name:"+this.friendName)
      // 소켓으로 친구 닉네임 넘겨서 친구추가요청 여기에 구현하기
      




      

      this.friendName = "";
    },

    // 친구 추가 요청을 받았을 때 
    friendAns(ans) {
      console.log("Ans: "+ans)

      // 로비서버에 소켓으로 친구 추가 요청 응답(ans 매개변수) 보내기 
      // 로비서버에서 user 테이블 안에 있는 친구 정보 수정
      // 친구가 로그아웃이거나 게임중이면 바로 친구추가 불가 메시지를 요청자가 받아야함

      





      
      this.getFriendReqModal = false;
    },

    // 친구 추가 요청 취소
    cancel() {
      if(this.friendReqStatus==='응답기다림') {
        // 서버에 소켓으로 상대방이 요청 취소했다고 전달하기. 





      }
      this.showModal=false;
      this.friendReqStatus='요청전';
    },


    logout() {

      // 로그아웃 버튼 누르면 여기 함수 실행 잘 되는거 확인함!!!!!!1
      // 로그아웃 장고 로그아웃 요청을 여기서 보낼지 lobby.vue로 보낼지 대흠님이랑 맞춰보기 
      // 소켓이나 axios로 로그아웃한다고 신호 보내고 로그아웃해야함 
      // 그 다음에 로그인 페이지로 전환
      console.log("sdkfjhas;dkfjhaskldjfhaskldjfhksjxcnsdjkzcisdk");
      console.log("sdkfjhas;dkfjhaskldjfhaskldjfhksjxcnsdjkzcisdk");
      console.log("sdkfjhas;dkfjhaskldjfhaskldjfhksjxcnsdjkzcisdk");
      console.log("sdkfjhas;dkfjhaskldjfhaskldjfhksjxcnsdjkzcisdk");
      console.log("sdkfjhas;dkfjhaskldjfhaskldjfhksjxcnsdjkzcisdk");
      console.log("sdkfjhas;dkfjhaskldjfhaskldjfhksjxcnsdjkzcisdk");
      console.log("sdkfjhas;dkfjhaskldjfhaskldjfhksjxcnsdjkzcisdk");
      console.log("sdkfjhas;dkfjhaskldjfhaskldjfhksjxcnsdjkzcisdk");
      console.log("sdkfjhas;dkfjhaskldjfhaskldjfhksjxcnsdjkzcisdk");
      console.log("sdkfjhas;dkfjhaskldjfhaskldjfhksjxcnsdjkzcisdk");
      console.log("sdkfjhas;dkfjhaskldjfhaskldjfhksjxcnsdjkzcisdk");
      console.log("sdkfjhas;dkfjhaskldjfhaskldjfhksjxcnsdjkzcisdk");
      console.log("sdkfjhas;dkfjhaskldjfhaskldjfhksjxcnsdjkzcisdk");
      console.log("sdkfjhas;dkfjhaskldjfhaskldjfhksjxcnsdjkzcisdk");
      console.log("sdkfjhas;dkfjhaskldjfhaskldjfhksjxcnsdjkzcisdk");
    },

      webSocketSend(Data){
        console.log("메시지 전송");
        this.websock.send(Data);
        console.log('1:' + Data)
        console.log('2:' + this.websock)
        console.log(this.websock.sessionId)
      },

      webSocketClose(e){
        console.log("소켓 닫기");
        this.websock.close();
      },

    // 딜레이 함수. 이거 쓰면 그냥 폴링방식으로 계속 기다림 주의
    // setTimeOut?은 다를까..? 한번 시도해보자 
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
  },
    
}
</script>

<style lang="scss" scoped>

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
    background: #414141;
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