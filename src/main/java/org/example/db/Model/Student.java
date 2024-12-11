package org.example.db.Model;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "students")
public class Student {
    @DatabaseField(id = true)
    private String ulearnID;

    @DatabaseField
    private String name;

    @DatabaseField
    private String group;

    public Student() {
    }

    public Student(String ulearnID, String name, String group) {
        this.ulearnID = ulearnID;
        this.name = name;
        this.group = group;
    }

    public String getUlearnID() {
        return ulearnID;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }
}
