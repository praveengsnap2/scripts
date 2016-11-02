package com.snap2buy.webservice.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Anoop.
 */
@XmlRootElement(name = "ProjectQuestion")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectQuestion {
    @XmlElement
    String id;
    @XmlElement
    String customerProjectId;
    @XmlElement
    String customerCode;
    @XmlElement
    String desc;
    @XmlElement
    String responseColumn;

    public ProjectQuestion() {
        super();
    }

    public ProjectQuestion(String id, String customerCode, String customerProjectId, String desc, String responseColumn) {
        this.id = id;
        this.customerProjectId = customerProjectId;
        this.customerCode = customerCode;
        this.desc = desc;
        this.responseColumn = responseColumn;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerProjectId() {
        return customerProjectId;
    }

    public void setCustomerProjectId(String customerProjectId) {
        this.customerProjectId = customerProjectId;
    }

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getResponseColumn() {
		return responseColumn;
	}

	public void setResponseColumn(String responseColumn) {
		this.responseColumn = responseColumn;
	}
	
    @Override
    public String toString() {
        return "ProjectQuestion{" +
                "id='" + id + '\'' +
                ", customerProjectId='" + customerProjectId + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", desc='" + desc + '\'' +
                ", responseColumn='" + responseColumn + '\'' +
                '}';
    }
}
