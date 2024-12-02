// Get the dialog and its elements
const registrationDialog = document.getElementById("registrationDialog");
const closeRegistrationBtn = registrationDialog.querySelector(".close-btn");
const dialogMessage = document.getElementById("dialogMessage");

// Function to show the registration success message
function showRegistrationSuccessMessage() {
    // Set the success message
    dialogMessage.innerHTML = "Registration successful! You can now log in.";

    // Display the dialog
    registrationDialog.style.display = "flex";
}

// Close the dialog when the close button is clicked
closeRegistrationBtn.addEventListener("click", () => {
    registrationDialog.style.display = "none";
});

// Close the dialog if the user clicks anywhere outside of the dialog
window.addEventListener("click", (event) => {
    if (event.target === registrationDialog) {
        registrationDialog.style.display = "none";
    }
});

// Automatically show the dialog after successful registration
window.onload = () => {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get("success") === "true") {
        showRegistrationSuccessMessage();
    }
};
