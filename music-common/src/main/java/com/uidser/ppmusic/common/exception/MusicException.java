package com.uidser.ppmusic.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicException extends RuntimeException{

    private String msg;
    private Integer code;

}
