# ShowCase

This library is used to showcase chain of views to guide the users about the feature on the screen.

<table>
    <tr>
        <td>
        <img src="showcase1.gif?raw=true" alt="" width="240" />
        </td>
        <td>
        <img src="showcase2.gif?raw=true" alt="" width="240" />
        </td>
    </tr>
</table>

Dependencies
-------
```
dependencies {
    implementation "com.tokopedia.tkpdlib:showcase-stepper:0.6.0"
}
```

Usage
-------

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
ArrayList <ShowCaseObject> showCaseList = new ArrayList<>();
showCaseList.add(new ShowCaseObject(
        viewToHighLight,
        "Title",
        "Description"));
showCaseList.add(new ShowCaseObject(
        view2,
        null,
        "Description 2");
showCaseDialog.show(this, showCaseList);
```

Item Customization
-------
```
showCaseList.add(new ShowCaseObject(@Nullable View view, 
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
    
Custom Target
-------
```
showCaseList.add(
                new ShowCaseObject(
                        findViewById(android.R.id.content),
                        "Show case using custom target",
                        "This is highlighted using custom target")
                        .withCustomTarget(new int[]{ xCenter, yCenter}
                                , radius) );
```
Use ```.withCustomTarget``` to highlight circle area for the specific coordinate (x,y) and radius.
```
showCaseList.add(
                new ShowCaseObject(
                        findViewById(android.R.id.content),
                        "Show case using custom target",
                        "This is highlighted using custom target")
                        .withCustomTarget(new int[]{ left, top, right, bottom} ) );
```
Use ```.withCustomTarget``` to highlight rectangle area for the specific coordinate (x,y) and radius.
    
Custom Layout Customization
-------
```java
showCaseDialog = new ShowCaseBuilder()
    .customView(R.layout.customView)
    .build();
```

Note: To use most of default functionality, the views in the custom layout should correspond with the same id with the id in ```R.layout.tutorial_view.xml```

License
-------

    Copyright 2019 Tokopedia

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
