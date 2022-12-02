package com.zk.comment.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("评论添加或修改实体")
@Data
public class CommentDTO {

    /**
     * '文章id'
     */
    @ApiModelProperty("文章id")
    private Long articleId;

    /**
     * 上级评论id,没有就是文章id
     */
    @ApiModelProperty("上级评论id,没有就不传入，后台默认为文章id")
    private Long pid;

    /**
     * 第一层评论的id
     */
    @ApiModelProperty("第一层评论的id")
    private Long firstFloorId;
    /**
     * '评论内容'
     */
    @ApiModelProperty("文章内容")
    private String content;
}
