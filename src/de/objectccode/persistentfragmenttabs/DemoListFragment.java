package de.objectccode.persistentfragmenttabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

import de.objectccode.persistentfragmenttabs.tabsframework.AbstractTabStackNavigationActivity;
import de.objectccode.persistentfragmenttabs.tabsframework.TabInfo;

/**
 * Simple list fragment that demonstrates ancestral navigation
 * 
 * @author sbaltes
 */
public class DemoListFragment extends SherlockListFragment {

  private String[] characters;

  public DemoListFragment() {
  }

  public AbstractTabStackNavigationActivity getTabStack() {
    return (AbstractTabStackNavigationActivity) getActivity();
  }

  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, characters);
    setListAdapter(adapter);
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onListItemClick(final ListView l, final View v, final int position, final long id) {
    super.onListItemClick(l, v, position, id);

    // first, you need to get the reference to the currently shown tab in order
    // to add the fragment onto this tab
    final TabInfo tab = getTabStack().getCurrentTabInfo();
    final DemoStringFragment fragment = new DemoStringFragment();
    fragment.setText(characters[position]);

    // second, you push the fragment. It becomes visible and the up button is
    // shown
    getTabStack().pushFragment(tab, fragment);

  }

  public DemoListFragment setCharacters(final String[] characters_) {
    characters = characters_;
    return this;
  }

}
