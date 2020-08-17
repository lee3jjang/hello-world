## 공통
 - [화면분할키활성화](https://answers.microsoft.com/ko-kr/windows/forum/windows_10-start-win_desk/%EC%9C%88%EB%8F%84%EC%9A%B010/4f1776b3-d911-4bc8-b71a-2598be927b51)

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
 > 1) varname::class.simpleName  
 > 2) varname::class.qualifiedName
 - [data class](https://kychul98.tistory.com/92)
 - 코드 줄 이동 : alt + shift + ↑, ↓
 - 코드 복제 : ctrl + d
 - 코드 1줄 삭제 : ctrl + y
 - 탭 닫기 : ctrl + F4
 - 탭 이동 : ctrl + tab
 - [안드로이드 액티비티 생명주기](https://thinkground.studio/android-%EC%95%A1%ED%8B%B0%EB%B9%84%ED%8B%B0-%EC%83%9D%EB%AA%85%EC%A3%BC%EA%B8%B0-activity-lifecycle/)
 - [companion object](https://www.androidhuman.com/lecture/kotlin/2016/07/10/kotlin_companion_object/)
 - [open 키워드](https://androidtest.tistory.com/102)
 - setter, getter : alt + insert
 - [AVD S10 스킨](https://csc0705.tistory.com/60)
 - [VSCode에서 코틀린](https://ebbnflow.tistory.com/157)
   * [컴파일러](https://github.com/JetBrains/kotlin/releases/tag/v1.4.0)
