function Show(x) {
    x.classList.toggle("change");
    var Menu = document.getElementById("hamburgerMenu");
    if (Menu.style.display !== "block") {
        Menu.style.display = "block";
    } else {
        Menu.style.display = "none";
    }
}