package edu.pucmm.practica1.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
@Entity
public class Mock  {
    @Id
    @GeneratedValue
    private int id;
    private String access;
    private String endpoint;
    private int http_status;
    private String content_type;
    private Date expirate;
    private String estado;
    @Nullable
    private String http_header;
    @Nullable
    private String http_body;
    @Nullable
    private String name;
    @Nullable
    private String description;
    @Nullable
    private int delay;
    @ManyToOne
    @Nullable
    private Proyecto proyecto;

    public Mock() {

    }
}
