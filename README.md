PersistentFragmentTabs
======================

This is a small framework for navigation tabs and fragment switching and handling of up and back navigation.
Each tab has its own up navigation stack. 
It uses [ActionBarSherlock](http://actionbarsherlock.com/) and is compatible back to API level 8.

Background
----------

The official android documentation provides an example for [navigation tabs with fragments](http://developer.android.com/guide/topics/ui/actionbar.html#Tabs),
but shows not much about up and back navigation. The need for it occured during a port of an iPhone app to android where 
the same user experience for tab and up navigation should be implemented.

How it works
------------



Installation
------------

Demo and framework are included in one android eclipse project.
It has one dependency to ActionBarSherlock that must be present in the workspace as a library project. 
The project can be run as an android android application.

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

