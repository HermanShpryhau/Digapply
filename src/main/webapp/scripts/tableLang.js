function setEnglish() {
    localStorage.setItem("table-lang", "https://cdn.datatables.net/plug-ins/1.10.25/i18n/English.json");
}

function setRussian() {
    localStorage.setItem("table-lang", "https://cdn.datatables.net/plug-ins/1.10.25/i18n/Russian.json");
}

if (localStorage.getItem("table-lang") === null) {
    localStorage.setItem("table-lang", "https://cdn.datatables.net/plug-ins/1.10.25/i18n/Russian.json");
}