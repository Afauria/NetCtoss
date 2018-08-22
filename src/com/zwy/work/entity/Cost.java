package com.zwy.work.entity;

import java.sql.Timestamp;

public class Cost {
	private Integer costId;
	private String name;
	private Integer baseDuration;//基本时常
	private Double baseCost;//基本费用
	private Double unitCost;//单位费用
	private String status;//状态:0-开通,1表示暂停
	private String descr;//介绍描述
	private Timestamp creatime;//创建时间
	private Timestamp startime;//开通时间
	private String costType;//资费类型,1-包月,2-套餐,3-计时.
	
	
	public Cost(){
	}

	public Cost(Integer costId, String name, Integer baseDuration, Double baseCost, Double unitCost, String status,
			String descr, Timestamp creatime, Timestamp startime, String costType) {
		this.costId = costId;
		this.name = name;
		this.baseDuration = baseDuration;
		this.baseCost = baseCost;
		this.unitCost = unitCost;
		this.status = status;
		this.descr = descr;
		this.creatime = creatime;
		this.startime = startime;
		this.costType = costType;
	}

	public Integer getCostId() {
		return costId;
	}

	public void setCostId(Integer costId) {
		this.costId = costId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBaseDuration() {
		return baseDuration;
	}

	public void setBaseDuration(Integer baseDuration) {
		this.baseDuration = baseDuration;
	}

	public Double getBaseCost() {
		return baseCost;
	}

	public void setBaseCost(Double baseCost) {
		this.baseCost = baseCost;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Timestamp getCreatime() {
		return creatime;
	}

	public void setCreatime(Timestamp creatime) {
		this.creatime = creatime;
	}

	public Timestamp getStartime() {
		return startime;
	}

	public void setStartime(Timestamp startime) {
		this.startime = startime;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	@Override
	public String toString() {
		return "Cost [costId=" + costId + ", name=" + name + ", baseDuration=" + baseDuration + ", baseCost=" + baseCost
				+ ", unitCost=" + unitCost + ", status=" + status + ", descr=" + descr + ", creatime=" + creatime
				+ ", startime=" + startime + ", costType=" + costType + "]";
	}
	
	
}
