package org.example.w2.todo;

import com.sun.jna.platform.win32.Sspi;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TodoVO { //db의 레코드 한줄한줄은 todovo의 인스턴스이다. 테이블 구조가 같으니까.

    private Integer tno;
    private String title;
    private String writer;
    private Timestamp regDate;
    private Timestamp modDate;
    private boolean delFlag;


}
