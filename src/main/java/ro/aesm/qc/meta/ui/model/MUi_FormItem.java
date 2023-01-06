package ro.aesm.qc.meta.ui.model;

import ro.aesm.qc.meta.ui.model.feature.LabeledItem;

public class MUi_FormItem extends MUi_Item {

	protected LabeledItem labeledItem = new LabeledItem();

	protected String label;
	protected boolean required;

	public LabeledItem getLabeledItem() {
		return labeledItem;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

}
