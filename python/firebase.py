import firebase_admin
from firebase_admin import credentials, db

cred = credentials.Certificate('service-account-file.json')
firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://vid-6b660.firebaseio.com/'
})

ref = db.reference()
ref.update({'반원': '고슴도치'})

ref = db.reference('강좌/파이썬')
ref.update({'파이썬 레시피 웹 활용': 'complete'})
ref.update({'파이썬 괴식 레시피': 'proceeding'})

ref = db.reference()
ref.update({'수강자': ['구독자A','구독자B','구독자C','구독자D']})

ref = db.reference('없는 경로')
print(ref.get())

ref = db.reference('반원')
print(ref.get())

ref = db.reference('강좌/파이썬')
print(ref.get())

ref = db.reference('강좌')
print(ref.get())

ref = db.reference('수강자')
print(ref.get())