<template>
    <div class=" d-flex flex-column align-items-center">
        <div>
            <img src="../../assets/images/Logo.jpg">
        </div>
        <h5 class="mt-2 mb-5" style="color:#464545">
            내 마음을 그려줘^^
        </h5>
    </div>
    
</template>

<script>
import LogoImage from "../../assets/images/Logo.jpg";
export default {
    name: "Logo",
    data: () => {
        return { LogoImage };
    }
}
</script>