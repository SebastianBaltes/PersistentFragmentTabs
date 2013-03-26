package de.objectccode.persistentfragmenttabs;

import android.support.v4.app.Fragment;

import com.actionbarsherlock.app.ActionBar;

import de.objectccode.persistentfragmenttabs.tabsframework.AbstractTabStackNavigationActivity;

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
        new DemoListFragment().setCharacters(new String[] { "James T. Kirk", "Spock", "Leonard 'Bones' McCoy", "Montgomery 'Scotty' Scott",
            "Hikaru Sulu", "Nyota Uhura", "Pavel Chekov", "Christine Chapel", "Janice Rand", }));

    // add a second tab: A clickable list of star trek characters 
    addTab(
        "Next Gen. Tab",
        new DemoListFragment().setCharacters(new String[] { "Jean-Luc Picard", "William Thomas 'Will' Riker", "Data", "Geordi La Forge",
            "Worf", "Doctor Beverly Crusher", "Doctor Katherine Pulaski", "Deanna Troi", "Natasha 'Tasha' Yar", "Wesley Crusher", }));

    // add a third tab 
    addTab("Hello Tab", new DemoStringFragment().setText("Hello on Tab 3"));
  }

  public void addTab(String title, Fragment fragment) {
    ActionBar.Tab tab = getSupportActionBar().newTab();
    tab.setText(title);
    addTab(tab, fragment);
  }

}
