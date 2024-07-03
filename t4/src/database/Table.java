package database;

//- `TABLE_CAT` VARCHAR = olympics
//- `TABLE_SCHEM` VARCHAR = null
//- `TABLE_NAME` VARCHAR = athletes
//- `TABLE_TYPE` VARCHAR = TABLE
//- `REMARKS` VARCHAR =
//- `TYPE_CAT` VARCHAR = null
//- `TYPE_SCHEM` VARCHAR = null
//- `TYPE_NAME` VARCHAR = null
//- `SELF_REFERENCING_COL_NAME` VARCHAR = null
//- `REF_GENERATION` VARCHAR = null

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Table(String catalog, String name, List<Column> columns, List<Key> keys) {
    public static List<Table> extractTablesFromCatalog(DatabaseMetaData metaData, String tableCatalog) throws SQLException {
        List<Table> tables = new ArrayList<>();
        ResultSet resultSet = metaData.getTables(tableCatalog, null, null, new String[]{"TABLE"});

        while (resultSet.next()) {
            String tableName = resultSet.getString("TABLE_NAME");

            tables.add(new Table(
                    tableCatalog,
                    tableName,
                    Column.extractColumnsFromTable(metaData, tableCatalog, tableName),
                    Key.extractKeysFromTable(metaData, tableCatalog, tableName)
            ));
        }

        return Collections.unmodifiableList(tables);
    }
}
