PersistentFragmentTabs
======================

This is a small framework for navigation tabs and fragment switching and handling of up and back navigation.
Each tab has its own stack of fragments. 
It uses [ActionBarSherlock](http://actionbarsherlock.com/) and is compatible back to API level 8.


Background
----------

The official android documentation provides an example for [navigation tabs with fragments](http://developer.android.com/guide/topics/ui/actionbar.html#Tabs),
but shows not much about up and back navigation. The need for it occured during a port of an iPhone app to android where 
the same user experience for tab and up navigation should be implemented.

How it works from a users point of view
---------------------------------------

How to use
----------


How it works interally
----------------------

The framework consists of 6 classes located in the package de.objectccode.persistentfragmenttabs.tabsframework, 
mainly an abstract activity base class (AbstractTabStackNavigationActivity) that must be extended and a wrapper object 
for the tab information (TabInfo).

The base activity AbstractTabStackNavigationActivity is derived from SherlockFragmentActivity and encapsulates 
most details. It has one abstract method createTabs that is a callback method that need to be implemented in the subclass 
and is used to add the tabs with the method addTab.

The TabInfo class is an internal class and should mainly used as a reference. It stores internal infos about a tab like the fragment stack, 
the tab object, the tab index (tag) and a reference to the tab activity. 

AbstractTabStackNavigationActivity holds a WeakHashMap of all Fragments that have been added to distinguish between fragment add and 
attach and to make this transparent to the programmer. 

The navigation is handled completly by the framework. The navigation is implemented with a custom stack (AbstractTabStackNavigationActivity.backButtonActions) of 
BackButtonAction objects.

There are only two internal BackButtonAction implementations: PopTabStackBackAction and SwitchTabBackAction. 
A PopTabStackBackAction is created each time a new fragment is pushed on the fragment stack of the tab.
A SwitchTabBackAction is created each time another tab is selected. Both objects allow to undo the action.

Installation
------------

Demo and framework are included in one android eclipse project.
It has one dependency to ActionBarSherlock that must be present in the workspace as a library project. 
The project can be run as an android android application. The project is intended to be copied, 
so you should copy the package de.objectccode.persistentfragmenttabs.tabsframework into your own project or use the 
demo project as a starting point.


Demo
----

The demo application shows three tabs. The first two tabs are list views, the third just a text.
A click to a list item opens the detail view on the same tab. The up button is shown in this case and navigates back to the list view, 
indipendent for each tab.


LICENSE
------- 

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License version 3,
as published by the Free Software Foundation.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License 3 for more details.
 
You should have received a copy of the GNU Lesser General Public
License version 3 along with this program.
If not, see <http://www.gnu.org/licenses/>.  

