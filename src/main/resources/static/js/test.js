const baseUrl = "http://localhost:8080";

async function registerUser(event) {
    console.log("clicked")
    event.preventDefault();
    try {
        const email = document.getElementById("register-email").value;
        const password = document.getElementById("register-password").value;
        const response = await(fetch(baseUrl + "/users/register", {
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

async function loginUser(event) {
    event.preventDefault();
    const email = document.getElementById("login-email").value;
    const password = document.getElementById("login-password").value;
    try {
        const response = await(fetch(baseUrl + "/users/login", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        }));
        if (response.ok) {
            const data = await response.json();
            const accessToken = data.accessToken;
            const refreshToken = data.refreshToken;
            localStorage.setItem("refreshToken", refreshToken)
            localStorage.setItem("accessToken", accessToken);
            console.log(data);
        } else {
            const error = await response.json();
            alert(error.message);
        }
    } catch(exception) {
        console.log(exception)
    }
}

document.getElementById("login-user").addEventListener("click", loginUser);

async function getProperties(event) {
    event.preventDefault();

    const response = await(fetch(baseUrl + "/properties", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("accessToken")
        }
    }));
    if (response.ok) {
        const data = await response.json();
        console.log(data);
    } else {
        const error = await response.json();
        alert(error.message);
    }
}

document.getElementById("get-properties").addEventListener("click", getProperties)

async function refreshToken() {
    const response = await(fetch(baseUrl + "/auth/refresh", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            refreshToken: localStorage.getItem("refreshToken")
        })
    }));
    if (response.ok) {
        const data = await response.json();
        const token = data.accessToken;
        console.log(data);
        localStorage.setItem("accessToken", token)
    } else {
        const error = await response.json();
        alert(error.message);
    }
}
document.getElementById("refresh-token").addEventListener("click", refreshToken);