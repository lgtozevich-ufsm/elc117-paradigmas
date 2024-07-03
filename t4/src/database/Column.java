package database;

//- `TABLE_CAT` CHAR = olympics
//- `TABLE_SCHEM` CHAR = null
//- `TABLE_NAME` CHAR = athletes
//- `COLUMN_NAME` CHAR = noc
//- `DATA_TYPE` INT = 1
//- `TYPE_NAME` CHAR = CHAR
//- `COLUMN_SIZE` INT = 3
//- `BUFFER_LENGTH` INT = 65535
//- `DECIMAL_DIGITS` INT = null
//- `NUM_PREC_RADIX` INT = 10
//- `NULLABLE` INT = 0
//- `REMARKS` CHAR =
//- `COLUMN_DEF` CHAR = null
//- `SQL_DATA_TYPE` INT = 0
//- `SQL_DATETIME_SUB` INT = 0
//- `CHAR_OCTET_LENGTH` INT = 12
//- `ORDINAL_POSITION` INT = 10
//- `IS_NULLABLE` CHAR = NO
//- `SCOPE_CATALOG` CHAR = null
//- `SCOPE_SCHEMA` CHAR = null
//- `SCOPE_TABLE` CHAR = null
//- `SOURCE_DATA_TYPE` SMALLINT = null
//- `IS_AUTOINCREMENT` CHAR = NO
//- `IS_GENERATEDCOLUMN` CHAR = NO

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Column(
        String tableCatalog,
        String tableName,
        String name,
        String typeName,
        int size,
        int decimalDigits,
        boolean nullable,
        boolean autoIncrement,
        boolean generated
) {
    public static List<Column> extractColumnsFromTable(DatabaseMetaData metaData, String tableCatalog, String tableName) throws SQLException {
        List<Column> columns = new ArrayList<>();
        ResultSet resultSet = metaData.getColumns(tableCatalog, null, tableName, null);

        while (resultSet.next()) {
            String columnName = resultSet.getString("COLUMN_NAME");
            String columnTypeName = resultSet.getString("TYPE_NAME");
            int columnSize = resultSet.getInt("COLUMN_SIZE");
            int columnDecimalDigits = resultSet.getInt("DECIMAL_DIGITS");
            String columnIsNullable = resultSet.getString("IS_NULLABLE");
            String columnIsAutoIncrement = resultSet.getString("IS_AUTOINCREMENT");
            String columnGenerated = resultSet.getString("IS_GENERATEDCOLUMN");

            columns.add(new Column(
                    tableCatalog,
                    tableName,
                    columnName,
                    columnTypeName,
                    columnSize,
                    columnDecimalDigits,
                    columnIsNullable.equals("YES"),
                    columnIsAutoIncrement.equals("YES"),
                    columnGenerated.equals("YES")
            ));
        }

        return Collections.unmodifiableList(columns);
    }
}
