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

**StartActivity**

*Description*

*Interface/Parameters*

*note*

to use the StartActivity API, we have to add the target Activity to the AndroidManifest.xml, otherwise, nobody could start this Activity anymore in the future.

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

 *  ``` RequestQueue mResQueue = Volley.newRequestQueue(this);```
 *  ```
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
        });
     ```
  *  ```mResQueue.add(mStringRes);```
  
 * always be three steps:new a Request Queue(one for one activity),create Request, add the Request to the Queue.

##Git

**Teaching**

http://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000

**add remote branch**

http://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000/0013752340242354807e192f02a44359908df8a5643103a000

**checkout the specific file from another branch without merge all the files**

`git checkout <workbranchname>` // change to the work branch
`git checkout <branchname2> -- <filename>` //checkout the sepicific file to take the place of the file in workbranch

**revert to the previous branch history**

++revert local depository++
`git reset --hard commit-id` //revert to the specific commit version, all the commits behind the commit-id will be dropped.

`git reset --hard HEAD~3` //revert the latest 3 commits.

++revert remote depository++
in some words: delete remote branch and re-push the reverted local branch to it.
steps:
 1. `git checkout the_branch`

 2. `git pull` //get the latest code change from server

 3. `git branch` the_branch_backup //backup this branch

 4. `git reset --hard the_commit_id` //revert the branch to the specific commit version

 5. `git push origin :the_branch` //delete the remote branch

 6. `git push origin the_branch` //push the reverted branch code to the remote server

 7. `git push origin :the_branch_backup` //now we could delete the backup depository

*how to deal with conflict when there is some changes which do no want to commit to the branch*

**Get help of specific command  from git**
git remote /help    //Get help of the command "git remote" from git

**Copy the content from specific remote branch**
 git clone -b <BranchName>  https://github.com/AprilKK/MyWeiBo.git      //Copy the content from the r remote BranchName branch


**Check what origin refers to in the fetch and push operation**
git remote -v

**Modify the origin parameter in fetch and push operation**
git remote add origin  github.git        // Set the value of origin  to github.git
git remote remove origin                //delete the origin value


**stash**

*steps:*

 1. `git stash` //backup the work directory to git stack. and read out the content from the latest commit version to keep sync with the latest commit.

*related cmd*

`git stash pop` //read out the content from git stack to restore the work directory.

`git stash list` //to list all the content in git stack, could be used to check the restore point.
`git stash clear` //clear git stack

##Weibo API/SDK

**What is OAuth 2.0**

http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html

**how to import to the project**

1. copy weiboSDKCore_3.1.4.jar to the Libary folder
2. in AS, right click on the xxx.jar file, and choose "Add as Library"
3. use import xxx to include the related package to the project.

*SDK use Reference*

http://blog.csdn.net/wwj_748/article/details/9566969
