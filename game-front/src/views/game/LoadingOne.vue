<template>
    <div id="LoadingOne">
        <p>A <b>scroll</b> (from the Old French <i>escroe</i> or <i>escroue</i>), also known as a <b>roll</b>, is a roll of papyrus, parchment, or paper containing writing.</p>
        <h2>Structure</h2>
        <p>A scroll is usually divided up into pages, which are sometimes separate sheets of papyrus or parchment glued together at the edges, or may be marked divisions of a continuous roll of writing material. The scroll is usually unrolled so that one page is exposed at a time, for writing or reading, with the remaining pages rolled up to the left and right of the visible page. It is unrolled from side to side, and the text is written in lines from the top to the bottom of the page. Depending on the language, the letters may be written left to right, right to left, or alternating in direction (boustrophedon).</p>
        <p>Some scrolls are simply rolled up pages; others may have wooden rollers on each end: Torah scrolls have rather elaborate rollers befitting their ceremonial function.</p>
        <h2>History of scroll use</h2>
        <p>Scrolls were the first form of editable record keeping texts, used in Eastern Mediterranean ancient Egyptian civilizations. Parchment scrolls were used by the Israelites among others before the codex or bound book with parchment pages was invented by the Romans, which became popular around the 1st century AD. Scrolls were more highly regarded than codices until well into Roman times, where they were usually written in single latitudinal column.</p>
        <p>The ink used in writing scrolls had to adhere to a surface that was rolled and unrolled, so special inks were developed. Even so, ink would slowly flake off of scrolls.</p>
        <h2>Rolls</h2>
        <p>Rolls recording UK Acts of Parliament held in the Parliamentary Archives, Palace of Westminster, London</p>
        <p>Shorter pieces of parchment or paper are called <i>rolls</i> or <i>rotuli</i>, although usage of the term by modern historians varies with periods. Historians of the classical period tend to use <i>roll</i> instead of <i>scroll</i>. Rolls may still be many meters or feet long, and were used in the medieval and Early Modern period in Europe and various West Asian cultures for manuscript administrative documents intended for various uses, including accounting, rent-rolls, legal agreements, and inventories. A distinction that sometimes applies is that the lines of writing in rotuli run across the width of the roll (that is to say, are parallel with any unrolled portion) rather than along the length, divided into page-like sections. Rolls may be wider than most scrolls, up to perhaps 60 cm or two feet wide. Rolls were often stored together in a special cupboard on shelves.</p>
        <p>A special Chinese form of short book, called the "whirlwind book," consists of several pieces of paper bound at the top with bamboo and then rolled up.</p>
        <h2>Scotland</h2>
        <p>In Scotland, the term <i>scrow</i> was used from about the 13th to the 17th centuries for scroll, writing, or documents in list or schedule form. There existed an office of Clerk of the Scrow (<i>Rotulorum Clericus</i>) meaning the Clerk of the Rolls or Clerk of the Register.</p>
        <h2>Replacement by the codex</h2>
        <p>The Romans invented the <i>codex</i> form of the book, folding the scroll into pages which made reading and handling the document much easier. Legend has it that Julius Caesar was the first to fold scrolls, concertina-fashion, for dispatches to his forces campaigning in Gaul. Scrolls were awkward to read if a reader wished to consult material at opposite ends of the document. Further, scrolls were written only on one side, while both sides of the codex page were used.</p>
        <p>Eventually, the folds were cut into sheets, or "leaves," and bound together along one edge. The bound pages were protected by stiff covers, usually of wood enclosed with leather. Codex is Latin for a "block of wood": the Latin <i>liber</i>, the root of "library," and the German <i>Buch</i>, the source of "book," both refer to wood. The codex was not only easier to handle than the scroll, but it also fit conveniently on library shelves. The spine generally held the book's title, facing out, affording easier organization of the collection.</p>
        <p>The term <i>codex</i> technically refers only to manuscript books - those that, at one time, were handwritten. More specifically, a codex is the term used primarily for a bound manuscript from Roman times up through the Middle Ages.</p>
        <p>From the fourth century on, the codex became the standard format for books, and scrolls were no longer generally used. After the contents of a parchment scroll were copied in codex format, the scroll was seldom preserved. The majority that did survive were found by archaeologists in burial pits and in the buried trash of forgotten communities.</p>
        <h2>Recent Torah scroll discovery</h2>
        <p>The oldest complete Torah scroll was discovered stored in an academic library in Bologna, Italy, by Professor Mauro Perani in 2013. It had been mislabeled in 1889 as dating from the 17th century, but Perani suspected it was actually older as it was written in an earlier Babylonian script. Two tests conducted by laboratories at Italy’s University of Salento and at the University of Illinois confirmed that the scroll dates from the second half of the 12th century to the first quarter of the 13th century. Ancient Torah scrolls are rare because when they are damaged they stop being used for liturgies and are buried.</p>
        <p>The scroll is made up of 58 sections of soft sheep leather. It is 36 meters long and 64 centimeters wide.</p>
        <h2>Modern technology</h2>
        <p>Modern technology may be able to assist in reading ancient scrolls. In January 2015, computer software may be making progress in reading 2,000-year-old Herculaneum scrolls, computer scientists report. After working for more than 10 years on unlocking the contents of damaged Herculaneum scrolls, researchers may be able to progress towards reading the scrolls, which cannot be physically opened.</p>
        <h2>See also</h2>
        <ul><li>Hanging scroll</li> <li>Handscroll</li> <li>Herculaneum papyri</li> <li>Paleography</li> <li>Vellum</li> <li>Woodblock printing</li></ul>
        <h2>Source</h2>
        <div><a href="https://en.m.wikipedia.org/wiki/Scroll">Scroll Wikipedia entry, retrieved 2020-02-21T23:06:04</a></div>
    </div>
</template>

<script>
export default {
    name: "LoadingOne",

    created() {
        [].forEach.call(document.querySelectorAll("p,li,h2,a"), function(el) {
            el.innerHTML = el.innerHTML
                .split(" ")
                .map(word => `<span>${word}</span>`)
                .join(" ");
        });
        // poor-man's lettering, or rather "wording"

        // autoscrolling "showcase"
        function autoscroll() {
            document.scrollingElement.scrollBy({ top: 100, behavior: "smooth" });
        }
        var INTRVL = setInterval(autoscroll, 100);
        function stop() {
            clearInterval(INTRVL);
        } 
        document.documentElement.addEventListener("mousemove", stop);
    },

    methods: {
    }
}
</script>

<style scoped>
body::after,
*::before,
span {
    position: sticky;
    top: 0em;
    bottom: 0em;
    white-space: nowrap;
    /* * nope /
    text-shadow: 0 0 1rem rgba(30,30,30,0.5);
    /*  seems to be performance disaster
    */
}
html {
    font-size: 15vh; /* never ever use fixed viewport-bound font-size anywhere in serious business, don't you dare! */
    max-width: 20em;
    margin: 0 auto;
    padding: 1rem;
    overflow: auto;
    position: relative;
    text-align: justify;
    scroll-behavior: smooth;
}
p, h2, ul, li, a, div {
    display: inline;
    font-size: 1rem;
}
h2::before { content: "§ "; }
p::before { content: "¶ "; }
li::before { content: "• "; }
body::after {
    content: "☙·E·O·F·❧";
    display: block;
    text-align: center;
}


html,
body {
    background-color: rgba(30, 30, 30, 1);
    color: rgba(200, 200, 200, 0.7);
}

h2 {
    color: rgba(255, 100, 100, 1);
}
b,
i {
    color: rgba(255, 100, 100, 0.7);
}
a {
    color: inherit;
}
body::after {
    color: black;
}
</style>