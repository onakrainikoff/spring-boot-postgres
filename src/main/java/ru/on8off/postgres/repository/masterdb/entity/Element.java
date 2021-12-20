package ru.on8off.postgres.repository.masterdb.entity;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLHStoreType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "elements")
@Getter @Setter
@TypeDefs({
    @TypeDef(name = "string-array", typeClass = StringArrayType.class),
    @TypeDef(name = "hstore", typeClass = PostgreSQLHStoreType.class),
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class Element extends BaseEntity<Integer>{

    @Column(name = "name", nullable = false)
    private String name;

    @Basic(optional = false)
    @ManyToOne(targetEntity = ElementGroup.class)
    @JoinColumn(name = "element_group")
    private ElementGroup elementGroup;

    @Type( type = "string-array" )
    @Column(name = "tags", columnDefinition = "varchar[]")
    private String[] tags;

    @Column(name="params")
    @Type(type="hstore")
    private Map<String, String> params;

    @Column(name="items")
    @Type(type="jsonb")
    private Map<String, List<String>> items;
}
