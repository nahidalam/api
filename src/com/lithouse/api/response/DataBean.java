package com.lithouse.api.response;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import com.lithouse.common.model.BaseModel;

@XmlRootElement
@XmlAccessorType ( XmlAccessType.FIELD )
public class DataBean extends BaseBean {
	@XmlElementRef
	private BaseModel result;
	
	public DataBean ( ) { }

	public DataBean ( BaseModel result ) {
		this.result = result;
	}
}
