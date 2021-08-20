let password1 = document.getElementById("password");
let password2 = document.getElementById("confirm-password");
let signupBtn = document.getElementById("sign-up-btn");
let errorMessage = document.getElementById("password-mismatch-alert");

let errorAlert = document.getElementById("error-alert");
let firstNameInput = document.getElementById("first-name");
let lastNameInput = document.getElementById("last-name");
let emailInput = document.getElementById("email")

signupBtn.addEventListener("click", (event) => {
    errorMessage.style.display = "none";
    if (password1.value !== password2.value) {
        event.preventDefault();
        errorMessage.style.display = "block";
        window.scrollTo(0, 0);
    }
    localStorage.setItem("firstName", firstNameInput.value);
    localStorage.setItem("lastName", lastNameInput.value);
    localStorage.setItem("email", emailInput.value);
})

let storedName = localStorage.getItem("firstName");
let storedLastName = localStorage.getItem("lastName");
let storedEmail = localStorage.getItem("email");

if (errorAlert !== null) {
    if (storedName !== null) {
        firstNameInput.value = storedName;
    }

    if (storedLastName !== null) {
        lastNameInput.value = storedLastName;
    }

    if (storedEmail !== null) {
        emailInput.value = storedEmail;
    }
}