# AndroidAutoHideHeader
[ ![Download](https://api.bintray.com/packages/vcaen/maven/androidautohideheader/images/download.svg) ](https://bintray.com/vcaen/maven/androidautohideheader/_latestVersion)

A layout that hide the header when the body is scrolled down and reveal it when the header is scrolled up

## Demo

![Demo gif](https://raw.githubusercontent.com/vcaen/AndroidAutoHideHeader/master/example.gif)

## Usage

In you xml, add the '''AutoHideHeaderLayout''' : 

```xml
    <com.vcaen.androidautohideheader.AutoHideHeaderLayout
        android:id="@+id/autohideview"
       android:layout_width="match_parent"
       android:layout_height="match_parent"/>
``` 

Then in your java code : 
 ```java
    AutoHideHeaderLayout view  = (AutoHideHeaderLayout) findViewById(R.id.autohideview);
    ListView listView = new ListView(this);
    view.setHeader(R.layout.header); // You can also set the View object
    view.setBodyView(listView);
```

## Import it ! 


In your gradle "app" :

```
dependencies {
    compile 'com.vcaen:androidautohideheader:1.0'
}
```
```
