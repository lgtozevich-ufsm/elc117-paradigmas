package database;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

public record Scheme(String catalog, List<Table> tables) {
    public static Scheme extractSchemeFromCatalog(DatabaseMetaData metaData, String catalog) throws SQLException {
        return new Scheme(catalog, Table.extractTablesFromCatalog(metaData, catalog));
    }
}
