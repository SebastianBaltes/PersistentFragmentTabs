package de.objectccode.persistentfragmenttabs.tabsframework;

class SwitchTabBackAction implements BackButtonAction {

  private final int index;
  private final AbstractTabStackNavigationActivity tabStackNavigation;

  public SwitchTabBackAction(final AbstractTabStackNavigationActivity tabStackNavigation, final int index) {
    this.tabStackNavigation = tabStackNavigation;
    this.index = index;
  }

  @Override
  public void back() {
    tabStackNavigation.changeOfStackAllowedForTabListener = false;
    try {
      tabStackNavigation.getSupportActionBar().setSelectedNavigationItem(index);
    } finally {
      tabStackNavigation.changeOfStackAllowedForTabListener = true;
    }
  }

  @Override
  public String toString() {
    return "SwitchTabBackAction [tabIndex=" + index + "]";
  }
}