package ah_doc_manag.model;

public class SearchCriteria {
    private String columnName;
    private String columnValue;

    public SearchCriteria(String columnName, String columnValue) {
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

}
