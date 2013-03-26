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
    final TabInfo tab = getTabStack().getCurrentTabInfo();
    DemoStringFragment fragment = new DemoStringFragment();
    fragment.setText(characters[position]);
    getTabStack().pushFragment(tab, fragment, false);
  
  }

  public AbstractTabStackNavigationActivity getTabStack() {
    return (AbstractTabStackNavigationActivity)getActivity();
  }

}
