let errorAlert = document.getElementById("error-alert");
let nameInput = document.getElementById("subject-name");
let submitBtn = document.getElementById("submit-btn");

submitBtn.addEventListener("click", (e) => {
    localStorage.setItem("subjectName", nameInput.value);
})

let storedName = localStorage.getItem("subjectName");

if (errorAlert !== null) {
    if (storedName !== null) {
        nameInput.value = storedName;
    }
}