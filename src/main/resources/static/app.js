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
  var text = document.getElementById("text").value;
  var type = document.getElementById("tyoe1").value;
  stompClient.send(
    "/app/application",
    {},
    JSON.stringify({ milestoneId: 1, type: type, title: text, from: "admin" })
  );
}

function sendPrivateMessage() {
  var text = document.getElementById("privateText").value;
  var type = document.getElementById("type2").value;
  var to = document.getElementById("to").value;
  stompClient.send(
    "/app/private",
    {},
    JSON.stringify({
      milestoneId: 1,
      type: type,
      title: text,
      to: to,
      from: "admin",
    })
  );
}

function show(notification) {
  var response = document.getElementById("messages");
  var template = document.getElementById("toast-template");

  let notificationArea = template.cloneNode(true);

  let fromUser = notificationArea.querySelector(".toast-from-user");
  fromUser.textContent = notification.from;

  let timeStamp = notificationArea.querySelector(".elapsed-time");
  var now = new Date();
  timeStamp.textContent = "just now";
  timeStamp.dataset.receptionAt = now;

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
