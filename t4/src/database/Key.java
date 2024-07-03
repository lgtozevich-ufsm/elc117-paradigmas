package database;

//- `TABLE_CAT` CHAR = olympics
//- `TABLE_SCHEM` CHAR = null
//- `TABLE_NAME` CHAR = hosts
//- `COLUMN_NAME` CHAR = editionId
//- `KEY_SEQ` SMALLINT = 1
//- `PK_NAME` CHAR = PRIMARY

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Key(
        String tableCatalog,
        String tableName,
        String name,
        String columnName,
        short sequence) {
    public static List<Key> extractKeysFromTable(DatabaseMetaData metaData, String tableCatalog, String tableName)
            throws SQLException {
        List<Key> keys = new ArrayList<>();
        ResultSet resultSet = metaData.getPrimaryKeys(tableCatalog, null, tableName);

        while (resultSet.next()) {
            String keyName = resultSet.getString("PK_NAME");
            String keyColumnName = resultSet.getString("COLUMN_NAME");
            short keySequence = resultSet.getShort("KEY_SEQ");

            keys.add(new Key(
                    tableCatalog,
                    tableName,
                    keyName,
                    keyColumnName,
                    keySequence));
        }

        return Collections.unmodifiableList(keys);
    }
}
