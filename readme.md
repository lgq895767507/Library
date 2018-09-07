* 比如现在有两个应用，一个应用需要更新另一个应用的某个界面，这个时候我们当然可以选择AIDL去实现。
但是对于界面更新比较频繁，这个时候就会有效率问题了，同时AIDL接口可能也会很复杂，这个时候采用RemoteViews来实现
就没有这个问题了。

#### 以下以一个简单的例子来模拟通知栏和桌面小工具的底层实现原理。

#### RemoteViews即远程view,这货能实现跨进程更新界面。

#### RemoteViews并不能支持所有的View类型，更不能自定义View。
* layout
    * framelayout
    * linearlayout
    * relativelayout
    * gridlayout
* view
    * analogclock
    * button
    * chronmeter
    * imagebutton
    * imageview
    * progressbar
    * textview
    * viewflipper
    * listview
    * gridview
    * stackview
    * adapterviewflipper
    * viewstub
   
* 以上是RemoteViews支持的所有的view类型。
