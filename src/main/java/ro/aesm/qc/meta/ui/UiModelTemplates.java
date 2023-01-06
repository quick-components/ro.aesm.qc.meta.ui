package ro.aesm.qc.meta.ui;

import ro.aesm.qc.meta.ui.model.MUi_Button;
import ro.aesm.qc.meta.ui.model.MUi_Item;

public class UiModelTemplates {

	public static MUi_Item createItem(String key) {
		if ("@save".equals(key)) {
			return createSaveButton();
		}
		return null;
	}

	public static MUi_Button createSaveButton() {
		MUi_Button btn = new MUi_Button();
		btn.setName("save");
		return btn;
	}
}
