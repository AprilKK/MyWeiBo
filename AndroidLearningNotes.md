#AndroidLearningNotes
this doc is used for recording the developing process of Anroid Weibo...it is a place to write down the usage of APIs and a place to remember some other materials used for developing.
all the devlopers could edit this file, to add their ideas, their findings, their innovations.
##General materials
###Android Fundermentals
1.**Activity**
Activity is the UserInterface of Android, everything we could see in the screen is hosted by an Activity, it is a entry for a app. Generally speaking, an activity implemented a user defined functionality, such as make a phonecall,take a picture. The App lifecycle is always refer to the Activity's lifecycle.

**View**
Inside the Activity,there are some Views to show the data to user and receive the input from user. it is the atom which could not be splitted used for displaying, and is the View part in MVC module.

2.**BroadCast**

3.**ContentProvider**

4.**Services**


##APIs
**SimpleAdaptor**
*Description*
 * An easy adapter to map static data to views defined in an XML file. You can specify the data backing the list as an ArrayList of Maps. Each entry in the ArrayList corresponds to one row in the list. The Maps contain the data for each row. You also specify an XML file that defines the views used to display the row, and a mapping from keys in the Map to specific views.
*Interface/Parameters*
* Context: the runtime of the views in SimpleAdapter, usually be the current Activity,namely "this".
* List&lt;Map&lt;String,T>>:this is a list comprised map. every element in the list binds to a view in the ListView. the "keys" in map mapping to the R.id.&lt;itemname> in the xml file, and the values in map mapping to the items value.
* int resource:usually an xml file that defines the view used to display on every row.
* string[] from:the keys in the map that will be mapping to the resources id.
* int[] to:the resources id that receive the values in the map.

**Volley**
*DownLoad*
 * Git clone https://Android.googlesource.com/platform/frameworks/volley 
or https://github.com/mcxiaoke/android-volley

*Import Volley to the project*
 * add ``` <uses-permissionandroid:name="android.permission.INTERNET"/>``` to AndroidManifest.xml
 * import this modue to the project following the below steps:
	* File->New->New Module
	* select the "Import Gradle Project"
	* enter the project source directory, always be the folder where build.gradle loacted in. 
	* edit settings.gradle, adding ```include ':app', ':android-volley'
project(':android-volley').projectDir = new File('Library/android-volley')```
	* edit build.gradle under app module, adding ``` compile project(':android-volley')```
	* sync gradle to rebuild the project
	* import the com.android.volley.\*\*\* to use
	
*How to Use*
 * ``` RequestQueue mResQueue = Volley.newRequestQueue(this);```
 * ```
StringRequest mStringRes = new StringRequest("http://www.weather.com.cn/data/cityinfo/101010100.html",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });```
  ```mResQueue.add(mStringRes);```
 * always be three steps:new a Request Queue(one for one activity),create Request, add the Request to the Queue.

##Git 

*Teaching*
http://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000

*add remote branch*
http://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000/0013752340242354807e192f02a44359908df8a5643103a000
*revert to the previous branch history"
【本地代码库回滚】：

git reset --hard commit-id :回滚到commit-id，讲commit-id之后提交的commit都去除

git reset --hard HEAD~3：将最近3次的提交回滚

【远程代码库回滚】：

这个是重点要说的内容，过程比本地回滚要复杂

应用场景：自动部署系统发布后发现问题，需要回滚到某一个commit，再重新发布

原理：先将本地分支退回到某个commit，删除远程分支，再重新push本地分支

操作步骤：

1、git checkout the_branch

2、git pull

3、git branch the_branch_backup //备份一下这个分支当前的情况

4、git reset --hard the_commit_id //把the_branch本地回滚到the_commit_id

5、git push origin :the_branch //删除远程 the_branch

6、git push origin the_branch //用回滚后的本地分支重新建立远程分支

7、git push origin :the_branch_backup //如果前面都成功了，删除这个备份分支
##Weibo API/SDK
*What is OAuth 2.0*
http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html
*Reference*
http://blog.csdn.net/wwj_748/article/details/9566969
