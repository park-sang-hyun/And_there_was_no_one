<template>
    <div>
        <canvas id="jsCanvas" class="canvas" @mousedown="startPainting" @mousemove="onMouseMove" @mouseup="stopPainting" @mouseleave="stopPainting" @click="handleCanvasClick" @contextmenu="handleCM"></canvas>
        
        <span>{{x}}, {{y}}</span>
        <div class="controls">
            <div class="controls__range">
                <p style="float:left;">PEN SIZE : </p>
                <input type="range" style="margin-top:21px;" id="jsRange" min="0.1" max="5.0" value="2.5" step="0.1" @input="handleRangeChange"/>
            </div>
            <div class="controls__btns">
                <button id="jsMode" @click="handleModeClick">Pen</button>
                <button id="jsSave" @click="handleSaveClick">Save</button>
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
import "../../assets/css/draw.css";
//import http from "../../util/http-common.js";

export default {
    components:{

    },
    data: () =>{
        return{
            x: 0,
            y: 0,
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
        }
    },
    created(){

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
        stopPainting(){
            this.painting = false;
        },
        startPainting(){
            this.painting = true;
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
            if(this.filling===true){
                this.filling = false;
                this.mode.innerText = "Pen";
            } else{
                this.filling = true;
                this.mode.innerText = "Paint";
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
            this.link.download = "PaintJS[🖼]";
            this.link.click();
        }
    }
};


</script>


