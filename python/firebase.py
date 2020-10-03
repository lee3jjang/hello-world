import pyrebase

config = dict(
    apiKey="AIzaSyDaA0rzHpSLVAdgHgHI9cww8KBL_Z2EqCY",
    authDomain="vid-6b660.firebaseapp.com",
    databaseURL="https=//vid-6b660.firebaseio.com",
    projectId="vid-6b660",
    storageBucket="vid-6b660.appspot.com",
    messagingSenderId="1039428669667",
    appId="1:1039428669667:web:9506164aa09c11fcf375d6",
    measurementId="G-R88KGB9491"
)

firebase = pyrebase.initialize_app(config)
storage = firebase.storage()
path_on_cloud = 'images/foo.jpg'
path_local = 'img/img_5terre.jpg'
storage.child(path_on_cloud).put(path_local)

storage.child(path_on_cloud).download('.', 'test_down.jpg')