import { initializeApp } from "https://www.gstatic.com/firebasejs/9.9.3/firebase-app.js";
import {
  getMessaging,
  getToken,
  onMessage,
} from "https://www.gstatic.com/firebasejs/9.9.3/firebase-messaging.js";
const firebaseConfig = {
  apiKey: "AIzaSyDlD6YR1vU8ELZ6KP42ok8OvZoka9XjkA0",
  authDomain: "milestone-app-842d2.firebaseapp.com",
  projectId: "milestone-app-842d2",
  storageBucket: "milestone-app-842d2.appspot.com",
  messagingSenderId: "323939545121",
  appId: "1:323939545121:web:53c46d8ae3d6bed108f360",
};

document.addEventListener("DOMContentLoaded", function () {
  // Initialize Firebase
  const app = initializeApp(firebaseConfig);
  const messaging = getMessaging(app);
  Notification.requestPermission().then((permission) => {
    if (permission === "granted") {
      console.log("Notification permission granted.");
      onMessage(messaging, (payload) => {
        console.log("Message received. ", payload);
      });
      getToken(messaging, {
        vapidKey:
          "BNoLXT8Er-kHDNZH5GyHYoTdY0_65TR4kWhYRQur2ZKBw4R1GyvmabYoCzukkziD8Z2xH4AZZJFDa91f97CgyyI",
      })
        .then((currentToken) => {
          if (currentToken) {
            document.getElementById("token").value = currentToken;
          }
        })
        .catch((e) => console.log(e));
    } else {
      // 通知を拒否した場合
      console.log("Unable to get permission to notify.");
    }
  });
});
