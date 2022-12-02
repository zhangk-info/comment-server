package com.zk.comment.request;

import com.zk.common.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel("评论查询条件实体")
@Data
@EqualsAndHashCode(callSuper = true)
public class CommentQuery extends BaseQuery {

    @ApiModelProperty("文章id")
    private Long articleId;
}
