package org.example.w2.todo;


import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.A;

import java.sql.Timestamp;

@Log4j2
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TodoVO {

    private Integer tno;
    private String title;
    private String writer;
    private Timestamp regdate;
    private Timestamp moddate;
    private boolean delflag;

}
