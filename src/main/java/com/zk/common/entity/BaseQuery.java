package com.zk.common.entity;

import cn.hutool.core.util.StrUtil;
import com.zk.common.util.SqlUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("基础查询条件")
public class BaseQuery {

    @ApiModelProperty("页码，默认1")
    private Integer page = 1;
    @ApiModelProperty("每页行数，默认20")
    private Integer pageSize = 20;
    @ApiModelProperty("排序字段")
    private String sortName = "id";
    @ApiModelProperty("排序方式 ASC 升序 DESC 降序")
    private String sortOrder = "DESC";


    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortName() {
        String sn = this.sortName.matches("^[a-zA-Z][a-zA-Z0-9_]*$") ? this.sortName : "id";
        //大写转下划线
        return SqlUtils.camelToUnderline(sn);
    }

    public void setSortName(String sortName) {
        this.sortName = SqlUtils.camelToUnderline(sortName);
    }

    public String getSortOrder() {
        return this.sortOrderLegal() ? this.sortOrder : "desc";
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    private boolean sortOrderLegal() {
        return StrUtil.equalsAnyIgnoreCase("desc", new CharSequence[]{this.sortOrder}) || StrUtil.equalsAnyIgnoreCase("asc", new CharSequence[]{this.sortOrder});
    }

    public boolean isAsc() {
        return StrUtil.isNotBlank(this.sortOrder) && StrUtil.equalsAnyIgnoreCase("asc", new CharSequence[]{this.sortOrder});
    }
}
