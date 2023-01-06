package ro.aesm.qc.meta.ui.model;

import java.util.ArrayList;
import java.util.List;

import ro.aesm.qc.meta.ui.model.feature.DefaultsProvider;

public class MUi_Region extends MUi_Item { //

	protected String layout;

	protected String childrenNamesValue;
	// protected List<String> childrenNames = new ArrayList<String>();
	protected List<MUi_Item> children = new ArrayList<MUi_Item>();

	protected DefaultsProvider defaultsProvider = new DefaultsProvider();

	protected MUi_PopupLayoutConfig popupConfig;
	protected MUi_TabsLayoutConfig tabsConfig;

	// defaults for items, values same as in item
//	protected String labelPosition; // begin top bottom end
//	protected String labelAlign; // start center end
//	protected String showLabel;// yes no empty (for alignment purpose)

//	public void afterPropertiesSet() {
//		if (this.children  != null) {
//			String[] tmp = this.childrenNamesValue.split(",");
//			for (String name : tmp) {
//				this.childrenNames.add(name.trim());
//			}
//		}
//	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getChildrenNamesValue() {
		return childrenNamesValue;
	}

	public void setChildrenNamesValue(String childrenNamesValue) {
		this.childrenNamesValue = childrenNamesValue;
	}

	public List<MUi_Item> getChildren() {
		return children;
	}

	public void setChildren(List<MUi_Item> children) {
		this.children = children;
	}

	public MUi_PopupLayoutConfig getPopupConfig() {
		return popupConfig;
	}

	public void setPopupConfig(MUi_PopupLayoutConfig popupConfig) {
		this.popupConfig = popupConfig;
	}

	public MUi_TabsLayoutConfig getTabsConfig() {
		return tabsConfig;
	}

	public void setTabsConfig(MUi_TabsLayoutConfig tabsConfig) {
		this.tabsConfig = tabsConfig;
	}

	public DefaultsProvider getDefaultsProvider() {
		return defaultsProvider;
	}

}
