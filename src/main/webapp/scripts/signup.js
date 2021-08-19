let password1 = document.getElementById("password");
let password2 = document.getElementById("confirm-password");
let signupBtn = document.getElementById("sign-up-btn");
let error_message = document.getElementById("password-mismatch-alert");

signupBtn.addEventListener("click", (event) => {
    error_message.style.display = "none";
    console.log("Event fired");
    if (password1.value !== password2.value) {
        console.log("Password mismatch");
        event.preventDefault();
        error_message.style.display = "block";
        window.scrollTo(0, 0);
    }
})