
package com.iwgame.testlombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import com.google.gson.Gson;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Student")
public class Student {
    @Column(isId = true, name = "id")
    private int id;

    @Column(name = "name")
    private String name;

}
