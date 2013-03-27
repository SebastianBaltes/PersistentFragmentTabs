package de.objectccode.persistentfragmenttabs.tabsframework;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;

/**
 * see
 * http://stackoverflow.com/questions/8645549/null-fragmenttransaction-being-
 * passed-to-tablistener-ontabselected
 * 
 * @author sbaltes
 */
class TabListenerWrapperPreventingNPE implements ActionBar.TabListener {

  private final ActionBar.TabListener delegate;
  private final FragmentManager manager;

  public TabListenerWrapperPreventingNPE(final FragmentManager manager, final ActionBar.TabListener delegate) {
    this.delegate = delegate;
    this.manager = manager;
  }

  @Override
  public void onTabReselected(final Tab tab, FragmentTransaction ft) {
    if (ft == null) {
      ft = manager.beginTransaction();
      delegate.onTabReselected(tab, ft);
      ft.commit();
    } else {
      delegate.onTabReselected(tab, ft);
    }
  }

  @Override
  public void onTabSelected(final Tab tab, FragmentTransaction ft) {
    if (ft == null) {
      ft = manager.beginTransaction();
      delegate.onTabSelected(tab, ft);
      ft.commit();
    } else {
      delegate.onTabSelected(tab, ft);
    }
  }

  @Override
  public void onTabUnselected(final Tab tab, FragmentTransaction ft) {
    if (ft == null) {
      ft = manager.beginTransaction();
      delegate.onTabUnselected(tab, ft);
      ft.commit();
    } else {
      delegate.onTabUnselected(tab, ft);
    }
  }

}
