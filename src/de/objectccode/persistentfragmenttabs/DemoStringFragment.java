package de.objectccode.persistentfragmenttabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Shows just a simple text 
 * @author sbaltes
 */
public class DemoStringFragment extends SherlockFragment {

  private String title;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.hello_world, container, false);
    View tv = v.findViewById(R.id.text);
    ((TextView) tv).setText(title);
    return v;
  }

  public DemoStringFragment setText(String title_) {
    this.title = title_;
    return this;
  }
  
}