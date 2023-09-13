<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="../assets/css/style.css"></link>
    <link href="https://cdn.jsdelivr.net/npm/remixicon@2.5.0/fonts/remixicon.css" rel="stylesheet">
    <script src="../assets/js/login.js" defer></script>
</head>
<body>
    <div class="nav-container">
        <h1 class="title">BIG PHARMA</h1>
    </div>
    <div class="login-container">
        <div class="login-box">
            <i class="ri-capsule-line"></i>
            <form method="post">
                <!-- 
                    id
                    password

                    submit
                 -->

                 <input pattern="^[0-9]{1,3}$" title="Please enter a digit. Min 1, Max 999" class="input" type="text" name="userId" placeholder="User ID" autocomplete="off" />
                 <input class="input" type="password" name="password" placeholder="Password" autocomplete="off" />
                 <p><a href="#forgotpasswordformhere">Forgot Password?</a></p>
                 <input class="submit" type="submit" name="login" value="Login" />
                 <input class="submit" type="submit" name="register" value="Register" />
            </form>
        </div>
    </div>
</body>
</html>