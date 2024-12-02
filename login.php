<?php
session_start();

// Redirect to index after successful login
header("Location: index.html");
exit();
?>


// Simulated login credentials (replace with your actual authentication logic)
$validUsername = "user123";
$validPassword = "pass123";

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $username = $_POST['username'];
    $password = $_POST['password'];

    if ($username === $validUsername && $password === $validPassword) {
        // Successful login
        $_SESSION['loggedin'] = true;
        $_SESSION['username'] = $username;

        // Redirect to the index page with a success message
        header("Location: index.html?login=success");
        exit();
    } else {
        // Failed login, redirect to login with error message
        header("Location: login.html?error=invalid");
        exit();
    }
}
?>
