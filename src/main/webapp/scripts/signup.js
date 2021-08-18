let password1 = document.getElementById("password");
let password2 = document.getElementById("confirm-password");
let signupBtn = document.getElementById("sign-up-btn");
let error_message = document.getElementById("password-mismatch-alert");

signupBtn.addEventListener("click", (event) => {
    error_message.style.display = "none";
    if (password1.value !== password2.value) {
        event.preventDefault();
        error_message.style.display = "flex";
        window.scrollTo(0, 0);
    }
})