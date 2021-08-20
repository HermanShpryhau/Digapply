$(document).ready(function () {
    $('#table').DataTable({
        language: {
            url: localStorage.getItem("table-lang")
        }
    });
});