## 안드로이드 스튜디오
 - [깃허브 연동하기](https://devmingsa.tistory.com/7)
 - [깃허브에서 프로젝트 가져오기](https://copycoding.tistory.com/81)
 - 소스 코드 보기 : ctrl + 마우스 클릭
 - 메인화면 조작하기
    1.  app -> src -> main -> AndroidManifest.xml 이동
    2. 아래 코드에서 `.NotMainActivity`를 원하는 **Activity** 이름으로 변경
  
```kotlin
    <activity android:name=".NotMainActivity">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />

            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>
```

 - 단위테스트 : 폴더 하나 만듬 -> 폴더 안에다 class 들을 만듬 -> 각 class의 method에 테스트할 method 코드를 작성 -> method 위에 `@Text` annotation 붙임 -> 폴더 run(ctrl + shift + F10)
 - 치환 : ctrl + shift + r
 - 변수의 클래스명 :
 > varname::class.simpleName
 > varname:class.qualifiedName
