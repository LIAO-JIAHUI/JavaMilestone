var stompClient = null;
var privateStompClient = null;
// ALL受け取り用のソケット
var socket = new SockJS("/ws");
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
  console.log(frame);
  stompClient.subscribe("/all/messages", function (result) {
    show(JSON.parse(result.body));
  });
});

// Private受け取り用のソケット
socket = new SockJS("/ws");
privateStompClient = Stomp.over(socket);
privateStompClient.connect({}, function (frame) {
  console.log(frame);
  privateStompClient.subscribe("/user/specific", function (result) {
    console.log(result.body);
    show(JSON.parse(result.body));
  });
});

function sendMessage() {
  var text = document.getElementById("title").value;
  var type = document.getElementById("type").value;
  stompClient.send(
    "/app/application",
    {},
    JSON.stringify({ milestoneId: 1, type: type, title: text, from: "admin" })
  );
}

function sendPrivateMessage() {
  var text = document.getElementById("title").value;
  var type = document.getElementById("type").value;
  var to = document.getElementById("to").value;
  stompClient.send(
    "/app/private",
    {},
    JSON.stringify({
      milestoneId: 1,
      type: type,
      title: "[Private]" + text,
      to: [to],
      from: "admin",
    })
  );
}

setInterval(updateElapsedTime, 5000);

function updateElapsedTime() {
  console.log();
  const timeStamps = document
    .getElementById("messages")
    .getElementsByClassName("elapsed-time");
  const now = moment();
  if (timeStamps.length == 0) {
    return;
  }
  for (let i = 0; i < timeStamps.length; i++) {
    let receivedAt = moment(timeStamps[i].dataset.receivedAt);
    let deff = now.diff(receivedAt, "minute");
    console.log("deff", deff);
    if (deff > 1440) {
      timeStamps[i].innerHTML = now.diff(receivedAt, "days") + " days ago";
    } else if (deff > 60) {
      timeStamps[i].innerHTML = now.diff(receivedAt, "hours") + " hours ago";
    } else if (deff < 1) {
      timeStamps[i].innerHTML = now.diff(receivedAt, "seconds") + " second ago";
    } else {
      timeStamps[i].innerHTML = deff + " minutes ago";
    }
  }
}

function show(notification) {
  var response = document.getElementById("messages");
  var template = document.getElementById("toast-template");

  let notificationArea = template.cloneNode(true);

  let fromUser = notificationArea.querySelector(".toast-from-user");
  fromUser.textContent = notification.from;

  let timeStamp = notificationArea.querySelector(".elapsed-time");
  var today = moment();
  timeStamp.textContent = "just now";

  timeStamp.dataset.receivedAt = today.format("YYYY-MM-DDTHH:mm:ss");

  let bodyMessage = notificationArea.querySelector(".toast-body");

  switch (notification.type) {
    case "create":
      bodyMessage.textContent = notification.title + "を作成しました";
      break;
    case "edit":
      bodyMessage.innerHTML =
        "「<a href='/milestones/" +
        notification.milestoneId +
        "'>" +
        notification.title +
        "</a>」を編集しました";
      break;
    case "delete":
      bodyMessage.textContent = notification.title + "を削除しました";
      break;
  }

  response.appendChild(notificationArea);

  let toast = new bootstrap.Toast(notificationArea, { autohide: false });
  toast.show();
}

{
  /* <div
          class="toast"
          roll="alert"
          aria-live="assertive"
          aria-atomic="true"
        >
          <div class="toast-header">
            <strong class="me-auto">user</strong>
            <small class="elapsed-time">just now</small>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="toast"
              aria-label="Close"
            ></button>
          </div>
          <div class="toast-body">
            「<a th:href="@{/milestones/{id}(id=3)}">マイルストーンの削除</a
            >」を作成しました。
          </div>
        </div> */
}
