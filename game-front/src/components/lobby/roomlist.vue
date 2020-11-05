<template>
  <div class="wrapper">
    <h5>게임방 목록</h5>
    <div class="cards">
      <div class="card" v-for="cnt in list" :key="cnt + 'key'">
        <div class="card__inner">
        <span>{{ cnt }} Card</span>
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
export default {
    data: () =>{
      return {
        list: [0, 1, 2, 3, 4, 5, 6, 7],
        pageLen: 0,
        pageNow: 1,
      }
    },
    created(){
        this.pageLen = Math.ceil(this.list.length / 8)  // 소수점 올림
        this.pageLen = (this.pageLen < 6) ? this.pageLen : 6; // 보여주는 페이지 최대크기는 6
        console.log(this.pageLen)
    },
    methods: {
      nextPage() {
        this.pageNow = ((this.pageNow + 1) > this.pageLen) ? this.pageNow : (this.pageNow + 1)
        console.log(this.pageNow)
      },
      prevPage() {
        this.pageNow = ((this.pageNow - 1) < 1) ? 1 : (this.pageNow - 1)
        console.log(this.pageLen)
      }
    },
}


</script>

<style scoped lang="scss">

  * {
    box-sizing: border-box;
  }

  .wrapper {
    min-width: 1000px;
    min-height: 600px;

    background-color: #fff;
    box-shadow: 0 0 5px 0 rgba(0, 0, 0, 0.06);
  }

  .cards {
    padding: 15px;
    display: flex;
    flex-flow: row wrap;
  }
  /* //Cards */
  .card {
    margin: 15px; 
    width: 45%;
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
      min-width: 100px;
      min-height: 80px;
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
	padding: 0 12px; margin: 0;
	background: #5c8a97;
	margin-top: 10px;
  margin-left: 35%;
	display: inline-block;
	height: 50px;
	overflow: hidden;
	border-radius: 5px;
	
	box-shadow: 0px 4px #3b636e, 0px 4px 6px rgba(0, 0, 0, 0.3);
}

#menu li {
	margin-left: 10px;
	display: inline-block;
	position: relative;
	bottom: -11px;
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