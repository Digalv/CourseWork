document.querySelector('.join').addEventListener('click', function() {
    if (document.querySelector('.form').value == "AT-211") {
        document.querySelector('.join').setAttribute("href", "main.html")
    } else {
        document.querySelector('.incorrect').style.display = "block";
    }
})