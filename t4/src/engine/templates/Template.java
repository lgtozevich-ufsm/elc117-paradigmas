package engine.templates;

import engine.TemplateSettings;
import engine.TemplateStandard;

public interface Template {
    String render(TemplateStandard standard, TemplateSettings settings);
}
