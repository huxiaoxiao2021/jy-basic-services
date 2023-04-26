package com.jdl.basic.provider.core.po;

import lombok.Data;

@Data
public class NCWhiteRule {

    private Integer id;

    private Integer refId;

    private String quotaName;

    private Integer gt;

    private Integer gte;

    private Integer lt;

    private Integer lte;

    private Integer gtValue;

    private Integer ltValue;

    private String unit;

    @Override
    public String toString() {
        String operator1;
        String operator2;
        StringBuilder sb = new StringBuilder();

        sb.append((gt == 1 || gte == 1) ? gtValue : "");
        if (gt == 1 && gte == 0) {
            operator1 = "<";
        } else if (gt == 1 && gte == 1) {
            operator1 = "<=";
        } else if (gt == 0 && gte == 1) {
            operator1 = "=";
        } else {
            operator1 = "";
        }
        sb.append(operator1);
        sb.append(quotaName);
        if (lt == 1 && lte == 0) {
            operator2 = "<";
        } else if (lt == 1 && lte == 1) {
            operator2 = "<=";
        } else if (lt == 0 && lte == 1) {
            operator2 = "=";
        } else {
            operator2 = "";
        }
        sb.append(operator2);
        sb.append((lt == 1 || lte == 1) ? ltValue : "");

        sb.append(unit);
        return sb.toString();
    }

}
