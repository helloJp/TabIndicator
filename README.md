#TabIndicator
##Ⅰ、效果图
![](https://raw.githubusercontent.com/helloJp/TabIndicator/master/art/screenshot_indicator01.gif)
</br>
##Ⅱ、使用方法
###1、布局文件中添加 **TabsIndicator**

```xml

 <me.jp.indicator.TabsIndicator
        android:id="@+id/indicator"
        android:layout_width="match_parent"
        android:layout_height="40dp"
        app:dividerColor="#90ffffff"
        app:linePosition="bottom"
        app:dividerVerticalMargin="8dp"
        app:dividerWidth="2dp"
        app:hasDivider="true"
        app:lineColor="@android:color/white"
        app:lineHeight="2dp"
        app:lineMarginTab="5dp"
        app:textColor="@color/selector_tab"
        app:textSizeNormal="14sp"
        app:textSizeSelected="18sp"
        />


    <android.support.v4.view.ViewPager
        android:id="@+id/viewpager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        />

```
###2、Java代码中调用
```java

		mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles));
        mViewPager.setOffscreenPageLimit(mTitles.length);
        mTabsIndicator = (TabsIndicator) findViewById(R.id.indicator);
        mTabsIndicator.addTabIcon(R.mipmap.tab1_n, R.mipmap.tab1_p);
        mTabsIndicator.addTabIcon(R.mipmap.tab2_n, R.mipmap.tab2_p);
        mTabsIndicator.addTabIcon(R.mipmap.tab3_n, R.mipmap.tab3_p);
        mTabsIndicator.addTabIcon(R.mipmap.tab4_n, R.mipmap.tab4_p);
        mTabsIndicator.setViewPager(0, mViewPager);
```
 </br>这里需要注意：**想要令Tab带有ICON，必须要在setViewPager(int currentTab, ViewPager viewPager)方法前,调用addTabIcon(int idNormal, int idSelected)，传入Tabs选项所需的ICON图片。**</br>
不需要ViewPager及TabsIndicator带有滑动效果，可以设置setAnimationWithTabChange(false)



| attr 属性             | description 描述 |
|:---				   |:---|
| textSizeNormal  	   | 未选中文本字体大小 |
| textSizeSelected     | 选中文本字体大小 |
| lineMarginTab	 	   | indicator线，距离每个tab的间距 |
| lineColor 		   |indicator线的颜色 |
| lineHeight 		   | indicator线的高度|
| linePosition 		   | indicator线的位置， top位于tab的底部，bottom位于底部 |
| hasDivider 	       | tab选项卡间的分隔线 true存在，false不存在 |
| dividerColor  	   | 分割线颜色 |
| dividerWidth         | 分割线宽度 |
| dividerVerticalMargin 	 | 分割线距离tab选项卡上下距离 |

#### 
2015.12.18 添加直接点击tab，indicator的滑动。

