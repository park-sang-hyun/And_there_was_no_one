<template>
  <div class="container">
    <h1>Lobby page</h1>
    <div class="gerstner-2">
      <div class="comp">
        <Profile :component="component" class="profile"/>
        <Friends :component="component" class="friends"/>
      </div>
      <div class="comp">
        <Roomlist :component="component" class="roomlist"/>
        <Chat :component="component" class="chat"/>
      </div>
    </div>
  </div>

</template>

<script>
import Chat from "../../components/lobby/chat.vue";
import Friends from "../../components/lobby/friends.vue";
import Profile from "../../components/lobby/profile.vue";
import Roomlist from "../../components/lobby/roomlist.vue";

export default {
  components: {
    Chat,
    Friends,
    Profile,
    Roomlist,
  },
}
</script>

<style scoped>
  .gerstner-2 {
    display: grid;
    grid-template-columns: 1fr 3fr;
    /* grid-column-gap: 1em; */
  }
  .comp {
    display: grid;
    grid-template-rows: 1fr .5fr;
  }
  .profile { 
    background-color: #ba68c8; 
  }
  .friends { 
    background-color: #245eaa; 
  }
  .roomlist{ 
    background-color: #aca002; 
  }
  .chat { 
    background-color: #77aa24; 
  }
</style>