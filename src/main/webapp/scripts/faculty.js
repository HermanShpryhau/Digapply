let errorAlert = document.getElementById("error-alert");
let facultyNameInput = document.getElementById("faculty-name");
let placesCount = document.getElementById("places-count");
let shortDescription = document.getElementById("short-faculty-description");
let descriptionInput = document.getElementById("faculty-description");
let submitBtn = document.getElementById("submit-btn");

submitBtn.addEventListener("click", (event) => {
    localStorage.setItem("facultyName", facultyNameInput.value);
    localStorage.setItem("places", placesCount.value);
    localStorage.setItem("shortDescription", shortDescription.value);
    localStorage.setItem("description", descriptionInput.value);
})

let storedName = localStorage.getItem("facultyName");
let storedPlaces = localStorage.getItem("places");
let storedShortDescription = localStorage.getItem("shortDescription");
let storedDescription = localStorage.getItem("description");

if (errorAlert !== null) {
    if (storedName !== null) {
        facultyNameInput.value = storedName;
    }
    if (storedPlaces !== null) {
        placesCount.value = storedPlaces;
    }
    if (storedShortDescription !== null) {
        shortDescription.value = storedShortDescription;
    }
    if (storedDescription !== null) {
        descriptionInput.value = shortDescription;
    }
}