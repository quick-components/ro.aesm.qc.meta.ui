package ro.aesm.qc.meta.ui.model.feature;

import ro.aesm.qc.meta.ui.ConstantsUi;

public class LabeledItem {

	protected String labelPosition; // begin top bottom end
	protected String labelAlign; // start center end
	protected String showLabel;// yes no empty (for alignment purpose)
	protected int labelWidth = -1;

	public void applyDefaults(LabeledItem source) {
		if (this.labelAlign == null) {
			this.labelAlign = (source != null) ? source.getLabelAlign() : ConstantsUi.LABEL_ALIGN_VALUE;
		}
		if (this.labelPosition == null) {
			this.labelPosition = (source != null) ? source.getLabelPosition() : ConstantsUi.LABEL_POSITION_VALUE;
		}
		if (this.showLabel == null) {
			this.showLabel = (source != null) ? source.getShowLabel() : ConstantsUi.SHOW_LABEL_VALUE;
		}
		if (this.labelWidth == -1) {
			this.labelWidth = (source != null) ? source.getLabelWidth() : ConstantsUi.LABEL_WIDTH_VALUE;
		}
	}

	public String getLabelPosition() {
		return labelPosition;
	}

	public void setLabelPosition(String labelPosition) {
		this.labelPosition = labelPosition;
	}

	public String getLabelAlign() {
		return labelAlign;
	}

	public void setLabelAlign(String labelAlign) {
		this.labelAlign = labelAlign;
	}

	public String getShowLabel() {
		return showLabel;
	}

	public void setShowLabel(String showLabel) {
		this.showLabel = showLabel;
	}

	public int getLabelWidth() {
		return labelWidth;
	}

	public void setLabelWidth(int labelWidth) {
		this.labelWidth = labelWidth;
	}
}
