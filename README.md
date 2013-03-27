PersistentFragmentTabs
======================

For Android Development. This is a simple and small framework for navigation tabs and fragment switching and handling of up and back navigation.
Each tab has its own stack of fragments. 
It uses [ActionBarSherlock](http://actionbarsherlock.com/) and is compatible back to API level 8.


Background
----------

The official android documentation provides an example for [navigation tabs with fragments](http://developer.android.com/guide/topics/ui/actionbar.html#Tabs),
but does not provide much information about up and back navigation. The need for it occured during a port of an iPhone app to android where 
the same user experience for tab and up navigation should be implemented.

For more background, see this [Stackoverflow](http://stackoverflow.com/questions/6987334/separate-back-stack-for-each-tab-in-android-using-fragments) question.

How it works from a users point of view
---------------------------------------

Coming from an iPhone world, it works like a TabBarController containing some NavigationController on each tab.

But it's best explained by example: The demo application shows three tabs. The first two tabs are list views, the third just a text.

![Master View](/tab_master.png?raw=true)

A click to a list item opens the detail view on the same tab. The up button is shown in this case and navigates back to the list view, 
independent for each tab. 

![Detail View](/tab_detail.png?raw=true)

If a tab with a list view is shown, then no up button is shown because there is no parent view,
the list view is the master. If a tab with a list item (detail view) is shown, then the up button is shown because 
there exists a parent view. Any number of fragments can be stacked for each tab, not just two.

Switching between tabs and/or using the back button works without breaking this logik. 

How to use
----------

All tabs and all fragments are shown on one single activity that needs to extend AbstractTabStackNavigationActivity. 
There you need to implement the method createTabs and call addTab:

```java
/**
 * Demo activity that shows three simple tabs and ancestral and temporal navigation support  
 * @author sbaltes
 */
public class MainActivity extends AbstractTabStackNavigationActivity {

  /**
   * overriden call back method where the tabs are created 
   */
  @Override
  protected void createTabs() {

    // add a first tab: A clickable list of star trek characters 
    addTab(
        "Classic Tab",
        new DemoListFragment().setCharacters(
            new String[] { "James T. Kirk", "Spock", "Leonard 'Bones' McCoy", }));

    // add a second tab: A clickable list of star trek characters 
    addTab(
        "Next Gen. Tab",
        new DemoListFragment().setCharacters(
            new String[] { "Jean-Luc Picard", "William Thomas 'Will' Riker", "Data", }));

    // add a third tab 
    addTab("Hello Tab", new DemoStringFragment().setText("Hello on Tab 3"));
  }

  public void addTab(String title, Fragment fragment) {
    ActionBar.Tab tab = getSupportActionBar().newTab();
    tab.setText(title);
    addTab(tab, fragment);
  }

}
```

The following example shows how to push a fragment, to show a detail view if an item is clicked:

```java
/**
 * Simple list fragment that demonstrates ancestral navigation 
 * @author sbaltes
 */
public class DemoListFragment extends SherlockListFragment {

  private String[] characters;

  public DemoListFragment() {
  }
  
  public DemoListFragment setCharacters(String[] characters_) {
    this.characters = characters_;
    return this;
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, characters);
    setListAdapter(adapter);
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
    
    // first, you need to get the reference to the currently shown tab in order to add the fragment onto this tab
    final TabInfo tab = getTabStack().getCurrentTabInfo();
    
    DemoStringFragment fragment = new DemoStringFragment();
    fragment.setText(characters[position]);
    
    // second, you push the fragment. It becomes visible and the up button is shown
    getTabStack().pushFragment(tab, fragment);
  
  }

  public AbstractTabStackNavigationActivity getTabStack() {
    return (AbstractTabStackNavigationActivity)getActivity();
  }

}
```

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
The project can be run as an android application. The project is intended to be copied, 
so you should copy the package de.objectccode.persistentfragmenttabs.tabsframework into your own project or use the 
demo project as a starting point.


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

