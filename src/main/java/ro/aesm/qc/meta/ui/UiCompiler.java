package ro.aesm.qc.meta.ui;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

import ro.aesm.qc.api.exception.QcResourceException;
import ro.aesm.qc.api.meta.component.IMetaComponentCompiler;
import ro.aesm.qc.api.meta.component.IMetaComponentModel;
import ro.aesm.qc.base.AbstractMetaCompiler;
import ro.aesm.qc.meta.ui.model.MUi_Button;
import ro.aesm.qc.meta.ui.model.MUi_Checkbox;
import ro.aesm.qc.meta.ui.model.MUi_DateField;
import ro.aesm.qc.meta.ui.model.MUi_FormItem;
import ro.aesm.qc.meta.ui.model.MUi_HiddenField;
import ro.aesm.qc.meta.ui.model.MUi_Html;
import ro.aesm.qc.meta.ui.model.MUi_Item;
import ro.aesm.qc.meta.ui.model.MUi_LovField;
import ro.aesm.qc.meta.ui.model.MUi_NumberField;
import ro.aesm.qc.meta.ui.model.MUi_Region;
import ro.aesm.qc.meta.ui.model.MUi_TextField;
import ro.aesm.qc.meta.ui.model.MUi_Textarea;
import ro.aesm.qc.meta.ui.model.feature.GridLayoutChildItem;
import ro.aesm.qc.meta.ui.model.feature.LabeledItem;

@Component(service = IMetaComponentCompiler.class)
public class UiCompiler extends AbstractMetaCompiler {

	protected static final String NL = "\n";

	@Override
	public Map<String, Object> compile(IMetaComponentModel uiModel) throws QcResourceException {
		if (!(uiModel instanceof UiModel)) {
			return null;
		}
		Map<String, Object> result = new HashMap<>();
		UiModel m = (UiModel) uiModel;
		StringBuilder buffer = new StringBuilder();
		MUi_Region mainRegion = m.getRegion("main");
		if (mainRegion == null) {
			throw new QcResourceException("Main region (with name='main') not found ");
		}
		this.writeRegion(buffer, mainRegion, null);
		result.put("ui", buffer.toString());
		return result;
	}

	protected void writeItem(StringBuilder buffer, MUi_Item item, MUi_Region parent) {
		if (item instanceof MUi_Region) {
			this.writeRegion(buffer, (MUi_Region) item, parent);
		} else if (item instanceof MUi_FormItem) {
			this.writeFormItem(buffer, (MUi_FormItem) item, parent);
		} else if (item instanceof MUi_Button) {
			this.writeButton(buffer, (MUi_Button) item, parent);
		} else if (item instanceof MUi_Html) {
			this.writeHtml(buffer, (MUi_Html) item, parent);
		}
	}

	protected void fillStyle(StringBuilder styleBuffer, MUi_Item item, MUi_Region parent) {
		if (parent != null) {
			if ("grid".equals(parent.getLayout())) {
				this.fillGridLayoutChildItemStyle(styleBuffer, item, parent);
			}
		}
	}

	protected void fillGridLayoutChildItemStyle(StringBuilder styleBuffer, MUi_Item item, MUi_Region parent) {
		GridLayoutChildItem gli = item.getGridLayoutChildItem();
		styleBuffer.append("grid-column: span " + gli.getColspan() + ";");
		if (gli.isNewRow()) {
			styleBuffer.append("grid-column-start: 1; grid-column-end: " + (gli.getColspan() + 1) + ";");
		}
	}

	protected void writeRegion(StringBuilder buffer, MUi_Region region, MUi_Region parent) {
		String css = region.getLayout() + "-layout";
		if (region.getCssClass() != null) {
			css += " " + region.getCssClass();
		}
		buffer.append("<div class=\"" + css + "\"");
		StringBuilder styleBuffer = new StringBuilder();
		this.fillStyle(styleBuffer, region, parent);
		if (!styleBuffer.isEmpty()) {
			buffer.append(" style=\"" + styleBuffer.toString() + "\"");
		}
		buffer.append(">");
		for (MUi_Item child : region.getChildren()) {
			this.writeItem(buffer, child, region);
		}
		buffer.append("</div>");
	}

	protected void writeFormItem(StringBuilder buffer, MUi_FormItem item, MUi_Region parent) {

		LabeledItem li = item.getLabeledItem();
		String formItemCssClass = li.getLabelPosition().equals(ConstantsUi.LABEL_POSITION__TOP) ? "v-form-item"
				: "h-form-item";
		String css = formItemCssClass;
		if (item.isRequired()) {
			css += " required";
		}
		if (item.getCssClass() != null) {
			css += " " + item.getCssClass();
		}
		buffer.append("<div class=\"" + css + "\"");

		StringBuilder styleBuffer = new StringBuilder();
		this.fillStyle(styleBuffer, item, parent);
		if (!styleBuffer.isEmpty()) {
			buffer.append(" style=\"" + styleBuffer.toString() + "\"");
		}
		buffer.append(">" + NL);

		String showLabel = li.getShowLabel();
		if ("yes".equals(showLabel) || "empty".equals(showLabel)) {
			String label = "";
			if (showLabel.equals("yes")) {
				// label = "<span>" + item.getLabel() + "</span>"; // {{itemsDict." +
				// item.getLabel() + ".label}}
				label = "<span>" + item.getName() + "</span>";
			}

			String labelStyle = "width:" + li.getLabelWidth() + "px;";
			if (ConstantsUi.LABEL_ALIGN__BEGIN.equals(li.getLabelAlign())) {
				labelStyle += "text-align:left;";
			} else if (ConstantsUi.LABEL_ALIGN__CENTER.equals(li.getLabelAlign())) {
				labelStyle += "text-align:center;";
			} else if (ConstantsUi.LABEL_ALIGN__END.equals(li.getLabelAlign())) {
				labelStyle += "text-align:right;";
			}
			buffer.append("    <div class=\"" + formItemCssClass + "-label\" style=\"" + labelStyle + "\">" + label
					+ "</div>");
		}

		buffer.append("<div class=\"" + formItemCssClass + "-input\">");
		writeFormItemInput(buffer, item);
		buffer.append("</div>" + NL);

		buffer.append("</div>" + NL);
	}

	protected void writeFormItemInput(StringBuilder buffer, MUi_Item item) {
		if (item instanceof MUi_TextField) {
			this.writeTextField(buffer, (MUi_TextField) item);
		} else if (item instanceof MUi_Textarea) {
			this.writeTextArea(buffer, (MUi_Textarea) item);
		} else if (item instanceof MUi_DateField) {
			this.writeDateField(buffer, (MUi_DateField) item);
		} else if (item instanceof MUi_NumberField) {
			this.writeNumberField(buffer, (MUi_NumberField) item);
		} else if (item instanceof MUi_LovField) {
			this.writeLovField(buffer, (MUi_LovField) item);
		} else if (item instanceof MUi_Checkbox) {
			this.writeCheckbox(buffer, (MUi_Checkbox) item);
		} else if (item instanceof MUi_HiddenField) {
			this.writeHiddenField(buffer, (MUi_HiddenField) item);
		}

	}

	protected void writeHiddenField(StringBuilder buffer, MUi_HiddenField item) {
		buffer.append("<input id=\"" + item.getId() + "\" type=\"hidden\"");
		this.add_vmodel(buffer, item);
		buffer.append("/>");
	}

	protected void writeTextField(StringBuilder buffer, MUi_TextField item) {
		buffer.append("<input id=\"" + item.getId() + "\"");
		this.add_vmodel(buffer, item);
		buffer.append("/>");
	}

	protected void writeTextArea(StringBuilder buffer, MUi_Textarea item) {
		buffer.append("<textarea id=\"" + item.getId() + "\"");
		this.add_vmodel(buffer, item);
		buffer.append(">");
		buffer.append("</textarea>");
	}

	protected void writeDateField(StringBuilder buffer, MUi_DateField item) {
		buffer.append("<input id=\"" + item.getId() + "\"");
		this.add_vmodel(buffer, item);
		buffer.append("/>");
	}

	protected void writeNumberField(StringBuilder buffer, MUi_NumberField item) {
		buffer.append("<input id=\"" + item.getId() + "\"");
		this.add_vmodel(buffer, item);
		buffer.append("/>");
	}

	protected void writeLovField(StringBuilder buffer, MUi_LovField item) {
		buffer.append("<select id=\"" + item.getId() + "\"");
		this.add_vmodel(buffer, item);
		buffer.append(">");
		buffer.append("</select>");
	}

	protected void writeCheckbox(StringBuilder buffer, MUi_Checkbox item) {
		buffer.append("<input id=\"" + item.getId() + "\" type=\"checkbox\"");
		this.add_vmodel(buffer, item);
		buffer.append("/>");
	}

	protected void writeButton(StringBuilder buffer, MUi_Button item, MUi_Region parent) {
		String text = item.getText();
		if (text == null) {
			text = item.getName();
		}
		String icon = item.getIcon();
		String btnText = text;
		buffer.append("<button id=\"@" + item.getId() + "\"");
		String css = "";
		if (item.getCssClass() != null) {
			css += " " + item.getCssClass();
		}
		if (!css.equals("")) {
			buffer.append(" class=\"" + css + "\"");
		}
		buffer.append(">");
		if (icon != null) {
			String style = "";
			if (text != null) {
				style = "style=\"margin-right:10px;\"";
			}
			btnText = "<span class=\"icon\"" + style + ">" + icon + "</span>" + text;
		}
		buffer.append(btnText);
		buffer.append("</button>");
	}

	protected void writeHtml(StringBuilder buffer, MUi_Html item, MUi_Region parent) {
		buffer.append(item.getContent());
	}

	protected void add_vmodel(StringBuilder buffer, MUi_Item item) {
		buffer.append(" value=\"" + item.getClass().getName() + "\"");
		// buffer.append(" v-model=\"" + item.getDsItem() + "\"");
	}

}
