package de.objectccode.persistentfragmenttabs.tabsframework;

import java.util.Stack;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;

import de.objectccode.persistentfragmenttabs.R;

/**
 * Used to identify a tab. Stores internal infos about a tab like the fragment stack, the tab object, the tab index (tag) and a reference to the tab activity. 
 * @author sbaltes
 */
public class TabInfo implements ActionBar.TabListener {

  Stack<Fragment> stack = new Stack<Fragment>();
  Tab tab;
  Integer tag;
  private final AbstractTabStackNavigationActivity tabStackNavigation;

  /**
   * @param tabStackNavigation
   */
  public TabInfo(final AbstractTabStackNavigationActivity tabStackNavigation) {
    this.tabStackNavigation = tabStackNavigation;
  }

  public void attachFragment(final FragmentTransaction transaction, final Fragment fragment) {
    if (tabStackNavigation.fragmentWasAdded.containsKey(fragment)) {
      Log.d(AbstractTabStackNavigationActivity.LOGTAG, "attachFragment(" + fragment + ")");
      transaction.attach(fragment);
    } else {
      tabStackNavigation.fragmentWasAdded.put(fragment, Boolean.TRUE);
      Log.d(AbstractTabStackNavigationActivity.LOGTAG, "addFragment(" + fragment + ")");
      transaction.add(R.id.fragment_container, fragment, String.valueOf(tag));
    }
    tabStackNavigation.setUpButtonDependingStack();
  }

  public void detachFragment(final FragmentTransaction transaction, final Fragment fragment) {
    if (!fragment.isDetached()) {
      Log.d(AbstractTabStackNavigationActivity.LOGTAG, "detach(" + fragment + ")");
      transaction.detach(fragment);
    }
    tabStackNavigation.setUpButtonDependingStack();
  }

  @Override
  public void onTabReselected(final Tab tab, final FragmentTransaction transaction) {
  }

  @Override
  public void onTabSelected(final Tab tab, final FragmentTransaction transaction) {
    Log.d(AbstractTabStackNavigationActivity.LOGTAG, "onTabSelected " + tab.getTag());
    attachFragment(transaction, stack.peek());
  }

  @Override
  public void onTabUnselected(final Tab tab, final FragmentTransaction transaction) {
    Log.d(AbstractTabStackNavigationActivity.LOGTAG, "onTabUnselected " + tab.getTag());
    detachFragment(transaction, stack.peek());
    if (tabStackNavigation.changeOfStackAllowedForTabListener) {
      tabStackNavigation.pushBackAction(new SwitchTabBackAction(tabStackNavigation, (Integer) tab.getTag()));
    }
  }
}