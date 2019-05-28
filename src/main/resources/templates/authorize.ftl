<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
  <meta charset="UTF-8">
  <title>Authorization</title>
  <script>
    var queryParams = {};
    if (window.location.href.split("?")[1]) {
      var queryString = window.location.href.split("?")[1];
      var queryValues = queryString.split("&")
      queryValues.forEach(function (value) {
        var result = value.split("=");
        if (result) {
          queryParams[result[0]] = result[1]
        }
      })
    }
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
      if (xhr.status === 200 && xhr.readyState === 4) {
        var response = JSON.parse(xhr.responseText).response;
        var link = document.createElement('link');
        link.id = 'id2';
        link.rel = 'stylesheet';
        link.href = response.config.themeCss.webview
        link.onload = function () {
          document.querySelector('#contentLoader').style.setProperty('display', 'none', 'important');
          document.getElementById("chatBoxContainer").classList.remove("animated-background");
          document.querySelector('#authorize-page').style.setProperty('display', 'flex', 'important');
        }
        document.head.appendChild(link);
        document.documentElement.style.setProperty('--primary-color', response.config.color);
        document.getElementById("bank-logo").src = 'https://s3-ap-southeast-1.amazonaws.com/triniti-ai/' + response.config.iconKey;
      } else {
          document.querySelector('#contentLoader').style.setProperty('display', 'none', 'important');
          document.getElementById("chatBoxContainer").classList.remove("animated-background");
          document.querySelector('#authorize-page').style.setProperty('display', 'flex', 'important');
      }
    };
    xhr.open('GET', 'https://' + queryParams.botDomain + '/portal/api/bot/' + queryParams.botCode + '/config');
    xhr.send();
    function onSubmit() {
      document.getElementById("user_oauth_approval").value = false;
    }
  </script>
</head>
<style>

  html{
        padding: 0px;
        margin: 0px;
    }

    .title {
        background-color: #E9686B;
        height: 50px;
        padding-left: 20%;
        padding-right: 20%;
        color: white;
        line-height: 50px;
        font-size: 18px;
    }
    .title-left{
        float: right;
    }
    .title-right{
        float: left;
    }
    .title-left a{
        color: white;
    }
    .container{
        clear: both;
        text-align: center;
    }

    .btn-primary {
    color: #fff;
    background-color: var(--primary-color);
    border-color: var(--primary-color);
    }

    .btn-primary:hover {
        color: #fff;
        background-color: var(--primary-color);
        border-color: var(--primary-color);
    }

    .btn-primary:active {
        color: #fff;
        background-color: var(--primary-color);
        border-color: var(--primary-color);
    }

    .btn-primary:focus {
        color: #fff;
        background-color: var(--primary-color);
        border-color: var(--primary-color);
    }

    .login-form .bank-login-btn {
        border-radius: 100px;
        background-color: #183d44;
        border-color: #183d44;
        margin-top: 20px;
        position: relative;
        width: 45% !important;
        font: inherit;
         padding: 10px;
         cursor: pointer;
        font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    }

    .login-form .bank-login-btn .btnloader
    {
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
    }

    .login-form .bank-login-btn:focus,
    .login-form .bank-login-btn:hover,
    .login-form .bank-login-btn:hover:focus {
        border-radius: 100px;
        background-color: #183d44;
        border-color: #183d44;
        outline: none;
    }
    #authorize-page{
      display: none !important;
      align-items: center;
      flex-direction: column;
    }
    #chatBoxContainer
    {
    position: relative;
    }
    .animated-background {
    animation-duration: 1s;
    animation-fill-mode: forwards;
    animation-iteration-count: infinite;
    animation-name: placeHolderShimmer;
    animation-timing-function: linear;
    background: #f6f7f8;
    background: linear-gradient(to right, #eeeeee 8%, #dddddd 18%, #eeeeee 33%);
    background-size: 800px 104px;
    height: 120px;
    position: relative;
}

.background-masker {
    background: #fff;
    position: absolute;
    padding-top: 7px;
    line-height: 22px;
}


/* Every thing below this is just positioning */

.background-masker.header-top,
.background-masker.header-bottom,
.background-masker.subheader-bottom {
    top: 0;
    left: 40px;
    right: 0;
    height: 10px;
}

.background-masker.header-left,
.background-masker.subheader-left,
.background-masker.header-right,
.background-masker.subheader-right {
    top: 0px;
    left: 40px;
    height: 18px;
    width: 10px;
}

.background-masker.header-bottom {
    top: 11px;
    height: 20px;
    width: 87%;
}

.background-masker.subheader-left,
.background-masker.subheader-right {
    top: 18px;
    height: 22px;
}

.background-masker.header-right,
.background-masker.subheader-right {
    width: auto;
    left: 300px;
    right: 0;
}

.background-masker.subheader-right {
    left: 230px;
    height: 16px;
    top: 20%;
}

.background-masker.subheader-bottom {
    top: 10px;
    height: 10px;
}

.background-masker.content-top,
.background-masker.content-second-line,
.background-masker.content-third-line,
.background-masker.content-second-end,
.background-masker.content-third-end,
.background-masker.content-first-end {
    top: 40px;
    left: 0;
    right: 0;
    height: 6px;
    line-height: 25px;
}

.background-masker.content-top {
    height: 20px;
}

.background-masker.content-first-end,
.background-masker.content-second-end,
.background-masker.content-third-end {
    width: auto;
    left: 380px;
    right: 0;
    top: 60px;
    height: 8px;
}

.background-masker.content-second-line {
    top: 68px;
    height: 17px;
}

.background-masker.content-second-end {
    left: 420px;
    top: 74px;
}

.background-masker.content-third-line {
    top: 95px;
    height: 15px;
}

.background-masker.content-third-end {
    top: 91%;
    width: 103px;
    left: 72%;
    height: 11px;
}

#contentLoader {
    margin-top: 11%;
}

.loader {
    cursor: none;
    opacity: 0.5;
}

.timeline-item {
    background: #fff;
    border-radius: 3px;
    padding: 15px;
    margin: 0 auto;
    max-width: 472px;
    min-height: 200px;
    overflow: scroll;
}
 .bank-logo-container {
    padding: 6%;
    background-color: #183d44;
    border-radius: 50px;
    display: flex;
    justify-content: center;
  }
  .bank-logo {
    width: 70px;
  }
   #netBanking{
    width: 100%;
  }

</style>

<body class="timeline-item">
  <div class="container login-form">
    <div id="chatBoxContainer" class="animated-background">
      <div id="contentLoader">
        <div class="background-masker header-left"></div>
        <div class="background-masker header-right"></div>
        <div class="background-masker header-bottom"></div>
        <div class="background-masker subheader-left"></div>
        <div class="background-masker subheader-right"></div>
        <div class="background-masker subheader-bottom"></div>
        <div class="background-masker content-top"></div>
        <div class="background-masker content-first-end"></div>
        <div class="background-masker content-second-line"></div>
        <div class="background-masker content-second-end"></div>
        <div class="background-masker content-third-line"></div>
        <div class="background-masker content-third-end"></div>
      </div>
    </div>
    <div id="authorize-page">
      <div class="bank-logo-container">
        <img alt="bank-logo" src="https://s3-ap-southeast-1.amazonaws.com/bizapps-admin/assets/active.png" class="bank-logo" id="bank-logo" />
      </div>
      <h3>Authorization required</h3>
      <p>Authorize one.active.ai to access your account and profile information</p>
      <form method="post" action="${authorizeUrl}" style="width: 100%;">
        <input type="hidden" id="user_oauth_approval" name="user_oauth_approval" value="true">
        <button class="btn btn-primary btn-block bank-login-btn" type="submit">Authorize</button>
        <button class="btn btn-primary btn-block bank-login-btn" onclick="onSubmit(event)" type="submit">Cancel</button>
      </form>
    </div>
  </div>
</body>

</html>