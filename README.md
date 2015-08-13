# AndroidAutoHideHeader
[ ![Download](https://api.bintray.com/packages/vcaen/maven/androidautohideheader/images/download.svg) ](https://bintray.com/vcaen/maven/androidautohideheader/_latestVersion)
[ ![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AndroidAutoHideHeader-blue.svg?style=flat)](http://android-arsenal.com/details/1/2285)

A layout that hide the header when the body is scrolled down and reveal it when the header is scrolled up

## Demo

![Demo gif](https://raw.githubusercontent.com/vcaen/AndroidAutoHideHeader/master/example.gif)


## Import it !


In your gradle "app" :

```
dependencies {
    compile 'com.vcaen:androidautohideheader:1.2'
}
```


## Usage

### Basics

#### Programmatically
In you xml, add the '''AutoHideHeaderLayout''' : 

```xml
    <com.vcaen.androidautohideheader.AutoHideHeaderLayout
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/autohideview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:fixedIfChildSmall="false"
        app:fixed="false"/>
``` 

Then in your java code : 
 ```java
    AutoHideHeaderLayout view  = (AutoHideHeaderLayout) findViewById(R.id.autohideview);
    ListView listView = new ListView(this);
    view.setHeader(R.layout.header); // You can also set the View object
    view.setBodyView(listView);
```

#### XML Only

You can add the children directly into the XML by adding them inside the ```AutoHideHeaderLayout``` :
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:tools="http://schemas.android.com/tools"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                tools:context=".MainActivity">

    <com.vcaen.androidautohideheader.AutoHideHeaderLayout
        android:id="@+id/autohideview"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="100dp">

            <ImageView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:scaleType="centerCrop"
                android:src="@drawable/bg"/>

        </RelativeLayout>

        <ScrollView
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical">

            </LinearLayout>
        </ScrollView>
    </com.vcaen.androidautohideheader.AutoHideHeaderLayout>

</RelativeLayout>
```
### Options :

The following options are available :

  * Fix the header

    *Java*
    ```java
        autoHideHeaderLayout.setFixed(Boolean)
    ```

    *XML*
    ```xml
        app:fixed="true"
    ```


  * Fix the header if the child is smaller than the view. Only works if the child has height set at ```wrap_content```

    *Java*
    ```java
        autoHideHeaderLayout.setFixedIfChildSmall(Boolean)
    ```

    *XML*
    ```xml
        app:fixedIfChildSmall="true"
    ```


## License

The MIT License (MIT)

Copyright (c) 2015 Vadim Caen

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE
