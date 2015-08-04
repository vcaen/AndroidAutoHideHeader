# AndroidAutoHideHeader
[ ![Download](https://api.bintray.com/packages/vcaen/maven/android-auto-hide-header/images/download.svg) ](https://bintray.com/vcaen/maven/android-auto-hide-header/_latestVersion)

A layout that hide the header when the body is scrolled down and reveal it when the header is scrolled up

## Demo

![Demo gif](https://raw.githubusercontent.com/vcaen/AndroidAutoHideHeader/master/example.gif)

## Usage

In you xml, add the '''AutoHideHeaderLayout''' : 

```xml
    <fr.vadimcaen.autohideheader.AutoHideHeaderLayout
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

For now I'm waiting for my project to be linked on jcenter so you can add the following repo on you root gradle file : 

```
repositories {
    maven {
        url  "http://dl.bintray.com/vcaen/maven" 
    }
}
```

And add the following dependency in you application gradle :

```
dependencies {
    compile 'com.vadimcaen.library:android-auto-hide-header:1.0.2'
}
```
```
