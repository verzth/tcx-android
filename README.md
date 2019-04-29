[![Release](https://jitpack.io/v/verzth/tcx-android.svg)](https://jitpack.io/com/github/verzth/tcx-android)
# TCX Android

TCX Android was build to help android developer connect to Backend base on [TCX Authentication](https://github.com/verzth/tcx).

> Keep in mind, this module **DOESN'T** manage handler to append automatically the credential, but just help you to generate the authentication credential.

### Dependencies
You may not need to add the dependencies to your gradle, it's given to you just to inform you that **TCX Android** using others dependencies.
```
implementation 'com.google.code.gson:gson:2.8.2'
implementation 'joda-time:joda-time:2.10.1'
implementation 'com.squareup.retrofit2:retrofit:2.5.0'
implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
```

### How to install
1. Add it to your root build.gradle with:
   ```
   allprojects {
       repositories {
           maven { url "https://jitpack.io" }
       }
   }
   ```
   
2. Add it to your app build.gradle with:
   ```
   dependencies {
       compile 'com.github.verzth:tcx-android:1.2.0@aar'
   }
   ```
### How to use
1. There are several methods used to initialize TCX Module.
   - TCX.**init**(String url, String ApplicationID, String PublicKey)
       ```
       public class BaseApplication extends Application {
           @Override
           public void onCreate() {
               super.onCreate();
               // Initialize TCX with given Credentials
               TCX.init("http://127.0.0.1/","DEMO","DEMO123");
           }
       }
       ```
   - TCX.**init**(String url, String ApplicationID, String PublicKey, String MasterToken)
      ```
      public class BaseApplication extends Application {
          @Override
          public void onCreate() {
              super.onCreate();
              // Initialize TCX with given Credentials
              TCX.init("http://127.0.0.1/","DEMO","DEMO123","DEMOKEY123");
          }
      }
      ```

2. Use TCX.**getInstance()** to get the default instance of your TCX.
   
   | No | Method | Return | Description |
   | :--: | :------ | :------: | :----------- |
   | 1 | TCX.**getID()** | String | Get Application ID |
   | 2 | TCX.**getPassToken()** | String | Get Application Pass token, it's used to generate time based token |
   | 3 | TCX.**getPassToken(HashMap<String,String> params)** | String | Get Application Pass token, it's used to generate parameter based token |
   | 4 | TCX.**getPass()** | String | Get Application Pass with no token |
   | 5 | TCX.**getPass(String token)** | String | Get Application Pass with given token |
   | 6 | TCX.**getMasterToken()** | String | Get authentication master token, encrypted with base64. Only needed on FTC authentication to bypass TWTC |
   | 6 | TCX.**getToken(OnTokenReceive callback)** | void | Get authentication token, encrypted with base64. Only needed on TWTC authentication |
   
3. Don't forget to add **tcx_datetime** on your parameter when you're using time based application pass,
   append it on your query parameters (GET, but accepted too on POST) or your request body(POST).
   
#### Interfaces
- OnTokenReceive.
  ```
  public interface OnTokenReceive {
      void onTokenReceive(AuthData data);
      void onFailed();
      void onError();
  }
  ```
  
#### Data Model
- AuthData.
  ```
  public class AuthData {
      public String token;
      public String refresh;
      @SerializedName("expired_at")
      public String expiredAt;
  }
  ```