# SideslipBack

## 一：添加依赖

```
allprojects {
	repositories {
		...
		maven { url 'https://www.jitpack.io' }
	}
}
```

```
dependencies {
    compile 'com.github.jcdee:SideslipBack:1.0'
}
```

## 二：直接在Activity中使用
> SideslipBackHelper.inject(this);


```
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    SideslipBackHelper.inject(this);
}
```

### 注意：
> 需要在AndroidManifest中设置需要侧滑退出的Activity的主题


```
<activity
    android:name=".SideslipBackActivity"
    android:theme="@style/AppTheme.SideslipTheme" />
```

#### styles添加：
```
<resources>
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
    
    <style name="AppTheme.SideslipTheme">
        <item name="android:windowIsTranslucent">true</item>
        <item name="android:windowBackground">@android:color/transparent</item>
    </style>
</resources>
```