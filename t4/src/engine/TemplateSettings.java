package engine;

import database.Table;

public record TemplateSettings(String driver, String url, String user, String password, Table table) { }
