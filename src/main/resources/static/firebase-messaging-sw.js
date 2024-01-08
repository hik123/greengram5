// Scripts for firebase and firebase messaging
importScripts("https://www.gstatic.com/firebasejs/9.0.0/firebase-app-compat.js");
importScripts("https://www.gstatic.com/firebasejs/9.0.0/firebase-messaging-compat.js");

// Initialize the Firebase app in the service worker by passing the generated config
const firebaseConfig = {
    apiKey: "AIzaSyAZpKT1QDpaA0402N8Qeap9vtx4X03mI3s",
    authDomain: "greengram-1a40d.firebaseapp.com",
    projectId: "greengram-1a40d",
    storageBucket: "greengram-1a40d.appspot.com",
    messagingSenderId: "1022026277124",
    appId: "1:1022026277124:web:da1b99102748be29b83051"    
};

firebase.initializeApp(firebaseConfig);


// Retrieve firebase messaging
const messaging = firebase.messaging();

/*
self.addEventListener('push', function (event) {
  const data = JSON.parse(event.data.text());
  
  event.waitUntil(async function() {
      for (const client of await self.clients.matchAll()) {
        console.log('ddd');
          client.postMessage(data);
      }

      let title = '';
      let body = '';
      switch(data.notification.title) {
        case 'dm':
          title = 'Direct Message';
          const jsonData = JSON.parse(data.notification.body);
          body = jsonData.msg;
          break;
      }

      self.registration.showNotification( title, {
          body: body
      });

  }());

});
*/

messaging.onBackgroundMessage(function(payload) {
 // console.log('Received background message ', payload);
 // Customize notification here
  /*
 self.clients.matchAll({
   type: 'window',
   includeUncontrolled: true
 }).then(all => all.forEach(client => {
   console.log('client',client)
   client.postMessage(payload);
   // client.postMessage("Responding to ");
  }))
 */
 const notificationTitle = payload.notification.title;
  
  let title = '';
  let body = '';
  switch(notificationTitle) {
    case 'dm':
      title = 'Direct Message222'
      const jsonData = JSON.parse(payload.notification.body);
      body = jsonData.msg;
      break;
  }

  const notificationOptions = {
    body: body,
  };

  self.registration.showNotification(title, notificationOptions);
});

/*
Uncaught (in promise) DOMException: Failed to execute 'subscribe' on 'PushManager': Subscription failed - no active Service Worker
    at getPushSubscription (http://localhost:3000/static/js/bundle.js:68376:37)
    at async getTokenInternal (http://localhost:3000/static/js/bundle.js:68293:28)


*/

