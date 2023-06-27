// Push通知を受け取ると呼ばれる
self.addEventListener("push", (event) => {
  const data = event.data.json();
  const title = data.notification.title || "無題";
  const message = data.notification.body || "メッセージが届いています。";
  event.waitUntil(
    self.registration.showNotification(title, {
      body: message,
      data: {
        link: data.data.link,
      },
    })
  );
});

// Push通知をクリックすると呼ばれる
self.addEventListener("notificationclick", (event) => {
  event.notification.close();
  clients.openWindow(event.notification.data.link);
});
