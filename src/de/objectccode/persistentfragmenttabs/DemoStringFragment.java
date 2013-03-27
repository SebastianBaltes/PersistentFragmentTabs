package de.objectccode.persistentfragmenttabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Shows just a simple text
 * 
 * @author sbaltes
 */
public class DemoStringFragment extends SherlockFragment {

  private String title;

  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    final View v = inflater.inflate(R.layout.hello_world, container, false);
    final View tv = v.findViewById(R.id.text);
    ((TextView) tv).setText(title);
    return v;
  }

  public DemoStringFragment setText(final String title_) {
    title = title_;
    return this;
  }

}