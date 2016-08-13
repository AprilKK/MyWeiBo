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