package com.example.coursach.entity.message;
import com.example.coursach.entity.converters.ErrorLocaleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MessageId implements Serializable {

    @Column(name = "code")
    private String code;

    @Column(name = "locale")
    @Convert(converter = ErrorLocaleConverter.class)
    private MessageLocale locale;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageId that = (MessageId) o;
        return code.equals(that.code) && locale == that.locale;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, locale);
    }
}
