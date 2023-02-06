# Customview-android [![](https://jitpack.io/v/kamrul3288/customview-android.svg)](https://jitpack.io/#kamrul3288/customview-android)

A simple library that helps to create differ type of shape and stroke without creating drawable file. Also collection of complex custom view

```gradle
allprojects {
  repositories {
    maven {url 'https://jitpack.io'}
  }
}
```
### Step 2. Add the dependency
```gradle
dependencies {
  implementation "com.github.kamrul3288:customview-android:1.0.3"
}
```

```xml
 <com.iamkamrul.button.ButtonRegular
        app:btn_background_color="@color/teal_200"
        app:btn_border_radius="15dp"
        app:btn_background_shape="rectangle"
        android:text="Hello"
        android:layout_margin="16dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```
