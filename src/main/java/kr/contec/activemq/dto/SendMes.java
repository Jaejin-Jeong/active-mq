package kr.contec.activemq.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
public class SendMes implements Serializable {

    private String id;

    private String msg;

    private String writer;

    @Override
    public String toString() {
        return "SendMes{" +
                "id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                ", writer='" + writer + '\'' +
                '}';
    }
}
