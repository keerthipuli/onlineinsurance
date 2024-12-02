// Get all subscribe buttons
const subscribeButtons = document.querySelectorAll(".subscribe-btn");

// Get the dialog and its elements
const dialog = document.getElementById("confirmationDialog");
const closeBtn = dialog.querySelector(".close-btn");
const dialogMessage = document.getElementById("dialogMessage");

// Function to show the subscription confirmation
function showSubscriptionMessage(planName, formId) {
    const form = document.getElementById(formId);
    const name = form.querySelector("input[name='name']").value;
    const email = form.querySelector("input[name='email']").value;
    const duration = form.querySelector("input[name='duration']").value;

    // Format the message with line breaks
    const message = `
        <strong>Plan:</strong> ${planName}<br>
        <strong>Name:</strong> ${name}<br>
        <strong>Email:</strong> ${email}<br>
        <strong>Duration:</strong> ${duration} Years
    `;
    
    // Display the message in the dialog
    dialogMessage.innerHTML = message;
    
    // Show the dialog
    dialog.style.display = "flex";
}

// Add event listeners for subscribe buttons
subscribeButtons.forEach((button) => {
    button.addEventListener("click", () => {
        const planName = button.getAttribute("data-plan");
        const formId = button.getAttribute("data-form");
        showSubscriptionMessage(planName, formId); // Show the confirmation message
    });
});

// Close the dialog when the close button is clicked
closeBtn.addEventListener("click", () => {
    dialog.style.display = "none";
});

// Close the dialog if the user clicks anywhere outside of the dialog
window.addEventListener("click", (event) => {
    if (event.target === dialog) {
        dialog.style.display = "none";
    }
});
