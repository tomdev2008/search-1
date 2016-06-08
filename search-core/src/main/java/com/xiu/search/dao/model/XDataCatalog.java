package com.xiu.search.dao.model;

import java.util.Date;
import java.util.List;

public class XDataCatalog {

    private Long catalogId;             // 运营分类ID

    private String catalogName;         // 运营分类名称

    private Long parentCatalogId;       // 运营分类父ID

    private String level;               // 层级
    
    private String display;             // 是否显示
    
    private String query;               // 查询表达式
    
    private String queryPiece;          // 完整的，包含子分类的，经过转换的查询newxiu索引的查询表达式
    
    private XDataCatalog parentCatalog;         // 父分类

    private List<XDataCatalog> childCatalog;    // 子分类
    
    
    
    private Long memberId;

    private String identifier;

    private Long markfordelete;

    private Date lastupdate;

    private String field1;

    private String oid;

    private Long rank;

    private Long optcounter;

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public Long getParentCatalogId() {
        return parentCatalogId;
    }

    public void setParentCatalogId(Long parentCatalogId) {
        this.parentCatalogId = parentCatalogId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQueryPiece() {
        return queryPiece;
    }

    public void setQueryPiece(String queryPiece) {
        this.queryPiece = queryPiece;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getMarkfordelete() {
        return markfordelete;
    }

    public void setMarkfordelete(Long markfordelete) {
        this.markfordelete = markfordelete;
    }

    public Date getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Long getOptcounter() {
        return optcounter;
    }

    public void setOptcounter(Long optcounter) {
        this.optcounter = optcounter;
    }

    public XDataCatalog getParentCatalog() {
        return parentCatalog;
    }

    public void setParentCatalog(XDataCatalog parentCatalog) {
        this.parentCatalog = parentCatalog;
    }

    public List<XDataCatalog> getChildCatalog() {
        return childCatalog;
    }

    public void setChildCatalog(List<XDataCatalog> childCatalog) {
        this.childCatalog = childCatalog;
    }
    
    
    
    
    
    
}