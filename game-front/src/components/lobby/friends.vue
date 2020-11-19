<template>
  <div>
    <!-- 모달 주변을 클릭하면 모달이 사라지는 효과 -->
      <transition name="fade" appear>
        <div v-if="showModal" @click="cancel" class="modal-overlay"></div>
      </transition>
      <!-- 친구추가 버튼을 눌렀을 때 생기는 모달 -->
      <transition name="pop" appear>
        <div class="friendmodal" 
            role="dialog" 
            v-if="showModal"
            >
          <div class="btn_container">
            <button @click="cancel" class="button" style="background-color: rgba(61, 61, 61, 0.5);">X</button>
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
            <p>{{ errmsg }}</p>
            <button @click="addFriend" class="button" style="margin-top:10px; background-color: rgba(48, 48, 48, 1);">요청하기</button>
          </div>

          <!-- 친구 추가 요청 후 상대방의 응답을 기다리는 상태 -->
          <div v-else>
              <p>친구 추가 요청을 보냈습니다. </p>
            <button @click="showModal=false, friendReqStatus='요청전'" class="button" style="margin-top:10px; background-color: rgba(48, 48, 48, 1);">닫기</button>
          </div>
        
        </div>
      </transition>

      <!-- 모달 주변을 클릭하면 모달이 사라지는 효과 -->
      <transition name="fade" appear>
        <div v-if="showFriendReq" @click="showFriendReq = false" class="modal-overlay"></div>
      </transition>
      <!-- 벨을 눌렀을 때 popup되는 모달  -->
      <transition name="pop" appear>
        <div class="friendmodal" 
            role="dialog" 
            v-if="showFriendReq"
          >

          <div class="btn_container" >
            <button @click="showFriendReq = false" class="button" style="background-color: rgba(61, 61, 61, 0.5)">X</button>
          </div>
          <!-- 아래 div를 form 태그로 하면 input 창에서 enter 치거나 버튼 눌렀을 때 새로고침됨 -->
          <div>
            <h1>새로운 알림</h1>

            <div class="scrollbar-box2" id="style-1" style="width: 400px" >
              <div class="force-overflow" >
                <div v-for="(alarm, index) in alarms" :key="(alarm.no, index) + 'alarmkey'" class="friend">
                  <div class="alarmlist">
                    <p style=" font-weight: bold; font-size: 15px;">{{alarm.content}}</p>
                    <button @click="friendAns('ok', index), showFriendReq = false" class="button" style="margin: 5px; background-color: rgba(48, 48, 48, 1)">수락</button>
                    <button @click="friendAns('no', index), showFriendReq = false" class="button" style="margin: 5px; background-color: rgba(48, 48, 48, 1)">거절</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </transition>

    <div id="friends-wrapper">
      
      <div class="btn_container" style="justify-content: space-between">
        <h5 style="margin-left:20px; color:rgba(255, 254, 254, 0.7);">친구 목록</h5>
        <!-- 친구 추가버튼  -->
        <button @click="showModal = true" class="button" style="margin-right:10px">+</button>
      </div>

      <!-- 친구 리스트 -->
      <!-- 친구 리스트에 스크롤바 두니까 아래로 안움직임 -->
      <!-- scrollbar-box가 한 페이지에 두개라서 발생한 문제로 보임 뒤에 2를 붙여서 해결 -->
      <div class="scrollbar-box2" id="style-1" >
        <div class="force-overflow" >
          <div v-for="friend in loginFriends" :key="friend.no + 'loginfriendkey'" class="loginfriend">
            <p style="margin-left: 20px; margin-top: 7px; margin-bottom: 0px; font-weight:bold; font-size:20px;">{{ friend.nickname }} </p>
            <p style="margin-right: 20px; margin-top: 10px">{{ friend.rank }}</p>
          </div>
          <div v-for="friend in logoutFriends" :key="friend.no + 'logoutfriendkey'" class="logoutfriend">
            <p style="margin-left: 20px; margin-top: 7px; margin-bottom: 0px; font-weight:bold; font-size:20px;">{{ friend.nickname }} </p>
            <p style="margin-right: 20px; margin-top: 10px">{{ friend.rank }}</p>
          </div>
        </div> 
      </div>

    </div>
  </div>
</template>

<script>
import http from "../../util/http-lobby.js";
import httpCommon from "../../util/http-common.js";

const storage = window.sessionStorage;

export default {
  data: () =>{
        return{
          loginFriends: [
          ],

          logoutFriends: [
          ],

          // 친구 추가 요청을 보낼 때 보이는 모달을 보여줄 것인지
          showModal: false,
          friendReqStatus: "요청전",
          friendName: "",
          friendName_copy: "",

          websock: null,

          showFriendReq: false,
          alarms: [],

          errmsg: "",
        }
  },

  created(){
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

      // 로그인한 친구랑 로그아웃한 친구랑 데이터 따로 있으니까 꼭 따로 구분해서 저장할 것 !!!!!!

      http
      .get("user/friend/list/" + sessionStorage.getItem('id') + "/")
      .then((res) => {
        // 아래 내용은 서버 기능 만들고 수정하기 
        // this.friends 변수에 친구 목록 데이터 넣기
        this.friends = res.data;
        this.loginFriends = res.data[0].login;
        this.logoutFriends = res.data[0].logout;
      })


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




      
      // 친구 추가 요청자와 응답자 모두 서버에서 상대 친구 정보를 받아서
      // this.friends 리스트에 데이터 추가하기 





      // 친구 로그아웃 or 로그인 했다는 데이터를 받았을 때!!!!!
      // this.refreshFriends(user, status);
      




    },

    // 친구 추가 요청을 보낼 때
    addFriend() {
      // 이름을 입력했을 때만 가능
      if (this.friendName) {
        this.friendName_copy = this.friendName
        
        // 장고로 존재하는 유저인지 확인
        httpCommon
        .get("accounts/findfriend/" + this.friendName + "/")
        .then((res) => {
          if(res.data.message === "ok") {
            this.errmsg = "존재하지 않는 닉네임입니다.";
            
          }
          else {
            //formData안에 값 넣기
            let formData = new FormData();
            formData.append("from_id", sessionStorage.getItem("id"));
            formData.append("to_nickname", this.friendName_copy);

            // 스프링으로 친구추가 요청 보내기
            http
            .post("alarm/send/", formData
            )
            .then((res) => {
            })
            this.friendReqStatus = '응답기다림'
          }
        })
      }
      else {
        this.errmsg = "닉네임을 입력해주세요"
      }
      this.friendName = "";
    },

    // 친구 추가 요청 답변을 보낼 때 호출되는 메서드
    friendAns(ans, index) {

      // 로비서버에 소켓으로 친구 추가 요청 응답(ans 매개변수) 보내기 
      // 로비서버에서 user 테이블 안에 있는 친구 정보 수정
      // 친구가 로그아웃이거나 게임중이면 바로 친구추가 불가 메시지를 요청자가 받아야함
      let formData = new FormData();
      if (ans === 'ok') {
        if(this.alarms[index].kind === -1){
          formData.append("user_id", sessionStorage.getItem('id'));
          formData.append("friend_id", this.alarms[index].to_id);
          formData.append("alarm_id", this.alarms[index].alarm_id);
          http  
          .post("user/friend/make", formData)
          .then((res) => {
            this.getFriendsList(); 
            this.showAlarm(1);
            if(res.data.status === false){
              // room 이동
              this.$router.replace({ name: 'WaitRoom' , params: { roomId: res.data.object }});
            }
          })
        }
        else{
          this.enterRoom(this.alarms[index].kind);
          formData.append("alarm_id", this.alarms[index].alarm_id);
          http
          .post("alarm/delete", formData)
          .then((res) => {
            this.getFriendsList(); 
            this.showAlarm(1);
          })
        }
      }
      else {
        formData.append("alarm_id", this.alarms[index].alarm_id);
        http
        .post("alarm/delete", formData)
        .then((res) => {
          this.getFriendsList(); 
          this.showAlarm(1);
        })
      }
    },

    enterRoom(roomNo) {
        http
        .get(`room/enter/${storage.getItem('id')}/${roomNo}`)
        .then((res) => {
          if (res.data.status) {
            this.$router.replace({ name: 'WaitRoom' , params: { roomId: roomNo }});
          } else {
            alert(res.data.data);
          }
        })
        .catch((err) => {
          console.log(err);
        })                                                                                 
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
      // 소켓이나 axios로 로그아웃한다고 신호 보내고 로그아웃해야함 
      // 그 다음에 로그인 페이지로 전환

      // 장고 서버로 로그아웃 요청 보내기 
      let formData = new FormData();
      formData.append("id", sessionStorage.getItem('id'));
      httpCommon
      .post("accounts/deltoken/", formData)
      .then((res) => {
         httpCommon
        .post("rest-auth/logout/", { 'headers': { 'Authorization': 'Token ' + sessionStorage.getItem('token') }}
          // db에 token 테이블 잘 날아가는지 확인해서 제대로 로그아웃 시키기 
        )
        .then((res) => {
          //세션 정보 삭제 
          sessionStorage.clear();
          alert("로그아웃 되었습니다");
          this.$router.push("/");

        })
      })
     
    },

    refreshFriends(user, status) {
      if (status === 'login'){
        for(let i=0; i<this.logoutFriends.length; i++){
          if (this.logoutFriends[i].no === user) {
            this.loginFriends.push(this.logoutFriends.splice(i, 1));
            break;
          }
        }
      }
      else {
        for(let i=0; i<this.loginFriends.length; i++){
          if (this.loginFriends[i].no === user) {
            let tmp = this.loginFriends[i];
            this.loginFriends.splice(i, 1);
            this.logoutFriends.push(tmp);
            break;
          }
        }
      }
    },

    webSocketSend(Data){
      this.websock.send(Data);
    },

    webSocketClose(e){
      this.websock.close();
    },

    showAlarm(typealarm) {
      // typealarm이 0 이면 데이터 없어도 호출 1이면 데이터 없을 때 호출 안함
      
      
      http
      .get("alarm/receive/" + sessionStorage.getItem('id') + "/")
      .then((res) => {
        this.alarms = res.data

        if (typealarm === 0){
          this.showFriendReq = true;
        }
        else {
          if (this.alarms.length) {
              this.showFriendReq = true;
          }
          else {
            this.showFriendReq = false;
          }
        }
      })

      this.reload();
      
    },

    reload() {
      this.$emit('reload', 'reload');
    }
  },
    
}
</script>

<style lang="scss" scoped>

  #friends-wrapper {
    border-radius: 20px;
    background-color: #aeb0b32f;
    height: 80%;
    width: 80%;
    padding: 20px;
    opacity:0.9;
    margin-left: 10%;

  }
  
  .scrollbar-box2
  {
    height: 215px;
    width: 97%;
    background-color: rgba(0, 41, 0, 0);
    overflow-y: scroll;
    position : relative; 
    bottom: 0px;

  }

  .force-overflow
  {
    /* 스크롤바 내부의 글자가 누적되는 창 크기
    스크롤바 height 보다 min-height가 커야 우측 스크롤바가 생김 */
    min-height: 190px;
    margin: 15px 10px;
  }

  .loginfriend {
    margin: 5px;
    padding: 5px;
    background :#bbbbbb98;
    border-radius: 10px;
    font-size: 15px;
    display: flex;
    justify-content: space-between;
  }
  .logoutfriend {
    margin: 5px;
    padding: 5px;
    background: rgba(255, 248, 220, 0.164);
    border-radius: 10px;
    font-size: 15px;
    display: flex;
    justify-content: space-between;
  }

  /*scrollbar STYLE 1*/
  #style-1::-webkit-scrollbar-track
  {
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0);
    border-radius: 10px;
    background-color: #eceef155;
    opacity:0.9;
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
  .btn_container {
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
  }

  .button {
    border: none;
    color: #FFF;
    background: rgba(255, 254, 254, 0.151);;
    appearance: none;
    font: inherit;
    border-radius: .3em;
    cursor: pointer;
    padding: 5px 10px;
    margin-bottom: 10px;
    opacity:0.9;

    &:hover {
    background: rgba(255, 255, 255, 0.493);
  }
  }

  .friendmodal {
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
  .friendmodal h1 {
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

  .alarmlist {
    background: #eceef188;
    border-radius: 20px;
    padding: 10px;
    margin: 10px 0;
  }
</style>