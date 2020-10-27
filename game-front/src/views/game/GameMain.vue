<template>
    <div>
        <canvas id="jsCanvas" class="canvas" @mousedown="startPainting" @mousemove="onMouseMove" @mouseup="stopPainting" @mouseleave="stopPainting" @click="handleCanvasClick" @contextmenu="handleCM"></canvas>
        
        <!--<span>{{x}}, {{y}}</span>-->
        <div class="controls">
            <div class="controls__range">
                <p style="float:left;">PEN 크기 : </p>
                <input type="range" style="margin-top:21px;" id="jsRange" min="0.1" max="5.0" value="2.5" step="0.1" @input="handleRangeChange"/>
            </div>
            <div class="controls__btns">
                <button id="jsMode" @click="handleModeClick">그리기</button>
                <button id="jsSave" @click="handleSaveClick">제출하기</button>
            </div>
            <div class="controls__colors" id="jsColors">
                <div class="controls__color jsColor" style="background-color: #2c2c2c" @click="handleColorClick"></div>
                <div class="controls__color jsColor" style="background-color: white" @click="handleColorClick"></div>
                <div class="controls__color jsColor" style="background-color: #ff3b30" @click="handleColorClick"></div>
                <div class="controls__color jsColor" style="background-color: #ff9500" @click="handleColorClick"></div>
                <div class="controls__color jsColor" style="background-color: #ffcc00" @click="handleColorClick"></div>
                <div class="controls__color jsColor" style="background-color: #4cd963" @click="handleColorClick"></div>
                <div class="controls__color jsColor" style="background-color: #5ac8fa" @click="handleColorClick"></div>
                <div class="controls__color jsColor" style="background-color: #0579ff" @click="handleColorClick"></div>
                <div class="controls__color jsColor" style="background-color: #5856d6" @click="handleColorClick"></div>
            </div>
        </div>
    </div>
</template>

<script>
import "../../assets/css/draw.css"
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import http from "../../util/http-common.js";

export default {
    components:{

    },
    data: () =>{
        return{
            x: 0,
            y: 0,
            ox: 0,
            oy: 0,
            nx: 0,
            ny: 0,
            isDrawing: false,
            cancas: null,
            ctx: null,
            colors: null,
            range: null,
            mode: null,
            saveBtn: null,
            INITIAL_COLOR: "#2c2c2c",
            CANVAS_SIZE: 500,
            painting: false,
            filling: false,
            image:null,
            link:null,
            recvList: [],
        }
    },
    created(){
         this.connect()
    },
    mounted(){
        this.canvas = document.getElementById("jsCanvas");
        this.ctx = this.canvas.getContext('2d');
        this.colors = document.getElementsByClassName("jsColor");
        this.range = document.getElementById("jsRange");
        this.mode = document.getElementById("jsMode");
        this.saveBtn = document.getElementById("jsSave");

        this.canvas.width = this.CANVAS_SIZE;
        this.canvas.height = this.CANVAS_SIZE;

        this.ctx.fillStyle = "white";
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
        this.ctx.strokeStyle = this.INITIAL_COLOR;
        this.ctx.fillStyle = this.INITIAL_COLOR;
        this.ctx.lineWidth = 2.5;

        this.painting = false;
        this.filling = false;

    },
    methods:{
         connect() {
            const serverURL= "http://localhost:8000"
            let socket = new SockJS(serverURL);
            this.stompClient = Stomp.over(socket);
            // console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
            this.stompClient.connect(
                {},
                frame => {
                // 소켓 연결 성공
                this.connected = true;
                // console.log('소켓 연결 성공', frame);
                // 서버의 메시지 전송 endpoint를 구독합니다.
                // 이런형태를 pub sub 구조라고 합니다.
                // console.log(this.teamId)
                this.stompClient.subscribe("/topic/"+"1", res => {
                 console.log("구독으로 받은 메시지 입니다.", res.body)

                    // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
                    console.log(JSON.parse(res.body))

                    let r = JSON.parse(res.body)
                    
                    this.receiveMouseMove(r.x1, r.y1, r.x2, r.y2, r.color, r.width, r.mode);
                    
                    //this.recvList.push(JSON.parse(res.body))
                    // this.scrolltoBottom()
                });
            },
                error => {
                // 소켓 연결 실패
                // console.log('소켓 연결 실패', error);
                this.connected = false;
                }
            );        
        },
        receiveMouseMove(x1, y1, x2, y2, color, width, mode){
            //console.log("painting : ", this.painting)
            //console.log("filling : ", this.filling)
            //console.log("mode : ", mode)

            this.ctx.strokeStyle = color;
            this.ctx.fillStyle = color;
            this.ctx.lineWidth = width;
            
            this.ctx.beginPath();
            this.ctx.moveTo(x1, y1);
            this.ctx.lineTo(x2, y2);
            this.ctx.stroke();
        },
        send() {
            if (this.stompClient && this.stompClient.connected) {
                const msg = { 
                    x1: this.ox,
                    y1: this.oy,
                    x2: this.nx,
                    y2: this.ny,
                    color: this.ctx.fillStyle,
                    width: this.ctx.lineWidth,
                    mode: this.filling
                };
                this.stompClient.send("/app/topic/" + "1",JSON.stringify(msg),{});
            }
         },
        stopPainting(){
            this.painting = false;
        },
        startPainting(e){
            this.painting = true;
            this.ox = e.offsetX;
            this.oy = e.offsetY;
        },
        onMouseMove(e){
            let x = e.offsetX;
            let y = e.offsetY;
            this.x = x;
            this.y = y;
        
            if(!this.painting) {
                this.ctx.beginPath();
                this.ctx.moveTo(x, y);
            } else {
                this.nx = x;
                this.ny = y;
                this.send();
                this.ox = this.nx;
                this.oy = this.ny;
                this.ctx.lineTo(x, y);
                this.ctx.stroke();
            }
        },
        handleColorClick(e){
            let color = e.target.style.backgroundColor;
            this.ctx.strokeStyle = color;
            this.ctx.fillStyle = color;
        },
        handleRangeChange(e){
            let size = e.target.value;
            this.ctx.lineWidth = size;
        },
        handleModeClick(){
            if(this.filling === true){
                this.filling = false;
                this.mode.innerText = "그리기";
            } else{
                this.filling = true;
                this.mode.innerText = "채우기";
            }
        },
        handleCanvasClick(){
            if(this.filling){
                this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
            }
        },
        handleCM(){
            event.preventDefault();
        },
        handleSaveClick(){
            this.image = this.canvas.toDataURL();
            this.link = document.createElement("a");
            this.link.href = this.image;
            this.link.download = "DrawBoard";
            this.link.click();
        }
    }
};
</script>
