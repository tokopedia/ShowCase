# ShowCase

This library is used to showcase chain of views to guide the users about the feature on the screen.

<img src="record showcase.gif?raw=true" alt="" width="240" />

Usage
====

First, create the `ShowCaseDialog`. Use Builder Pattern.

As an example:

```java
showCaseDialog = new ShowCaseBuilder()
    .textColorRes(android.R.color.white)
    .shadowColorRes(R.color.shadow)
    .titleTextSizeRes(R.dimen.text_title)
    .textSizeRes(R.dimen.text_normal)
    .spacingRes(R.dimen.spacing_normal)
    .backgroundContentColorRes(R.color.blue)
    .circleIndicatorBackgroundDrawableRes(R.drawable.selector_circle_green)
    .prevStringRes(R.string.previous)
    .nextStringRes(R.string.next)
    .finishStringRes(R.string.finish)
    .useCircleIndicator(true)
    .clickable(true)
    .build();
```

After the dialog has been created, provide the `ArrayList` of `ShowCaseObject`

As an example to showcase 2 views:

```
ArrayList <ShowCaseDialog.ShowCaseObject> showCaseList = new ArrayList<>();
showCaseList.add(new ShowCaseDialog.ShowCaseObject(
        viewToHighLight,
        "Title",
        "Description"));
showCaseList.add(new ShowCaseDialog.ShowCaseObject(
        view2,
        null,
        "Description 2");
showCaseDialog.show(this, showCaseList);
```

Item Customization
====
```
showCaseList.add(new ShowCaseDialog.ShowCaseObject(@Nullable View view, 
                                @Nullable String title,
                                String text, 
                                ShowCaseContentPosition showCaseContentPosition,
                                int tintBackgroundColor) );
```
View
    is the view to anchor. Fill `null` if no view to anchor.

Title
    is the title to show, will be bold in default. Fill `null` if you don't use title.

text
    is the text to show. By default will parse HTML.

position (optional) 
    default is `UNDEFINED` and will position the content automatically.
    Other option is `LEFT`,`TOP`,`RIGHT`,`BOTTOM` to position manually.

tintBackgroundColor (optional) 
    to override the backgroundColor to the view. The default is transparent.
    
Custom Layout Customization
====
```java
showCaseDialog = new ShowCaseBuilder()
    .customView(R.layout.customView)
    .build();
```

Note that the customView should have the same ID to use default functionality

License
-------

    Copyright 2017 Tokopedia

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
