let errorAlert = document.getElementById("error-alert");
let emailInput = document.getElementById("emailInput");
let submitBtn = document.getElementById("submit-btn");

submitBtn.addEventListener("click", (event) => {
    localStorage.setItem("signin-email", emailInput.value);
})

let storedEmail = localStorage.getItem("signin-email");

if (errorAlert !== null) {
    if (storedEmail !== null) {
        emailInput.value = storedEmail;
    }
}
