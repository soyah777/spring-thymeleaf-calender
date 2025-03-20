
document.addEventListener("DOMContentLoaded", function(){
    let baseUrl = window.location.origin+"/calendar";

    document.addEventListener("keydown" , function(event){

        let currentYear = parseInt(document.getElementById("yearSelect").value);
        let currentMonth = parseInt(document.getElementById("monthSelect").value);


        if(event.key === "ArrowLeft"){
            let newYear = (currentMonth === 1)?currentYear-1:currentYear;
            let newMonth = (currentMonth === 1)?12:currentMonth-1;
            window.location.href = `${baseUrl}?year=${newYear}&month=${newMonth}`;

        }else if(event.key === "ArrowRight"){
            let newYear = (currentMonth === 12)?currentYear+1:currentYear;
            let newMonth = (currentMonth === 12)?1:currentMonth+1;
            window.location.href = `${baseUrl}?year=${newYear}&month=${newMonth}`;
        }
    });


    document.getElementById("yearSelect").addEventListener("change",updateCalendar);
    document.getElementById("monthSelect").addEventListener("change" ,updateCalendar);

    function updateCalendar(){
        let selectedYear = document.getElementById("yearSelect").value;
        let selectedMonth = document.getElementById("monthSelect").value;
        window.location.href = `${baseUrl}?year=${selectedYear}&month=${selectedMonth}`;
    }

    document.querySelectorAll('.day').forEach(dayElement =>
        dayElement.addEventListener('click',function(){
            const year = dayElement.getAttribute('data-year');
            const month = dayElement.getAttribute('data-month');
            const date = dayElement.getAttribute('data-date');

            const url = '/todo-details?year=' + year + '&month=' + month + '&date=' + date;

            window.location.href = url;
        })
    )

    // 「←」で前月へ移動
    function changeMonthToPrev(){
        let currentYear = parseInt(document.getElementById("yearSelect").value);
        let currentMonth = parseInt(document.getElementById("monthSelect").value);

        let newYear = (currentMonth === 1)? currentYear-1 : currentYear;
        let newMonth = (currentMonth === 1)? 12 : currentMonth-1;
        window.location.href = `${baseUrl}?year=${newYear}&month=${newMonth}`;
    }

    const prevMonthButton = document.querySelector(".prev");

    prevMonthButton.addEventListener("click",changeMonthToPrev);
    prevMonthButton.addEventListener("touchstart",changeMonthToPrev,{passive:true});



    // 「→」で翌月へ移動
    function changeMonthToNext(){
        let currentYear = parseInt(document.getElementById("yearSelect").value);
        let currentMonth = parseInt(document.getElementById("monthSelect").value);

        let newYear = (currentMonth === 12) ? currentYear+1 : currentYear;
        let newMonth = (currentMonth === 12) ? 1 : currentMonth+1;

        window.location.href = `${baseUrl}?year=${newYear}&month=${newMonth}`;
    }

    const nextMonthButton = document.querySelector(".next");

    nextMonthButton.addEventListener("click",changeMonthToNext);
    nextMonthButton.addEventListener("touchstart",changeMonthToNext,{passive:true});

});