# Simple ProgressBar

一个简单的、带特效的Android进度条，进度条覆盖文字时会变色

## 效果图

![](http://sdxuemd.qiniudn.com/SCR_20160921_024953.gif)

## Usage

### Step 1

#### Gradle

```groovy
dependencies {
    compile "cool.lht.android:simpleprogressbar:1.0"
}
```

### Using XML

可以直接在XML当中添加ProgressBar，例如，需要添加一个进度为20%，文字内容为"downloading..."的进度条：

```xml
<cool.lht.customprogressbar.LhtProgressBar
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:progress="20"
        app:progressText="downloading..." />
```

###### 属性

* `progressText`: 进度文字内容
* `borderWidth`: 外围边框宽度
* `backgroundColor`: 进度条背景颜色
* `textColor`: 进度文字颜色
* `progress`: 当前进度值
* `maxProgress`: 最大进度值
* `textSize`: 进度文字大小

### Using Java

如果在XML中已经添加过，直接获取使用即可：

```java
LhtProgressBar progressBar = (LhtProgressBar) findViewById(R.id.progressbar);
progressBar.setProgress(40);
```

在java动态添加进度条：

```java
LhtProgressBar progressBar = new LhtProgressBar(this);
progressBar.setProgressText("download:40%");
progressBar.setProgress(40);
layout.addView(progressBar);
```

###### java方法

* `setProgressText`
* `setMaxProgress`
* `setProgress`
* `setTextSize`
* `setBackgroundColor`
* `setTextColor`
* `setBorderWidth`

功能和XML中属性一致

## License

```
Copyright 2016 Hatim Liu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```