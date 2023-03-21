package com.example.coursach.entity.converters;

import com.example.coursach.entity.enums.ReportStatus;

import javax.persistence.AttributeConverter;

public class ReportStatusConverter implements AttributeConverter<ReportStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ReportStatus reportStatus) {
        return reportStatus.getVal();
    }

    @Override
    public ReportStatus convertToEntityAttribute(Integer integer) {
        return ReportStatus.lookup(integer);
    }
}
