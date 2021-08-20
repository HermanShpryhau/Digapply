let errorAlert = document.getElementById("error-alert");
let nameInput = document.getElementById("first-name");
let surnameInput = document.getElementById("last-name");
let submitBtn = document.getElementById("submit-btn");

submitBtn.addEventListener("click", (e) => {
    localStorage.setItem("name", nameInput.value);
    localStorage.setItem("surname", surnameInput.value);
})

let storedName = localStorage.getItem("name");
let storedSurname = localStorage.getItem("surname");

if (errorAlert !== null) {
    if (storedName !== null) {
        nameInput.value = storedName;
    }
    if (storedSurname !== null) {
        surnameInput.value = storedSurname;
    }
}