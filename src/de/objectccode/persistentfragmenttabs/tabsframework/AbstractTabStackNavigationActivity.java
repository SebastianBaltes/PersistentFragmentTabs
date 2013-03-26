package de.objectccode.persistentfragmenttabs.tabsframework;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

import de.objectccode.persistentfragmenttabs.R;

/**
 * Main activity for tab fragment. Each tab has its own stack of content fragments. Up and back button are handled automatically by an custom back stack. 
 * @author sbaltes
 */
public abstract class AbstractTabStackNavigationActivity extends SherlockFragmentActivity {

  public static final String LOGTAG = AbstractTabStackNavigationActivity.class.getSimpleName();

  /**
   * Flag that disables change of the back stack. This is needed during back stack operations like SwitchTabBackAction.
   */
  boolean changeOfStackAllowedForTabListener = true;
  
  /**
   * Set of all fragments that were added once to this activity in order to distinguish between add and attach
   */
  WeakHashMap<Fragment, Boolean> fragmentWasAdded = new WeakHashMap<Fragment, Boolean>();
  
  /**
   * Custom back button stack. It contains of two possible actions: change of tab and push of new fragments 
   */
  private final Stack<BackButtonAction> backButtonActions = new Stack<BackButtonAction>();
  
  /**
   * Maps the tab index to the tab info object
   */
  @SuppressLint("UseSparseArrays")
  private final Map<Integer, TabInfo> tabMap = new HashMap<Integer, TabInfo>();

  /**
   * add a tab and the tab content. Call it in createTabs.
   * @param tab
   * @param fragment
   */
  public void addTab(final ActionBar.Tab tab, final Fragment fragment) {
    final Integer tag = tabMap.size();
    final TabInfo tabInfo = new TabInfo(this);
    tabInfo.tag = tag;
    tabInfo.tab = tab;
    tabInfo.stack.push(fragment);
    tab.setTabListener(new TabListenerWrapperPreventingNPE(getSupportFragmentManager(), tabInfo));
    tab.setTag(tag);
    tabMap.put(tag, tabInfo);
    getSupportActionBar().addTab(tab);
  }

  /**
   * Callback method (part of onCreate) for adding all tabs with addTab 
   */
  protected abstract void createTabs();

  /**
   * Gets the tab info object of the currently selected tab
   * @return TabInfo
   */
  public TabInfo getCurrentTabInfo() {
    final int index = getSupportActionBar().getSelectedNavigationIndex();
    final TabInfo tabInfo = tabMap.get(index);
    return tabInfo;
  }

  @Override
  public void onBackPressed() {
    if (backButtonActions.isEmpty()) {
      Log.d(LOGTAG, "backButtonActions.isEmpty() => default back action");
      super.onBackPressed();
    } else {
      final BackButtonAction action = backButtonActions.pop();
      Log.d(LOGTAG, "popBackAction(" + action + ")");
      action.back();
      Log.d(LOGTAG, "backButtonActions: " + backButtonActions);
    }
  }

  @Override
  public void onConfigurationChanged(final Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    setContentView(R.layout.tab_navigation);
  }

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // This has to be called before setContentView and you must use the
    // class in com.actionbarsherlock.view and NOT android.view
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    setContentView(R.layout.tab_navigation);

    // Prevent display of progress bar at startup in android 2.2...
    setSupportProgressBarIndeterminateVisibility(false);

    // should prevent recreation of tabs after activity restart (e.g.
    // orientation change) - does it work?
    if (savedInstanceState == null) {
      createTabs();
    }
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    Log.d(LOGTAG, "onOptionsItemSelected");
    if (item.getItemId() == android.R.id.home) {
      do {
        if (backButtonActions.isEmpty()) {
          throw new RuntimeException("UpButton was clicked, but backButtonActions is empty");
        }
        final BackButtonAction action = backButtonActions.pop();
        final int currentIndex = getSupportActionBar().getSelectedNavigationIndex();
        if (action instanceof PopTabStackBackAction) {
          final PopTabStackBackAction popAction = (PopTabStackBackAction) action;
          if (popAction.index == currentIndex) {
            action.back();
            // Log.d(LOGTAG, "backButtonActions: "+backButtonActions);
            break;
          }
        }
      } while (true);
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * pop the current fragment from the tabs back stack and show the previous fragment  
   */
  public void popFragment() {
    Log.d(LOGTAG, "popFragment()");
    final TabInfo tabInfo = getCurrentTabInfo();
    if (tabInfo.stack.size() <= 1) {
      Log.d(LOGTAG, "popFragment() - nothing to pop...");
      return;
    }
    final FragmentManager fragmentManager = getSupportFragmentManager();
    final FragmentTransaction ft = fragmentManager.beginTransaction();
    tabInfo.detachFragment(ft, tabInfo.stack.pop());
    tabInfo.attachFragment(ft, tabInfo.stack.peek());
    ft.commit();
  }

  protected void pushBackAction(final BackButtonAction action) {
    Log.d(LOGTAG, "pushBackAction(" + action + ")");
    backButtonActions.push(action);
    Log.d(LOGTAG, "backButtonActions: " + backButtonActions);
  }

  /**
   * pushs a new fragment onto the tabs back stack and shows the new fragment
   * @param tabInfo tabinfo where the fragment should be pushed
   * @param fragment the fragment to show
   * @param pushEvenIfSameClassAsTopFragment if false, the fragment is pushed only if it is of a different class than the currently shown fragment of that tab - instead it replaces the current fragment. If true, then the fragment is pushed in any case. 
   */
  public void pushFragment(final TabInfo tabInfo, final Fragment fragment, final boolean pushEvenIfSameClassAsTopFragment) {
    Log.d(LOGTAG, "pushFragment(" + fragment + ")");
    final boolean onWrongTab = getCurrentTabInfo() != tabInfo;
    if (onWrongTab) {
      pushBackAction(new SwitchTabBackAction(this, getCurrentTabInfo().tag));
    }
    final boolean newFragmentIsOfSameClassAsOldFragment = tabInfo.stack.size() > 0
        && tabInfo.stack.peek().getClass().equals(fragment.getClass());
    final boolean justReplacePreviousFragment = newFragmentIsOfSameClassAsOldFragment && !pushEvenIfSameClassAsTopFragment;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    final FragmentTransaction ft = fragmentManager.beginTransaction();
    tabInfo.detachFragment(ft, tabInfo.stack.peek());
    if (justReplacePreviousFragment) {
      Log.d(LOGTAG, "replace previous fragment because it's of same class");
      tabInfo.stack.pop();
    }
    tabInfo.stack.push(fragment);
    tabInfo.attachFragment(ft, fragment);
    ft.commit();
    if (!justReplacePreviousFragment) {
      pushBackAction(new PopTabStackBackAction(this, tabInfo.tag));
    }
    if (onWrongTab) {
      Log.d(LOGTAG, "on wrong tab, select tab " + tabInfo.tag);
      changeOfStackAllowedForTabListener = false;
      try {
        getSupportActionBar().setSelectedNavigationItem(tabInfo.tag);
      } finally {
        changeOfStackAllowedForTabListener = true;
      }
    }
    setUpButtonDependingStack();
  }

  protected void setUpButtonDependingStack() {
    final boolean upButtonEnabled = getCurrentTabInfo().stack.size() > 1;
    Log.d(LOGTAG, "setUpButton(" + upButtonEnabled + ")");
    getSupportActionBar().setDisplayHomeAsUpEnabled(upButtonEnabled);
    getSupportActionBar().setHomeButtonEnabled(upButtonEnabled);
  }

}
