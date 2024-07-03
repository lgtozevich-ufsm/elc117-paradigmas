package engine.templates;

import engine.Resource;
import engine.TemplateSettings;
import engine.TemplateStandard;

public interface Template {
    Resource render(TemplateStandard standard, TemplateSettings settings);
}
