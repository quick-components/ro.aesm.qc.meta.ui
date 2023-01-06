package ro.aesm.qc.meta.ui.model.feature;

import ro.aesm.qc.meta.ui.ConstantsUi;

public class GridLayoutChildItem {

	protected int colspan = -1;

	protected int rowspan = -1; // rowspan experimental

	protected Boolean newRow;

	public void applyDefaults(GridLayoutChildItem source) {
		if (this.colspan == -1) {
			this.colspan = (source != null) ? source.getColspan() : ConstantsUi.COLSPAN_VALUE;
		}
		if (this.rowspan == -1) {
			this.rowspan = (source != null) ? source.getRowspan() : ConstantsUi.ROWSPAN_VALUE;
		}
		if (this.newRow == null) {
			this.newRow = (source != null) ? source.isNewRow() : ConstantsUi.NEWROW_VALUE;
		}
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public Boolean isNewRow() {
		return newRow;
	}

	public void setNewRow(Boolean newRow) {
		this.newRow = newRow;
	}
}
