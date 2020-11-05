<template>
  <div>
    <div>
      <br/>
      친구 목록
      
      <!-- 친구 추가버튼  -->
      <button @click="showModal = true" class="button">+</button>
    </div>

    <!-- 친구추가 버튼 눌렀을 때 생기는 모달 -->
    <transition name="pop" appear>
      <div class="modal" 
          role="dialog" 
          v-if="showModal"
          >
        <button @click="showModal = false" class="button">X</button>
        <form action="">
          <h1>친구 추가</h1>
          <p>추가할 친구의 닉네임을 입력하세요</p>
          <input 
            v-model="friendName"
            type="text"
            @keyup.enter="searchFriend"
            placeholder="닉네임"
          >
          <button @click="searchFriend" class="button">요청하기</button>
        </form>
      
      </div>
    </transition>

    <!-- 친구 리스트 -->
    <!-- 친구 리스트에 스크롤바 두니까 아래로 안움직임 -->
    <!-- scrollbar-box가 한 페이지에 두개라서 발생한 문제로 보임 뒤에 2를 붙여서 해결 -->
    <div class="scrollbar-box2" id="style-1" >
      <div class="force-overflow" >
        <div v-for="friend in friends" :key="friend + 'key'">
          <p>{{ friend }}: "friend name"</p>
        </div>
      </div> 
    </div>
  </div>
</template>

<script>
import http from "../../util/http-common.js";

export default {
  data: () =>{
        return{
          friends: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15],
          showModal: false,
          friendName: "",
        }
  },
  created(){
      // 처음에 Django 서버에서 친구목록 데이터를 받아옴
      this.getFriends();      
    },
  methods: {
    getFriends() {
      // User 서버에서 내 친구 목록 가져오기.
      // 친구가 로그인되어있는지 정보도 들어있음
      http
      .get("/accounts/getfriends/")
      .then((res) => {
        // 아래 내용은 서버 기능 만들고 수정하기 
        this.friends = res.data;
      })
    },
    searchFriend() {
      this.showModal = false;
      // 소켓으로 친구 닉네임 넘겨서 찾기 
      // console.log("friend Name:"+this.friendName)
      
    }
  }
}
</script>

<style scoped>
  .chatbox {
    border-radius: 20px;
    background-color: rgba(0, 0, 0, 0.6);
    height: 80%;
    width: 90%;
    margin: 0px auto;
  }
  
.scrollbar-box2
{
	margin: auto;
	height: 250px;
	width: 85%;
	background-color: rgba(0, 41, 0, 0);
	overflow-y: scroll;
  position : relative; 
  bottom: 0px;
  margin:20px;
}

.force-overflow
{
  /* 스크롤바 내부의 글자가 누적되는 창 크기
  스크롤바 height 보다 min-height가 커야 우측 스크롤바가 생김 */
	min-height: 251px;
  margin: 20px;
}

/*
 *  scrollbar STYLE 1
 */

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

.button {
  border: none;
  color: #FFF;
  background: #42b983;
  appearance: none;
  font: inherit;
  border-radius: .3em;
  cursor: pointer;
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