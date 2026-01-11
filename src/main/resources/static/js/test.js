async function registerUser(event) {
    console.log("clicked")
    event.preventDefault();
    try {
        const email = document.getElementById("register-email").value;
        const password = document.getElementById("register-password").value;
        const response = await(fetch("http://localhost:8080/users/register", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: email,
                password: password,

            }),
        }));
        if (response.ok) {
            const data = await response.json();
            console.log("Success: ", data)
        } else {
            const error = await response.json();
            alert(error.message)
        }
    } catch(exception) {
        alert(exception.message)
    }
}

document.getElementById("register-user").addEventListener("click", registerUser);