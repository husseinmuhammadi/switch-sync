package ir.javadev.jsf.component;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import java.util.Map;

@FacesComponent("ir.javadev.jsf.component.TabContainer")
@ResourceDependencies({
        @ResourceDependency(
                library = "componentFaces",
                name = "js/componentFaces.js"
        )
})
public class UITabContainer extends UIOutput {
    public static final String FAMILY = "ir.javadev.jsf.component";
    public static final String RENDERER_TYPE = "ir.javadev.jsf.renderer.TabContainerRenderer";

    private String activeId;

    public UITabContainer() {
    }

    @Override
    public String getFamily() {
        return FAMILY;
    }

    public boolean getRendersChildren() {
        return true;
    }

    public String getRendererType() {
        return RENDERER_TYPE;
    }

    public Map<String, Map<String, Map<String, String>>> getSrcMap() {
        return (Map) this.getStateHelper().eval(UITabContainer.PropertyKeys.srcMap);
    }

    public void setSrcMap(Map<String, Map<String, Map<String, String>>> srcMap) {
        this.getStateHelper().put(UITabContainer.PropertyKeys.srcMap, srcMap);
    }

    public String getActiveId() {
        return this.activeId;
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId;
    }

    public static enum PropertyKeys {
        srcMap;

        private PropertyKeys() {
        }
    }
}
