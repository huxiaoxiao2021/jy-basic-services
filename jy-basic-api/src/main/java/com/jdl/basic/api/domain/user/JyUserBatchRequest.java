package com.jdl.basic.api.domain.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class JyUserBatchRequest extends JyUserQueryCondition implements Serializable {

    private static final long serialVersionUID = 1461527685425376288L;

    List<JyUser> users;
}
