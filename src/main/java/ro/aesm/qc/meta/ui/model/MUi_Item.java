package ro.aesm.qc.meta.ui.model;

import ro.aesm.qc.meta.ui.model.feature.GridLayoutChildItem;

public abstract class MUi_Item {

	protected String id;
	protected String name;
	protected String cssClass;

	protected GridLayoutChildItem gridLayoutChildItem = new GridLayoutChildItem();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public GridLayoutChildItem getGridLayoutChildItem() {
		return gridLayoutChildItem;
	}

}
