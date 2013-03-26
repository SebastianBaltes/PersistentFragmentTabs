package de.objectccode.persistentfragmenttabs.tabsframework;

class PopTabStackBackAction implements BackButtonAction {

  final int index;
  private final AbstractTabStackNavigationActivity tabStackNavigation;

  public PopTabStackBackAction(final AbstractTabStackNavigationActivity tabStackNavigation, final int index) {
    this.tabStackNavigation = tabStackNavigation;
    this.index = index;
  }

  @Override
  public void back() {
    final int currentIndex = tabStackNavigation.getSupportActionBar().getSelectedNavigationIndex();
    if (index != currentIndex) {
      throw new RuntimeException(this + " but current selected tab index is " + currentIndex);
    }
    tabStackNavigation.popFragment();
  }

  @Override
  public String toString() {
    return "PopTabStackBackAction [tabIndex=" + index + "]";
  }
}